package com.grimrepos.biopay.service.impl;

import com.grimrepos.biopay.dto.PaymentRequest;
import com.grimrepos.biopay.dto.PaymentResponse;
import com.grimrepos.biopay.entity.BankAccount;
import com.grimrepos.biopay.entity.Payment;
import com.grimrepos.biopay.exception.BadRequestException;
import com.grimrepos.biopay.exception.ResourceNotFoundException;
import com.grimrepos.biopay.repository.BankAccountRepository;
import com.grimrepos.biopay.repository.PaymentRepository;
import com.grimrepos.biopay.service.BankPinAuthService;
import com.grimrepos.biopay.service.PaymentAuthService;
import com.grimrepos.biopay.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Base64;

@Slf4j
@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentAuthService paymentAuthService;
    private final BankAccountRepository bankAccountRepository;
    private final BankPinAuthService bankPinAuthService;
    private final PaymentRepository paymentRepository;

    @Transactional
    @Override
    public PaymentResponse makePayment(PaymentRequest request) {
        //Validate amount
        if (request.getAmount() == null || request.getAmount() <= 0) {
            throw new BadRequestException("Payment amount must be greater than zero");
        }
        log.info("Payment initiated for account: {} amount {} {}",
                request.getBankAccountNumber(),
                request.getAmount(),
                resolveCurrency(request));

        // 1. Fetch bank account
        BankAccount account = bankAccountRepository.findByAccountNumber(request.getBankAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Bank account not found"));

        // 2. Verify fingerprint
        byte[] fingerprintBytes;
        try {
            fingerprintBytes = Base64.getDecoder().decode(request.getFingerprintTemplate());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid fingerprint template format");
        }
        boolean fingerprintMatch = paymentAuthService.verifyFingerprint(account.getUser().getUserId(), fingerprintBytes);
        if (!fingerprintMatch) {
            log.warn("Fingerprint mismatch for user: {}", account.getUser().getUserId());
            return buildResponse(account, request.getAmount(), resolveCurrency(request), "FAILED", "Fingerprint mismatch");
        }

        // 3. Verify Bank PIN
        boolean pinMatch = bankPinAuthService.verifyBankPin(
                request.getBankAccountNumber(),
                request.getBankPin()
        );
        if (!pinMatch){
            log.warn("Invalid PIN for account: {}", request.getBankAccountNumber());
            return buildResponse(account, request.getAmount(), resolveCurrency(request), "FAILED", "Invalid bank PIN");
        }

        // 4. Check balance
        if (account.getBalance() < request.getAmount()) {
            log.warn("Insufficient funds in account: {}. Requested: {}, Available: {}",
                    account.getAccountNumber(), request.getAmount(), account.getBalance());
            return buildResponse(account, request.getAmount(), resolveCurrency(request), "FAILED", "Insufficient funds");
        }
        // 5. Deduct balance
        account.setBalance(account.getBalance() - request.getAmount());
        bankAccountRepository.save(account);

        // 6. Save payment and return response
        log.info("Payment successful for account: {} amount: {} {}",
                account.getAccountNumber(), request.getAmount(), resolveCurrency(request));
        return buildResponse(account, request.getAmount(), resolveCurrency(request), "SUCCESS", null);
    }

    /**
     * Resolve currency, defaulting to ZAR if none is provided.
     */
    private String resolveCurrency(PaymentRequest request) {
        return (request.getCurrency() == null || request.getCurrency().isBlank())
                ? "ZAR"
                : request.getCurrency().trim().toUpperCase();
    }

    private PaymentResponse buildResponse(BankAccount account, Double amount, String currency, String status, String failureReason) {
        Payment payment = Payment.builder()
                .bankAccount(account)
                .amount(amount)
                .currency(currency)
                .status(status)
                .failureReason(failureReason)
                .timestamp(Instant.now())
                .build();

        payment = paymentRepository.save(payment);

        return PaymentResponse.builder()
                .paymentId(payment.getPaymentId())
                .bankAccountNumber(account.getAccountNumber())
                .amount(amount)
                .currency(currency)
                .status(status)
                .failureReason(failureReason)
                .timestamp(payment.getTimestamp())
                .remainingBalance(account.getBalance())
                .build();
    }

}

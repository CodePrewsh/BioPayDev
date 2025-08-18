package com.grimrepos.biopay.service.impl;

import com.grimrepos.biopay.entity.FingerprintTemplate;
import com.grimrepos.biopay.exception.ResourceNotFoundException;
import com.grimrepos.biopay.repository.FingerprintTemplateRepository;
import com.grimrepos.biopay.repository.UserRepository;
import com.grimrepos.biopay.service.BankPinAuthService;
import com.grimrepos.biopay.service.PaymentAuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentAuthServiceImpl implements PaymentAuthService {

    private final UserRepository userRepository;
    private final FingerprintTemplateRepository fingerprintRepository;
    private final BankPinAuthService bankPinAuthService;

    @Override
    public boolean verifyFingerprint(UUID userId, byte[] fingerprintTemplate) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        FingerprintTemplate latest = fingerprintRepository
                .findTopByUser_UserIdOrderByCreatedAtDesc(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No fingerprint template found"));

        // For now, simple byte comparison — replace with real biometric match later
        return Arrays.equals(latest.getTemplateData(),fingerprintTemplate);
    }

    @Override
    public boolean verifyFingerprintAndPin(UUID userId, byte[] fingerprintTemplate, String bankAccountNumber, String pin) {
        boolean fingerprintMatch = verifyFingerprint(userId,fingerprintTemplate);
        if (!fingerprintMatch) {
            return false;
        }
        return bankPinAuthService.verifyBankPin(bankAccountNumber,pin);
    }
}

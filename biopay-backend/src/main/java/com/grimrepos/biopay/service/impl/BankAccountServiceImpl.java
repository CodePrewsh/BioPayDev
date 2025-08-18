package com.grimrepos.biopay.service.impl;

import com.grimrepos.biopay.dto.BankAccountLinkRequest;
import com.grimrepos.biopay.entity.BankAccount;
import com.grimrepos.biopay.entity.User;
import com.grimrepos.biopay.exception.ResourceNotFoundException;
import com.grimrepos.biopay.repository.BankAccountRepository;
import com.grimrepos.biopay.repository.UserRepository;
import com.grimrepos.biopay.service.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;

    @Override
    public BankAccount linkAccount(BankAccountLinkRequest request) {
        User user = userRepository.findById(UUID.fromString(request.getUserId()))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        BankAccount account = BankAccount.builder()
                .user(user)
                .accountNumber(request.getAccountNumber())
                .bankName(request.getBankName())
                .linkedAt(Instant.now())
                .build();

        return bankAccountRepository.save(account);
    }
}

package com.grimrepos.biopay.service.impl;

import com.grimrepos.biopay.dto.UserRegistrationRequest;
import com.grimrepos.biopay.dto.UserRegistrationResponse;
import com.grimrepos.biopay.entity.BankAccount;
import com.grimrepos.biopay.entity.RegistrationSession;
import com.grimrepos.biopay.entity.User;
import com.grimrepos.biopay.entity.enums.SessionStatus;
import com.grimrepos.biopay.entity.enums.UserStatus;
import com.grimrepos.biopay.exception.BadRequestException;
import com.grimrepos.biopay.repository.BankAccountRepository;
import com.grimrepos.biopay.repository.RegistrationSessionRepository;
import com.grimrepos.biopay.repository.UserRepository;
import com.grimrepos.biopay.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RegistrationSessionRepository sessionRepository;
    private final BankAccountRepository bankAccountRepository;

    @Transactional
    @Override
    public UserRegistrationResponse registerNewUser(UserRegistrationRequest request) {
        userRepository.findByIdNumber(request.getIdNumber())
                .ifPresent(u -> { throw new BadRequestException("User with ID number already exists"); });

        // create user
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .idNumber(request.getIdNumber())
                .status(UserStatus.PENDING)
                .registeredAt(Instant.now())
                .build();
        user = userRepository.save(user);

        // create registration session
        RegistrationSession session = RegistrationSession.builder()
                .user(user)
                .inputValidated(false)
                .fingerprintUploaded(false)
                .templateGenerated(false)
                .deviceConfigured(false)
                .status(SessionStatus.PENDING)
                .startedAt(Instant.now())
                .build();
        session = sessionRepository.save(session);

        // create linked bank account
        BankAccount bankAccount = BankAccount.builder()
                .user(user)
                .accountNumber(request.getAccountNumber())
                .bankName(request.getBankName())
                .linkedAt(Instant.now())
                .build();
        bankAccount = bankAccountRepository.save(bankAccount);

        return new UserRegistrationResponse(
                user.getUserId(),
                session.getSessionId(),
                bankAccount.getAccountId(),
                "User registered and linked with bank account. Complete fingerprint upload and device configuration to finish registration."
        );
    }

}

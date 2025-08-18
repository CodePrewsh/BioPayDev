package com.grimrepos.biopay.service.impl;

import com.grimrepos.biopay.service.BankPinAuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BankPinAuthServiceImpl implements BankPinAuthService {
    @Override
    public boolean verifyBankPin(String bankAccountNumber, String pin) {
        // In reality, this would call a secure external API.
        // For now, we just simulate success for a specific test PIN.
        return "1234".equals(pin);
    }
}

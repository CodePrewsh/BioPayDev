package com.grimrepos.biopay.service;

public interface BankPinAuthService {
    boolean verifyBankPin(String bankAccountNumber,String pin);
}

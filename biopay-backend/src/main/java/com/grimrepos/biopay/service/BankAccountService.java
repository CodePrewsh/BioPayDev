package com.grimrepos.biopay.service;

import com.grimrepos.biopay.dto.BankAccountLinkRequest;
import com.grimrepos.biopay.entity.BankAccount;

public interface BankAccountService {
    BankAccount linkAccount(BankAccountLinkRequest request);
}

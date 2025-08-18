package com.grimrepos.biopay.controller;

import com.grimrepos.biopay.dto.BankAccountLinkRequest;
import com.grimrepos.biopay.entity.BankAccount;
import com.grimrepos.biopay.service.BankAccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
@AllArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @PostMapping("/link")
    public ResponseEntity<BankAccount> linkAccount(@Valid @RequestBody BankAccountLinkRequest request) {
        BankAccount account = bankAccountService.linkAccount(request);
        return ResponseEntity.ok(account);
    }
}

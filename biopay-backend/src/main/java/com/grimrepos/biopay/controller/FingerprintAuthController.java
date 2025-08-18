package com.grimrepos.biopay.controller;

import com.grimrepos.biopay.service.PaymentAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class FingerprintAuthController {

    private final PaymentAuthService paymentAuthService;

    @PostMapping("/fingerprint/{userId}")
    public ResponseEntity<String> verifyFingerprint(
            @PathVariable String userId, @RequestBody String base64Fingerprint) {
        byte[] fingerprintBytes = Base64.getDecoder().decode(base64Fingerprint);
        boolean match = paymentAuthService.verifyFingerprint(UUID.fromString(userId), fingerprintBytes);
        return ResponseEntity.ok(match?"Fingerprint match":"Fingerprint mismatch");
    }
}

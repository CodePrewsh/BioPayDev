package com.grimrepos.biopay.controller;

import com.grimrepos.biopay.dto.PinVerificationRequest;
import com.grimrepos.biopay.service.PaymentAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payment-auth")
@RequiredArgsConstructor
public class PaymentAuthController {

    private final PaymentAuthService paymentAuthService;

    @PostMapping("/verify/{userId}")
    public ResponseEntity<String> verifyFingerprintAndPin(@PathVariable String userId,@RequestParam String base64Fingerprint,@Valid @RequestBody PinVerificationRequest pinRequest) {
        byte[] fingerprintBytes = Base64.getDecoder().decode(base64Fingerprint);

        boolean authSuccess = paymentAuthService.verifyFingerprintAndPin(
                UUID.fromString(userId),
                fingerprintBytes,
                pinRequest.getBankAccountNumber(),
                pinRequest.getPin()
        );

        return ResponseEntity.ok(authSuccess? "Authentication successful":"Authentication failed");
    }

}

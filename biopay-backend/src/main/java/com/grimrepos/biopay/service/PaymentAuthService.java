package com.grimrepos.biopay.service;

        import java.util.UUID;

public interface PaymentAuthService {
    boolean verifyFingerprint(UUID userId, byte[] fingerprintTemplate);
    boolean verifyFingerprintAndPin(UUID userId,byte[] fingerprintTemplate,String bankAccountNumber,String pin);
}

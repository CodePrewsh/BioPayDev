package com.grimrepos.biopay.service;

import com.grimrepos.biopay.dto.FingerprintUploadRequest;
import com.grimrepos.biopay.dto.RegistrationSessionResponse;
import com.grimrepos.biopay.dto.SessionDeviceRequest;

import java.util.UUID;

public interface RegistrationService {
    RegistrationSessionResponse getSession(UUID sessionId);
    void uploadFingerprint(UUID sessionId, FingerprintUploadRequest request);
    void registerDevice(UUID sessionId, SessionDeviceRequest request);
}

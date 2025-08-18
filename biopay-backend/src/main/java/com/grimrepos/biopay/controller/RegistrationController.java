package com.grimrepos.biopay.controller;

import com.grimrepos.biopay.dto.FingerprintUploadRequest;
import com.grimrepos.biopay.dto.RegistrationSessionResponse;
import com.grimrepos.biopay.dto.SessionDeviceRequest;
import com.grimrepos.biopay.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @GetMapping("/sessions/{sessionId}")
    public ResponseEntity<RegistrationSessionResponse> getSession(@PathVariable String sessionId) {
        RegistrationSessionResponse sessionResponse = registrationService.getSession(UUID.fromString(sessionId));
        return ResponseEntity.ok(sessionResponse);
    }

    @PostMapping("/sessions/{sessionId}/fingerprint")
    public ResponseEntity<String> uploadFingerprint(@PathVariable String sessionId,@Valid @RequestBody FingerprintUploadRequest request) {
        registrationService.uploadFingerprint(UUID.fromString(sessionId),request);
        return ResponseEntity.ok("Fingerprint uploaded");
    }

    @PostMapping("/sessions/{sessionId}/device")
    public ResponseEntity<String> registerDevice(@PathVariable String sessionId,@Valid @RequestBody SessionDeviceRequest request) {
        registrationService.registerDevice(UUID.fromString(sessionId),request);
        return ResponseEntity.ok("Device registered");
    }
}

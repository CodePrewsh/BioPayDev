package com.grimrepos.biopay.service.impl;

import com.grimrepos.biopay.dto.FingerprintUploadRequest;
import com.grimrepos.biopay.dto.RegistrationSessionResponse;
import com.grimrepos.biopay.dto.SessionDeviceRequest;
import com.grimrepos.biopay.entity.FingerprintTemplate;
import com.grimrepos.biopay.entity.RegistrationSession;
import com.grimrepos.biopay.entity.SessionDevice;
import com.grimrepos.biopay.entity.enums.SessionStatus;
import com.grimrepos.biopay.exception.BadRequestException;
import com.grimrepos.biopay.exception.ResourceNotFoundException;
import com.grimrepos.biopay.repository.FingerprintTemplateRepository;
import com.grimrepos.biopay.repository.RegistrationSessionRepository;
import com.grimrepos.biopay.repository.SessionDeviceRepository;
import com.grimrepos.biopay.repository.UserRepository;
import com.grimrepos.biopay.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationSessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final FingerprintTemplateRepository fingerprintRepository;
    private final SessionDeviceRepository sessionDeviceRepository;

    @Override
    public RegistrationSessionResponse getSession(UUID sessionId) {
        RegistrationSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Registration session not found"));
        return RegistrationSessionResponse.builder()
                .sessionId(session.getSessionId())
                .userId(session.getUser() != null ? session.getUser().getUserId():null)
                .inputValidated(session.isInputValidated())
                .fingerprintUploaded(session.isFingerprintUploaded())
                .templateGenerated(session.isTemplateGenerated())
                .deviceConfigured(session.isDeviceConfigured())
                .status(session.getStatus() != null ? session.getStatus().name():null)
                .startedAt(session.getStartedAt())
                .completedAt(session.getCompletedAt())
                .build();
    }

    @Override
    public void uploadFingerprint(UUID sessionId, FingerprintUploadRequest request) {
        RegistrationSession session = sessionRepository.findById(sessionId)
                .orElseThrow(()->new ResourceNotFoundException("Registration session not found"));

        if (session.getUser() == null) {
            throw new BadRequestException("Session has no linked user");
        }

        byte[] template;
        try {
            template = Base64.getDecoder().decode(request.getBase64Template());
        }catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid base64 fingerprint data");
        }

        FingerprintTemplate fp = FingerprintTemplate.builder()
                .user(session.getUser())
                .templateData(template)
                .qualityScore(null)
                .createdAt(Instant.now())
                .build();

        fingerprintRepository.save(fp);

        //update session flags
        session.setFingerprintUploaded(true);
        session.setTemplateGenerated(true);

        //if deviConfigured already true, mark session completed
        if (session.isDeviceConfigured()) {
            session.setStatus(SessionStatus.COMPLETED);
            session.setCompletedAt(Instant.now());
        }

        sessionRepository.save(session);

    }

    @Override
    public void registerDevice(UUID sessionId, SessionDeviceRequest request) {
        RegistrationSession session = sessionRepository.findById(sessionId)
                .orElseThrow(()-> new BadRequestException("Registration session not found"));

        SessionDevice device = SessionDevice.builder()
                .registrationSession(session)
                .deviceType(request.getDeviceType())
                .osVersion(request.getOsVersion())
                .appVersion(request.getAppVersion())
                .registeredAt(Instant.now())
                .build();

        sessionDeviceRepository.save(device);

        session.setDeviceConfigured(true);

        if (session.isFingerprintUploaded()) {
            session.setStatus(SessionStatus.COMPLETED);
            session.setCompletedAt(Instant.now());
        }

        sessionRepository.save(session);

    }
}

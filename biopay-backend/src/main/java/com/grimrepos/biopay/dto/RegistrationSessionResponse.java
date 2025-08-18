package com.grimrepos.biopay.dto;

import com.grimrepos.biopay.entity.enums.SessionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationSessionResponse {
    private UUID sessionId;
    private UUID userId;
    private boolean inputValidated;
    private boolean fingerprintUploaded;
    private boolean templateGenerated;
    private boolean deviceConfigured;
    private String status;
    private Instant startedAt;
    private Instant completedAt;
}

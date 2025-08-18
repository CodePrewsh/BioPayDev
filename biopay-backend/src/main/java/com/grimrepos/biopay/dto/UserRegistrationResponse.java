package com.grimrepos.biopay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationResponse {
    private UUID userId;
    private UUID registrationSessionId;
    private UUID bankAccountId;
    private String message;
}

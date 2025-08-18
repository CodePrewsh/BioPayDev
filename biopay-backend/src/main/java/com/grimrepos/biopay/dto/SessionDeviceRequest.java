package com.grimrepos.biopay.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionDeviceRequest {
    @NotBlank(message = "Session ID is required")
    private String sessionId;

    @NotBlank(message = "Device type is required")
    @Size(max = 50,message = "Device type must be <= 50 characters")
    private String deviceType;

    @Size(max = 20,message = "OS version must be <= 20 characters")
    private String osVersion;

    @Size(max = 20,message = "App version must be <= 20 characters")
    private String appVersion;
}

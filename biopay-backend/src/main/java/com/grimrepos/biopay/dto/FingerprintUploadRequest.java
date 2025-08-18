package com.grimrepos.biopay.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FingerprintUploadRequest {
    //@NotBlank(message = "Session ID is required")
    private String sessionId;

    @NotNull(message = "Fingerprint data cannot be null")
    private String base64Template;
}

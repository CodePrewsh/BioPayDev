package com.grimrepos.biopay.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PinVerificationRequest {
    @NotBlank(message = "Bank account number is required")
    private String bankAccountNumber;
    @NotBlank(message = "Pin is required")
    private String pin;
}

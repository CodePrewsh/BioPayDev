package com.grimrepos.biopay.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {
    @NotBlank(message = "Bank account number is required")
    private String bankAccountNumber;

    @NotBlank(message = "Finger print base64 is required")
    private String fingerprintTemplate; //base 64

    @NotBlank(message = "Bank pin is required")
    private String bankPin;

    @NotNull(message = "Amount is required")
    private Double amount;

    private String currency;

}

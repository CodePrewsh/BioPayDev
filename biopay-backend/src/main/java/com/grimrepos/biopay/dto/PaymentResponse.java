package com.grimrepos.biopay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private UUID paymentId;
    private String bankAccountNumber;
    private Double amount;
    private String currency;
    private String status;   // SUCCESS / FAILED
    private String failureReason;
    private Instant timestamp;
    private Double remainingBalance;
}

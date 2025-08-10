package com.grimrepos.biopay.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"user", "paymentProfile"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "CardAuthAttempt")
public class CardAuthAttempt {
    //====== for attempt_id
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "attempt_id", nullable = false, updatable = false, columnDefinition = "CHAR(36)")
    @EqualsAndHashCode.Include
    private UUID id;

    //====== for user_id (foreign key)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //====== for profile_id (foreign key)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private PaymentProfile paymentProfile;

    //====== for attempt_time
    @CreationTimestamp
    @Column(name = "attempt_time", nullable = false, updatable = false)
    private LocalDateTime attemptTime;

    //====== for success
    @Column(name = "success", nullable = false)
    @Builder.Default
    private Boolean success = false;

    //====== for result_code
    @Column(name = "result_code")
    @Size(max = 50, message = "Result code must not exceed 50 characters")
    private String resultCode;

    //====== for failure_reason
    @Column(name = "failure_reason")
    @Size(max = 200, message = "Failure reason must not exceed 200 characters")
    private String failureReason;

    public void markAsSuccessful(String resultCode) {
        this.success = true;
        this.resultCode = resultCode;
        this.failureReason = null;
    }

    public void markAsFailed(String resultCode, String failureReason) {
        this.success = false;
        this.resultCode = resultCode;
        this.failureReason = failureReason;
    }
}
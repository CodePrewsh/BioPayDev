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
@ToString(exclude = {"user", "device"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "AuthenticationLog")
public class AuthenticationLog {
    //====== for auth_id
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "auth_id", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    @EqualsAndHashCode.Include
    private UUID id;

    //====== for user_id (foreign key)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //====== for device_id (foreign key)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    //====== for scan_time
    @CreationTimestamp
    @Column(name = "scan_time", nullable = false, updatable = false)
    private LocalDateTime scanTime;

    //====== for match_score
    @Column(name = "match_score", nullable = false)
    @DecimalMin(value = "0.0", message = "Match score must be non-negative")
    @DecimalMax(value = "1.0", message = "Match score must not exceed 1.0")
    private Float matchScore;

    //====== for result
    @Enumerated(EnumType.STRING)
    @Column(name = "result", nullable = false)
    private AuthResult result;
}

@Getter
enum AuthResult {
    SUCCESS("Success"),
    FAILURE("Failure");

    private final String displayName;

    AuthResult(String displayName) {
        this.displayName = displayName;
    }

    public static AuthResult fromString(String result) {
        if (result == null || result.trim().isEmpty()) {
            throw new IllegalArgumentException("Auth result cannot be null or empty");
        }

        String normalizedResult = result.trim().toUpperCase();

        try {
            return AuthResult.valueOf(normalizedResult);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                String.format("Invalid auth result: '%s'. Allowed values are SUCCESS, FAILURE", result)
            );
        }
    }
}

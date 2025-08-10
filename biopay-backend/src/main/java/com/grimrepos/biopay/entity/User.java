package com.grimrepos.biopay.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"fingerprintTemplates", "authenticationLogs", "transactions", "adminActionLogs", "cardAuthAttempts", "paymentProfiles"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "Users")
public class User {
    //====== for user_id
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    @Column(name = "user_id", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    @EqualsAndHashCode.Include
    private UUID id;

    //====== for name
    @Column(name = "name", nullable = false)
    @NotBlank(message = "Full Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    //====== for email
    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Please provide a valid email")
    @NotBlank(message = "Email is required")
    @EqualsAndHashCode.Include
    private String email;

    //====== for phone
    @Column(name = "phone")
    @Pattern(regexp = "^[+]?[1-9]\\d{1,14}$", message = "Invalid phone number format")
    private String phone;

    //====== for role
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @Builder.Default
    private UserRole role = UserRole.USER;

    //====== for created_at timestamp
    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP" , nullable = false, updatable = false)
    private LocalDateTime createdAt;

    //====== for relationships
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<FingerprintTemplate> fingerprintTemplates = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<AuthenticationLog> authenticationLogs = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Transaction> transactions = new ArrayList<>();

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<AdminActionLog> adminActionLogs = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<CardAuthAttempt> cardAuthAttempts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<PaymentProfile> paymentProfiles = new ArrayList<>();

    //====== custom
    public User(String name, String email, String phone, UserRole role) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role != null ? role : UserRole.USER;
    }

    public User(String name, String email, String phone, String role) {
        this(name, email, phone, UserRole.fromString(role));
    }

    //====== for role validation
    public void setRole(UserRole role) {
        if (role == null) {
            throw new IllegalArgumentException("User role cannot be null. Allowed user types are USER, ADMIN");
        }
        this.role = role;
    }

    public void setRole(String roleString) {
        if (roleString == null || roleString.trim().isEmpty()) {
            throw new IllegalArgumentException("User role cannot be null or empty. Allowed user types are USER, ADMIN");
        }
        this.role = UserRole.fromString(roleString.trim().toUpperCase());
    }

    public void updateProfile(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}

@Getter
enum UserRole {
    USER("User"),
    ADMIN("Admin");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public static UserRole fromString(String role) {
        if (role == null || role.trim().isEmpty()) {
            throw new IllegalArgumentException("User role cannot be null or empty");
        }

        String normalizedRole = role.trim().toUpperCase();

        try {
            return UserRole.valueOf(normalizedRole);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                String.format("Invalid user role: '%s'. Allowed values are USER, ADMIN", role)
            );
        }
    }

    public static boolean isValidRole(String role) {
        if (role == null || role.trim().isEmpty()) {
            return false;
        }
        try {
            fromString(role);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
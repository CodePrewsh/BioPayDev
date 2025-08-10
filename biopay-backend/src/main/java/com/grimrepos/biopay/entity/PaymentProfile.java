package com.grimrepos.biopay.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"user", "transactions", "cardAuthAttempts"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "PaymentProfile")
public class PaymentProfile {
    //====== for profile_id (primary key)
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "profile_id", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    @EqualsAndHashCode.Include
    private UUID id;

    //====== for user_id (foreign key)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //====== for yoco_customer_id
    @Column(name = "yoco_customer_id", unique = true)
    @Size(max = 100, message = "Yoco customer ID must not exceed 100 characters")
    private String yocoCustomerId;

    //====== for card_token
    @Column(name = "card_token")
    @Size(max = 255, message = "Card token must not exceed 255 characters")
    private String cardToken;

    //====== for label
    @Column(name = "label")
    @Size(max = 50, message = "Label must not exceed 50 characters")
    private String label;

    //====== for default_profile
    @Column(name = "default_profile", nullable = false)
    @Builder.Default
    private Boolean defaultProfile = false;

    //====== for created_at timestamp
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    //====== for last_used_at timestamp
    @UpdateTimestamp
    @Column(name = "last_used_at")
    private LocalDateTime lastUsedAt;

    //====== for relationships
    @OneToMany(mappedBy = "paymentProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "paymentProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CardAuthAttempt> cardAuthAttempts;

    public void markAsUsed() {
        this.lastUsedAt = LocalDateTime.now();
    }

    public void setAsDefault() {
        this.defaultProfile = true;
    }

    public void unsetAsDefault() {
        this.defaultProfile = false;
    }
}
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
@ToString(exclude = {"transaction"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "TransactionLog")
public class TransactionLog {
    //====== for log_id
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column( name = "log_id", nullable = false, updatable = false,  columnDefinition = "CHAR(36)")
    @EqualsAndHashCode.Include
    private UUID logId;

    //====== for transaction_id (foreign key)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    //====== for event
    @Column(name = "event", nullable = false)
    @NotBlank(message = "Event is required")
    @Size(max = 100, message = "Event must not exceed 100 characters")
    private String event;

    //====== for timestamp
    @CreationTimestamp
    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp;

    //====== for description
    @Column(name = "description", length = 500)
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;
}


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
@ToString(exclude = {"admin"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "AdminActionLog")
public class AdminActionLog {
    //====== for action_id
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "action_id", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    @EqualsAndHashCode.Include
    private UUID id;

    //====== for admin_id (foreign key)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;

    //====== for action
    @Column(name = "action", nullable = false)
    @NotBlank(message = "Action is required")
    @Size(max = 100, message = "Action must not exceed 100 characters")
    private String action;

    //====== for details
    @Column(name = "details", length = 1000)
    @Size(max = 1000, message = "Details must not exceed 1000 characters")
    private String details;

    //====== for action_time
    @CreationTimestamp
    @Column(name = "action_time", nullable = false, updatable = false)
    private LocalDateTime actionTime;
}
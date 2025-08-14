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
@ToString(exclude = {"user", "templateData"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "FingerprintTemplate")
public class FingerprintTemplate{
    //====== for template_id (primary key)
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "template_id", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    @EqualsAndHashCode.Include
    private UUID templateId;

    //====== for user_id (foreign key)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //====== for template_data
    @Lob
    @Column(name = "template_data", nullable = false)
    @NotNull(message = "template data is required")
    private byte[] templateData;

    //====== for created_at
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
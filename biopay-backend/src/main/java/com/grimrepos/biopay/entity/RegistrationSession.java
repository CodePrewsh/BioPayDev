package com.grimrepos.biopay.entity;

import com.grimrepos.biopay.entity.enums.SessionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "registration_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationSession {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "session_id",updatable = false,nullable = false)
    private UUID sessionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private boolean inputValidated;
    private boolean fingerprintUploaded;
    private boolean templateGenerated;
    private boolean deviceConfigured;

    @Enumerated(EnumType.STRING)
    private SessionStatus status;

    private Instant startedAt;
    private Instant completedAt;

    @OneToMany(mappedBy = "registrationSession",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<SessionDevice> devices = new ArrayList<>();
}

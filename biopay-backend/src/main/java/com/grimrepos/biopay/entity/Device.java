package com.grimrepos.biopay.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
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
@ToString(exclude = {"authenticationLogs"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "Device")
public class Device {
    //====== for device_id
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "device_id", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    @EqualsAndHashCode.Include
    private UUID id;

    //====== for name
    @Column(name = "name", nullable = false)
    @NotBlank(message = "Device name is required")
    @Size(max = 100, message = "Device name must not exceed 100 characters")
    private String name;

    //====== for location
    @Column(name = "location")
    @Size(max = 200, message = "Location must not exceed 200 characters")
    private String location;

    //====== for status
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private DeviceStatus status = DeviceStatus.INACTIVE;

    //====== for registered_at timestamp
    @CreationTimestamp
    @Column(name = "registered_at", nullable = false, updatable = false)
    private LocalDateTime registeredAt;

    //====== for relationship
    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AuthenticationLog> authenticationLogs;

    public void updateDevice(String name, String location, DeviceStatus status) {
        this.name = name;
        this.location = location;
        this.status = status;
    }
}

@Getter
enum DeviceStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private final String displayName;

    DeviceStatus(String displayName) {
        this.displayName = displayName;
    }

    public static DeviceStatus fromString(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Device status cannot be null or empty");
        }

        String normalizedStatus = status.trim().toUpperCase();

        try {
            return DeviceStatus.valueOf(normalizedStatus);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                String.format("Invalid device status: '%s'. Allowed values are ACTIVE, INACTIVE", status)
            );
        }
    }
}
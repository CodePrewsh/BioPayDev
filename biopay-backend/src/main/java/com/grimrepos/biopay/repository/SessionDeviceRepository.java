package com.grimrepos.biopay.repository;

import com.grimrepos.biopay.entity.SessionDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessionDeviceRepository extends JpaRepository<SessionDevice, UUID> {
}

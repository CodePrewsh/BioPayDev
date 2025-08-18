package com.grimrepos.biopay.repository;

import com.grimrepos.biopay.entity.RegistrationSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RegistrationSessionRepository extends JpaRepository<RegistrationSession, UUID> {
}

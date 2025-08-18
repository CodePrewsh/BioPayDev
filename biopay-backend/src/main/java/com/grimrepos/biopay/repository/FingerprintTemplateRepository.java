package com.grimrepos.biopay.repository;

import com.grimrepos.biopay.entity.FingerprintTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FingerprintTemplateRepository extends JpaRepository<FingerprintTemplate, UUID> {
    //List<FingerprintTemplate> findByUser_userId(UUID userId);
    Optional<FingerprintTemplate> findTopByUser_UserIdOrderByCreatedAtDesc(UUID userId);
}

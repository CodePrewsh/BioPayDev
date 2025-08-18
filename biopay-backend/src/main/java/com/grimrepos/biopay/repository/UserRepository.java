package com.grimrepos.biopay.repository;

import com.grimrepos.biopay.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByIdNumber(String idNumber);
    Optional<User> findByEmail(String email);
}

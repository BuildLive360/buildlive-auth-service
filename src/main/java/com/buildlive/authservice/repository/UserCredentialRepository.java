package com.buildlive.authservice.repository;

import com.buildlive.authservice.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserCredentialRepository extends JpaRepository<UserCredential, UUID> {

   Optional<UserCredential> findByName(String username);
}

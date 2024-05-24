package com.pajakbpkad.pajakbpkad.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pajakbpkad.pajakbpkad.models.PajakUser;

public interface PajakUserRepository extends JpaRepository<PajakUser, UUID>  {
    PajakUser findByUsername(String username);
}

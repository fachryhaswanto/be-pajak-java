package com.pajakbpkad.pajakbpkad.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pajakbpkad.pajakbpkad.models.KodeAkunPajak;

public interface KodeAkunPajakRepository extends JpaRepository<KodeAkunPajak, UUID> {
    
}

package com.pajakbpkad.pajakbpkad.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pajakbpkad.pajakbpkad.models.Dinas;



public interface DinasRepository extends JpaRepository<Dinas, UUID> {
    Dinas findByNamaDinas(String namaDinas);
}

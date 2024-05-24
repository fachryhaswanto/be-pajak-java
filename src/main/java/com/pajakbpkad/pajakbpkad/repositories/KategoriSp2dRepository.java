package com.pajakbpkad.pajakbpkad.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pajakbpkad.pajakbpkad.models.KategoriSp2d;

public interface KategoriSp2dRepository extends JpaRepository<KategoriSp2d, UUID> {

    KategoriSp2d findByNamaKategori(String namaKategori);
    
}

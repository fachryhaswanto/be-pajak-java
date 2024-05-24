package com.pajakbpkad.pajakbpkad.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pajakbpkad.pajakbpkad.models.KategoriSp2d;
import com.pajakbpkad.pajakbpkad.models.Pajak;
import com.pajakbpkad.pajakbpkad.models.PajakUser;

public interface PajakRepository extends JpaRepository<Pajak, UUID> {

    Pajak findByNtpn(String ntpn);
    Pajak findByBilling(String billing);
    
    @Query("SELECT p FROM Pajak p LEFT JOIN p.sp2d s WHERE s.kategoriSp2d = :kategoriSp2d ORDER BY p.createdAt DESC")
    List<Pajak> findPajakByKategoriSp2d(@Param("kategoriSp2d") KategoriSp2d kategoriSp2d);

    @Query("SELECT p FROM Pajak p LEFT JOIN p.sp2d s WHERE s.kategoriSp2d = :kategoriSp2d AND p.createdBy = :createdBy ORDER BY p.createdAt DESC")
    List<Pajak> findPajakByKategoriSp2dAndCreatedBy(@Param("kategoriSp2d") KategoriSp2d kategoriSp2d, @Param("createdBy") PajakUser pajakUser);
    
}

package com.pajakbpkad.pajakbpkad.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pajakbpkad.pajakbpkad.models.PajakGu;
import com.pajakbpkad.pajakbpkad.models.PajakUser;

import java.util.List;


public interface PajakGuRepository extends JpaRepository<PajakGu, UUID> {
    
    PajakGu findByNomorBukti(String nomorBukti);
    PajakGu findByNtpn(String ntpn);
    PajakGu findByBilling(String billing);
    List<PajakGu> findByCreatedByOrderByCreatedAtDesc(PajakUser createdBy);

}

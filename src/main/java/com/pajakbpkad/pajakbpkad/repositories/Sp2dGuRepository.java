package com.pajakbpkad.pajakbpkad.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pajakbpkad.pajakbpkad.models.PajakUser;
import com.pajakbpkad.pajakbpkad.models.Sp2dGu;


public interface Sp2dGuRepository extends JpaRepository<Sp2dGu, UUID> {

    Sp2dGu findByNomorSpm(String nomorSpm);
    Sp2dGu findByNomorSp2d(String nomorSp2d);
    List<Sp2dGu> findByCreatedByOrderByCreatedAtDesc(PajakUser createdBy);
    
}

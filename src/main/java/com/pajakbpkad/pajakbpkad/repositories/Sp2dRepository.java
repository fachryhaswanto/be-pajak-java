package com.pajakbpkad.pajakbpkad.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pajakbpkad.pajakbpkad.models.KategoriSp2d;
import com.pajakbpkad.pajakbpkad.models.PajakUser;
import com.pajakbpkad.pajakbpkad.models.Sp2d;

public interface Sp2dRepository extends JpaRepository<Sp2d, UUID> {

    Sp2d findByNomorSpm(String nomorSpm);
    Sp2d findByNomorSp2d(String nomorSp2d);
    List<Sp2d> findByKategoriSp2dOrderByCreatedAtDesc(KategoriSp2d kategoriSp2d);
    List<Sp2d> findByKategoriSp2dAndCreatedByOrderByCreatedAtDesc(KategoriSp2d kategoriSp2d, PajakUser pajakUser);

    //boleh juga seperti ini jika ingin menggunakan object sort pada saat di akses di service
    // List<Sp2d> findByKategoriSp2dOrderByCreatedAtDesc(KategoriSp2d kategoriSp2d, Sort sort);
    
}

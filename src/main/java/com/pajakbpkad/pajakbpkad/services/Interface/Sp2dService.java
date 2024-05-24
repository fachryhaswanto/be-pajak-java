package com.pajakbpkad.pajakbpkad.services.Interface;

import java.util.List;
import java.util.UUID;

import com.pajakbpkad.pajakbpkad.models.Sp2d;

public interface Sp2dService {

    Iterable<Sp2d> findAll();
    Sp2d findById(UUID id);
    List<Sp2d> findByKategoriSp2dAndOrCreatedBy(UUID kategoriSp2d, UUID pajakUserId);
    Sp2d createOrUpdate(Sp2d request);
    Boolean delete(UUID id);
    
}

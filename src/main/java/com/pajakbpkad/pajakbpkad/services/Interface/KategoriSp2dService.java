package com.pajakbpkad.pajakbpkad.services.Interface;

import java.util.UUID;

import com.pajakbpkad.pajakbpkad.models.KategoriSp2d;

public interface KategoriSp2dService {

    Iterable<KategoriSp2d> findAll();
    KategoriSp2d findById(UUID id);
    KategoriSp2d createOrUpdate(KategoriSp2d request);
    Boolean delete(UUID id);
    
}

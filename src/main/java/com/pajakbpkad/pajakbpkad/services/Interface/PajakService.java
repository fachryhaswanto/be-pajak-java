package com.pajakbpkad.pajakbpkad.services.Interface;

import java.util.List;
import java.util.UUID;

import com.pajakbpkad.pajakbpkad.models.Pajak;

public interface PajakService {

    Iterable<Pajak> findAll();
    Pajak findById(UUID id);
    List<Pajak> findPajakByKategoriSp2dAndOrCreatedBy(UUID kategoriSp2dId, UUID pajakUserId);
    Pajak createOrUpdate(Pajak request);
    Boolean delete(UUID id);
        
}

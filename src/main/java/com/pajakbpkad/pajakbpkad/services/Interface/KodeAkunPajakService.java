package com.pajakbpkad.pajakbpkad.services.Interface;

import java.util.UUID;

import com.pajakbpkad.pajakbpkad.models.KodeAkunPajak;

public interface KodeAkunPajakService {

    Iterable<KodeAkunPajak> findAll();
    KodeAkunPajak findById(UUID id);
    KodeAkunPajak createOrUpdate(KodeAkunPajak request);
    Boolean delete(UUID id);
    
}

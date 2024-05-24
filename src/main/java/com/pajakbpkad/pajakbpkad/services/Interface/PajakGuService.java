package com.pajakbpkad.pajakbpkad.services.Interface;

import java.util.List;
import java.util.UUID;

import com.pajakbpkad.pajakbpkad.models.PajakGu;

public interface PajakGuService {

    Iterable<PajakGu> findAll();
    PajakGu findById(UUID id);
    List<PajakGu> findByCreatedBy(UUID createdById);
    PajakGu createOrUpdate(PajakGu request);
    Boolean delete(UUID id);
    
}

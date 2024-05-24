package com.pajakbpkad.pajakbpkad.services.Interface;

import java.util.List;
import java.util.UUID;

import com.pajakbpkad.pajakbpkad.models.Sp2dGu;

public interface Sp2dGuService {

    Iterable<Sp2dGu> findAll();
    List<Sp2dGu> findByCreatedBy(UUID createdById);
    Sp2dGu findById(UUID id);
    Sp2dGu createOrUpdate(Sp2dGu request);
    Boolean delete(UUID id);
    
}

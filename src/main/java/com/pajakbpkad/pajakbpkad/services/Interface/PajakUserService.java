package com.pajakbpkad.pajakbpkad.services.Interface;

import java.util.UUID;

import com.pajakbpkad.pajakbpkad.models.PajakUser;

public interface PajakUserService {

    Iterable<PajakUser> findAll();
    PajakUser findById(UUID id);
    PajakUser createOrUpdate(PajakUser request);
    Boolean delete(UUID id);
}

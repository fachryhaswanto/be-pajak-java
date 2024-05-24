package com.pajakbpkad.pajakbpkad.services.Interface;


import java.util.UUID;

import com.pajakbpkad.pajakbpkad.models.Dinas;

public interface DinasService {
    Iterable<Dinas> findAll();
    Dinas findById(UUID id);
    Dinas createOrUpdate(Dinas request);
    Boolean delete(UUID id);

}

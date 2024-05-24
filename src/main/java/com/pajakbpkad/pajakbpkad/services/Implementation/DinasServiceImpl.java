package com.pajakbpkad.pajakbpkad.services.Implementation;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pajakbpkad.pajakbpkad.models.Dinas;
import com.pajakbpkad.pajakbpkad.repositories.DinasRepository;
import com.pajakbpkad.pajakbpkad.services.Interface.DinasService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DinasServiceImpl implements DinasService {

    @Autowired
    private DinasRepository dinasRepository;

    @Override
    public Dinas createOrUpdate(Dinas request) {
        if(request.getId() == null) {
            Dinas dinas = dinasRepository.findByNamaDinas(request.getNamaDinas());

            if(dinas != null) {
                return null;
            }
        }

        return dinasRepository.save(request);
    }

    @Override
    public Boolean delete(UUID id) {
        Optional<Dinas> dinas = dinasRepository.findById(id);

        if(dinas.isPresent()) {
            dinasRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Iterable<Dinas> findAll() {
        return dinasRepository.findAll(Sort.by("id").ascending());
    }

    @Override
    public Dinas findById(UUID id) {
        Optional<Dinas> dinas = dinasRepository.findById(id);

        if(dinas.isPresent()) {
            return dinas.get();
        } else {
            return null;
        }
    }

}

package com.pajakbpkad.pajakbpkad.services.Implementation;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pajakbpkad.pajakbpkad.models.KodeAkunPajak;
import com.pajakbpkad.pajakbpkad.repositories.KodeAkunPajakRepository;
import com.pajakbpkad.pajakbpkad.services.Interface.KodeAkunPajakService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class KodeAkunPajakServiceImpl implements KodeAkunPajakService {

    @Autowired
    private KodeAkunPajakRepository kodeAkunPajakRepository;

    @Override
    public KodeAkunPajak createOrUpdate(KodeAkunPajak request) {
        return kodeAkunPajakRepository.save(request);
    }

    @Override
    public Boolean delete(UUID id) {
        Optional<KodeAkunPajak> kodeAkunPajak = kodeAkunPajakRepository.findById(id);

        if(kodeAkunPajak.isPresent()) {
            kodeAkunPajakRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Iterable<KodeAkunPajak> findAll() {
        return kodeAkunPajakRepository.findAll();
    }

    @Override
    public KodeAkunPajak findById(UUID id) {
        Optional<KodeAkunPajak> kodeAkunPajak = kodeAkunPajakRepository.findById(id);

        if(kodeAkunPajak.isPresent()) {
            return kodeAkunPajak.get();
        } else {
            return null;
        }
    }

    
    
}

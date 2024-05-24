package com.pajakbpkad.pajakbpkad.services.Implementation;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pajakbpkad.pajakbpkad.models.KategoriSp2d;
import com.pajakbpkad.pajakbpkad.repositories.KategoriSp2dRepository;
import com.pajakbpkad.pajakbpkad.services.Interface.KategoriSp2dService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class KategoriSp2dServiceImpl implements KategoriSp2dService {

    @Autowired
    private KategoriSp2dRepository kategoriSp2dRepository;

    @Override
    public KategoriSp2d createOrUpdate(KategoriSp2d request) {
        
        if(request.getId() == null) {
            KategoriSp2d kategoriSp2d = kategoriSp2dRepository.findByNamaKategori(request.getNamaKategori());

            if(kategoriSp2d != null) {
                return null;
            }
        }

        return kategoriSp2dRepository.save(request);

    }

    @Override
    public Boolean delete(UUID id) {
        Optional<KategoriSp2d> result = kategoriSp2dRepository.findById(id);

        if(result.isPresent()) {
            kategoriSp2dRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Iterable<KategoriSp2d> findAll() {
        return kategoriSp2dRepository.findAll();
    }

    @Override
    public KategoriSp2d findById(UUID id) {
        Optional<KategoriSp2d> kategoriSp2d = kategoriSp2dRepository.findById(id);

        if(kategoriSp2d.isPresent()) {
            return kategoriSp2d.get();
        } else {
            return null;
        }
    }    
    
}

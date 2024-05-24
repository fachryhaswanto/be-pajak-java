package com.pajakbpkad.pajakbpkad.services.Implementation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pajakbpkad.pajakbpkad.exception.NomorSp2dExistException;
import com.pajakbpkad.pajakbpkad.exception.NomorSpmExistException;
import com.pajakbpkad.pajakbpkad.models.KategoriSp2d;
import com.pajakbpkad.pajakbpkad.models.PajakUser;
import com.pajakbpkad.pajakbpkad.models.Sp2d;
import com.pajakbpkad.pajakbpkad.repositories.Sp2dRepository;
import com.pajakbpkad.pajakbpkad.services.Interface.Sp2dService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class Sp2dServiceImpl implements Sp2dService {
    
    @Autowired
    private Sp2dRepository sp2dRepository;

    @Override
    public Sp2d createOrUpdate(Sp2d request) {
       
        if(request.getId() == null) {
            Sp2d resultNomorSpm = sp2dRepository.findByNomorSpm(request.getNomorSpm());

            if(resultNomorSpm != null) {
                throw new NomorSpmExistException("Nomor SPM sudah ada");
            }

            Sp2d resultNomorSp2d = sp2dRepository.findByNomorSp2d(request.getNomorSp2d());

            if(resultNomorSp2d != null) {
                throw new NomorSp2dExistException("Nomor SP2D sudah ada");
            }
        }

        return sp2dRepository.save(request);

    }

    @Override
    public Boolean delete(UUID id) {
        
        Optional<Sp2d> result = sp2dRepository.findById(id);
        
        if(result.isPresent()) {
            sp2dRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
        
    }

    @Override
    public Iterable<Sp2d> findAll() {
        return sp2dRepository.findAll(Sort.by("createdAt").descending());
    }

    @Override
    public Sp2d findById(UUID id) {
        Optional<Sp2d> result = sp2dRepository.findById(id);

        if(result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }

    @Override
    public List<Sp2d> findByKategoriSp2dAndOrCreatedBy(UUID kategoriSp2dId, UUID pajakUserId) {
        
        KategoriSp2d kategoriSp2d = new KategoriSp2d();
        PajakUser pajakUser = new PajakUser();

        kategoriSp2d.setId(kategoriSp2dId);
        
        if(pajakUserId != null) {
            pajakUser.setId(pajakUserId);
            
            return sp2dRepository.findByKategoriSp2dAndCreatedByOrderByCreatedAtDesc(kategoriSp2d, pajakUser);
        } else {
            return sp2dRepository.findByKategoriSp2dOrderByCreatedAtDesc(kategoriSp2d);
        }

    }

    
}

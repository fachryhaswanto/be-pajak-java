package com.pajakbpkad.pajakbpkad.services.Implementation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pajakbpkad.pajakbpkad.exception.NomorSp2dExistException;
import com.pajakbpkad.pajakbpkad.exception.NomorSpmExistException;
import com.pajakbpkad.pajakbpkad.models.PajakUser;
import com.pajakbpkad.pajakbpkad.models.Sp2dGu;
import com.pajakbpkad.pajakbpkad.repositories.Sp2dGuRepository;
import com.pajakbpkad.pajakbpkad.services.Interface.Sp2dGuService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class Sp2dGuServiceImpl implements Sp2dGuService {
    
    @Autowired
    private Sp2dGuRepository sp2dGuRepository;

    @Override
    public Sp2dGu createOrUpdate(Sp2dGu request) {

         if(request.getId() == null) {
            Sp2dGu resultNomorSpm = sp2dGuRepository.findByNomorSpm(request.getNomorSpm());

            if(resultNomorSpm != null) {
                throw new NomorSpmExistException("Nomor SPM sudah ada");
            }

            Sp2dGu resultNomorSp2d = sp2dGuRepository.findByNomorSp2d(request.getNomorSp2d());

            if(resultNomorSp2d != null) {
                throw new NomorSp2dExistException("Nomor SP2D sudah ada");
            }
        }

        return sp2dGuRepository.save(request);
    }

    @Override
    public Boolean delete(UUID id) {
        
        Optional<Sp2dGu> sp2dGu = sp2dGuRepository.findById(id);

        if(sp2dGu.isPresent()) {
            sp2dGuRepository.deleteById(id);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public Iterable<Sp2dGu> findAll() {
        return sp2dGuRepository.findAll();
    }

    @Override
    public Sp2dGu findById(UUID id) {
        Optional<Sp2dGu> sp2dGu = sp2dGuRepository.findById(id);

        if(sp2dGu.isPresent()) {
            return sp2dGu.get();
        } else {
            return null;
        }
    }

    @Override
    public List<Sp2dGu> findByCreatedBy(UUID createdById) {
        
        PajakUser pajakUser = new PajakUser();

        pajakUser.setId(createdById);

        return sp2dGuRepository.findByCreatedByOrderByCreatedAtDesc(pajakUser);

    }

    

    

}

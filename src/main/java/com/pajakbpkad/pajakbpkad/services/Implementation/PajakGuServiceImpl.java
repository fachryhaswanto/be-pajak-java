package com.pajakbpkad.pajakbpkad.services.Implementation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pajakbpkad.pajakbpkad.exception.BillingExistException;
import com.pajakbpkad.pajakbpkad.exception.NomorBuktiExistException;
import com.pajakbpkad.pajakbpkad.exception.NtpnExistException;
import com.pajakbpkad.pajakbpkad.models.PajakGu;
import com.pajakbpkad.pajakbpkad.models.PajakUser;
import com.pajakbpkad.pajakbpkad.repositories.PajakGuRepository;
import com.pajakbpkad.pajakbpkad.services.Interface.PajakGuService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PajakGuServiceImpl implements PajakGuService {

    @Autowired
    private PajakGuRepository pajakGuRepository;

    @Override
    public PajakGu createOrUpdate(PajakGu request) {
        
        if(request.getId() == null) {

            PajakGu resultNomorBukti = pajakGuRepository.findByNomorBukti(request.getNomorBukti());

            if(resultNomorBukti != null) {
                throw new NomorBuktiExistException("Nomor Bukti sudah ada");
            }

            PajakGu resultNtpn = pajakGuRepository.findByNtpn(request.getNtpn());

            if(resultNtpn != null) {
                throw new NtpnExistException("NTPN sudah ada");
            }

            PajakGu resultBilling = pajakGuRepository.findByBilling(request.getBilling());

            if(resultBilling != null) {
                throw new BillingExistException("Billing sudah ada");
            }
        }

        return pajakGuRepository.save(request);

    }

    @Override
    public Boolean delete(UUID id) {
        
        Optional<PajakGu> pajakGu = pajakGuRepository.findById(id);

        if(pajakGu.isPresent()) {
            pajakGuRepository.deleteById(id);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public Iterable<PajakGu> findAll() {
        return pajakGuRepository.findAll();
    }

    @Override
    public PajakGu findById(UUID id) {
        Optional<PajakGu> pajakGu = pajakGuRepository.findById(id);

        if(pajakGu.isPresent()) {
            return pajakGu.get();
        } else {
            return null;
        }
    }

    @Override
    public List<PajakGu> findByCreatedBy(UUID createdById) {
        PajakUser pajakUser = new PajakUser();

        pajakUser.setId(createdById);

        return pajakGuRepository.findByCreatedByOrderByCreatedAtDesc(pajakUser);
    }

    
    
    
}

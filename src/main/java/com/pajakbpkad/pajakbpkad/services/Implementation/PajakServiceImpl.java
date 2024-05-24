package com.pajakbpkad.pajakbpkad.services.Implementation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pajakbpkad.pajakbpkad.exception.BillingExistException;
import com.pajakbpkad.pajakbpkad.exception.NtpnExistException;
import com.pajakbpkad.pajakbpkad.models.KategoriSp2d;
import com.pajakbpkad.pajakbpkad.models.Pajak;
import com.pajakbpkad.pajakbpkad.models.PajakUser;
import com.pajakbpkad.pajakbpkad.repositories.PajakRepository;
import com.pajakbpkad.pajakbpkad.services.Interface.PajakService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PajakServiceImpl implements PajakService {

    @Autowired
    private PajakRepository pajakRepository;

    @Override
    public Pajak createOrUpdate(Pajak request) {
        
        if(request.getId() == null) {

            Pajak resultNtpn = pajakRepository.findByNtpn(request.getNtpn());

            if(resultNtpn != null) {
                throw new NtpnExistException("NTPN sudah ada");
            }

            Pajak resultBilling = pajakRepository.findByBilling(request.getBilling());

            if(resultBilling != null) {
                throw new BillingExistException("Billing sudah ada");
            }
        }

        return pajakRepository.save(request);

    }

    @Override
    public Boolean delete(UUID id) {
        
        Optional<Pajak> pajak = pajakRepository.findById(id);

        if(pajak.isPresent()) {
            pajakRepository.deleteById(id);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public Iterable<Pajak> findAll() {
        return pajakRepository.findAll();
    }

    @Override
    public Pajak findById(UUID id) {
        Optional<Pajak> pajak = pajakRepository.findById(id);

        if(pajak.isPresent()) {
            return pajak.get();
        } else {
            return null;
        }
    }

    @Override
    public List<Pajak> findPajakByKategoriSp2dAndOrCreatedBy(UUID kategoriSp2dId, UUID pajakUserId) {
        KategoriSp2d kategoriSp2d = new KategoriSp2d();
        PajakUser pajakUser = new PajakUser();

        kategoriSp2d.setId(kategoriSp2dId);
        if(pajakUserId != null) {
            pajakUser.setId(pajakUserId);

            return pajakRepository.findPajakByKategoriSp2dAndCreatedBy(kategoriSp2d, pajakUser);
        } else {
            return pajakRepository.findPajakByKategoriSp2d(kategoriSp2d);
        }

    }

    
    

}

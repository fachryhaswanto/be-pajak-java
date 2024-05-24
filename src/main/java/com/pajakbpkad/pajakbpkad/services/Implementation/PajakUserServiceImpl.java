package com.pajakbpkad.pajakbpkad.services.Implementation;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pajakbpkad.pajakbpkad.models.PajakUser;
import com.pajakbpkad.pajakbpkad.repositories.PajakUserRepository;
import com.pajakbpkad.pajakbpkad.services.Interface.PajakUserService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PajakUserServiceImpl implements PajakUserService {

    @Autowired
    private PajakUserRepository pajakUserRepository;

    // @Autowired
    // private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public PajakUser createOrUpdate(PajakUser request) {

        if(request.getId() == null) {
            PajakUser user = pajakUserRepository.findByUsername(request.getUsername());

            if(user != null) {
                return null;
            }
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(hashedPassword);

        return pajakUserRepository.save(request);
    }

    @Override
    public Boolean delete(UUID id) {
        Optional<PajakUser> result = pajakUserRepository.findById(id);
        
        if(result.isPresent()) {
            pajakUserRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Iterable<PajakUser> findAll() {
        return pajakUserRepository.findAll();
    }

    @Override
    public PajakUser findById(UUID id) {
        Optional<PajakUser> result = pajakUserRepository.findById(id);

        if(result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }

}

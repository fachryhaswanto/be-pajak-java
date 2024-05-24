package com.pajakbpkad.pajakbpkad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pajakbpkad.pajakbpkad.models.PajakUser;
import com.pajakbpkad.pajakbpkad.repositories.PajakUserRepository;

@Service
public class PajakUserDetailsService implements UserDetailsService {

    @Autowired
    private PajakUserRepository pajakUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username)  {
        PajakUser pajakUser = pajakUserRepository.findByUsername(username);

        if(pajakUser != null) {
            return pajakUser;
        } else {
            throw new UsernameNotFoundException("Username tidak ditemukan");
        }
    }

    
    
}

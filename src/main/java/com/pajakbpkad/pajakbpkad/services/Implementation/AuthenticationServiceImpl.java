package com.pajakbpkad.pajakbpkad.services.Implementation;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.pajakbpkad.pajakbpkad.dto.request.token.TokenData;
import com.pajakbpkad.pajakbpkad.dto.response.ResponseToken;
import com.pajakbpkad.pajakbpkad.exception.UsernameNotFoundException;
import com.pajakbpkad.pajakbpkad.models.PajakUser;
import com.pajakbpkad.pajakbpkad.repositories.PajakUserRepository;
import com.pajakbpkad.pajakbpkad.services.Interface.AuthenticationService;
import com.pajakbpkad.pajakbpkad.utils.JWTUtils;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService{
    
    @Autowired
    private PajakUserRepository pajakUserRepository;
    
    @Autowired
    private JWTUtils jwtUtils;

    // @Autowired
    // private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseToken login(String username, String password) {
        
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        PajakUser pajakUser = pajakUserRepository.findByUsername(username);

        if(pajakUser == null) {
            throw new UsernameNotFoundException("Username tidak ditemukan");
        }

        String token = jwtUtils.generateToken(pajakUser);
        String refrestoken = jwtUtils.generateRefreshToken(new HashMap<>(), pajakUser);

        ResponseToken responseToken = new ResponseToken();
        
        responseToken.setToken(token);
        responseToken.setRefreshToken(refrestoken);
        responseToken.setExpirationTime("24Hours");

        return responseToken;
        
        //YANG LAMA
        // PajakUser user = pajakUserRepository.findByUsername(username);
        
        // if(user == null) {
        //     throw new UsernameNotFoundException("Username tidak ditemukan");
        // } else {
        //     Boolean isPasswordMatched = passwordEncoder.matches(password, user.getPassword());

        //     if(isPasswordMatched) {
        //         return true;
        //     } else {
        //         throw new InvalidPasswordException("Password tidak valid");
        //     }
        // }
    }

    @Override
    public ResponseToken refreshToken(TokenData tokenData) {

        String username = jwtUtils.extractUsername(tokenData.getToken());
        PajakUser pajakUser = pajakUserRepository.findByUsername(username);

        if(pajakUser == null ) {
            throw new UsernameNotFoundException("Username tidak ditemukan");
        }

        if(jwtUtils.isTokenValid(tokenData.getToken(), pajakUser)) {
            ResponseToken responseToken = new ResponseToken();

            String token = jwtUtils.generateToken(pajakUser);

            responseToken.setToken(token);
            responseToken.setRefreshToken(tokenData.getToken());
            responseToken.setExpirationTime("24Hours");

            return responseToken;

        }

        return null;
        
    }

    @Override
    public String extractUsername(String token) {
        return jwtUtils.extractUsername(token);
    }

    

}

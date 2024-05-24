package com.pajakbpkad.pajakbpkad.services.Interface;

import com.pajakbpkad.pajakbpkad.dto.request.token.TokenData;
import com.pajakbpkad.pajakbpkad.dto.response.ResponseToken;

public interface AuthenticationService {
    
    ResponseToken login(String username, String password);
    ResponseToken refreshToken(TokenData tokenData);
    String extractUsername(String token);

    

}

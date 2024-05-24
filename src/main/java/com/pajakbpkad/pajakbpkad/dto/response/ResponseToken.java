package com.pajakbpkad.pajakbpkad.dto.response;

import lombok.Data;

@Data
public class ResponseToken {

    private String token;
    private String refreshToken;
    private String expirationTime;
    
}

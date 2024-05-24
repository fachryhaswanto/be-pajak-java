package com.pajakbpkad.pajakbpkad.dto.request.token;

import lombok.Data;

@Data
public class TokenData {
    private String token;
    private String refreshToken;
    private String expirationTime;
}


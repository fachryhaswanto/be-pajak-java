package com.pajakbpkad.pajakbpkad.dto.request.pajakuser;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginData {

    @NotEmpty(message = "Username tidak boleh kosong")
    private String username;

    @NotEmpty(message = "Password tidak boleh kosong")
    private String password;
    
}

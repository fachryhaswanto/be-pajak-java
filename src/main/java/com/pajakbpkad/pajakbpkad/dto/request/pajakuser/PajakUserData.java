package com.pajakbpkad.pajakbpkad.dto.request.pajakuser;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PajakUserData {

    private UUID id;

    @NotEmpty(message = "Username tidak boleh kosong")
    private String username;

    @NotEmpty(message = "Password tidak boleh kosong")
    private String password;

    @NotEmpty(message = "Role tidak boleh kosong")
    private String role;
    
}

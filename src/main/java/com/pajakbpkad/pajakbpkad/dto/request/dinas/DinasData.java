package com.pajakbpkad.pajakbpkad.dto.request.dinas;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DinasData {

    private UUID id;

    @NotEmpty(message = "Nama Dinas tidak boleh kosong")
    private String namaDinas;
    
}

package com.pajakbpkad.pajakbpkad.dto.request.kodeakunpajak;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class KodeAkunPajakData {

    private UUID id;

    @NotEmpty(message = "Kode tidak boleh kosong")
    private String kode;
    
    @NotEmpty(message = "Nama Akun tidak boleh kosong")
    private String namaAkun;
    
}

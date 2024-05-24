package com.pajakbpkad.pajakbpkad.dto.request.kategorisp2d;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class KategoriSp2dData {

    private UUID id;

    @NotEmpty(message = "Nama Kategori tidak boleh kosong")
    private String namaKategori;
    
}

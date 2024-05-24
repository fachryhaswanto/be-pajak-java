package com.pajakbpkad.pajakbpkad.dto.request.sp2d;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FindSp2dByKategoriSp2dAndOrUserData {
    
    @NotNull(message = "Kategori SP2D Id tidak boleh kosong")
    UUID kategoriSp2dId;
    
    UUID createById;

}

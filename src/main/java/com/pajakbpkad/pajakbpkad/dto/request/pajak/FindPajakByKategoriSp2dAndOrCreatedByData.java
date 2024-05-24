package com.pajakbpkad.pajakbpkad.dto.request.pajak;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FindPajakByKategoriSp2dAndOrCreatedByData {

    @NotNull(message = "kategoriSp2dId tidak boleh kosong")
    UUID kategoriSp2dId;

    UUID createdById;
    
}

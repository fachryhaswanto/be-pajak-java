package com.pajakbpkad.pajakbpkad.dto.request.sp2d;

import java.util.UUID;

import com.pajakbpkad.pajakbpkad.models.KategoriSp2d;
import com.pajakbpkad.pajakbpkad.models.PajakUser;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Sp2dData {

    private UUID id;

    @NotEmpty(message = "Nomor SPM tidak boleh kosong")
    private String nomorSpm;

    @NotEmpty(message = "Tanggal SPM tidak boleh kosong")
    private String tanggalSpm;

    @NotEmpty(message = "Nomor SP2D tidak boleh kosong")
    private String nomorSp2d;

    @NotEmpty(message = "Tanggal SP2D tidak boleh kosong")
    private String tanggalSp2d;

    private Long nilaiSp2d;

    @NotEmpty(message = "Keterangan tidak boleh kosong")
    private String keterangan;

    @NotEmpty(message = "Tahun tidak boleh kosong")
    private String tahun;

    @NotNull(message = "Kategori SP2D tidak boleh kosong")
    private KategoriSp2d kategoriSp2d;

    private PajakUser createdBy;

    private PajakUser updatedBy;    
    
}

package com.pajakbpkad.pajakbpkad.dto.request.sp2dgu;

import java.util.UUID;

import com.pajakbpkad.pajakbpkad.models.PajakGu;
import com.pajakbpkad.pajakbpkad.models.PajakUser;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Sp2dGuData {

    private UUID id;

    @NotNull(message = "Pajak GU tidak boleh kosong")
    private PajakGu pajakGu;

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

    private PajakUser createdBy;

    private PajakUser updatedBy;
    
}

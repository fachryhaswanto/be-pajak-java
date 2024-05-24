package com.pajakbpkad.pajakbpkad.dto.request.pajak;

import java.util.UUID;

import com.pajakbpkad.pajakbpkad.models.KodeAkunPajak;
import com.pajakbpkad.pajakbpkad.models.PajakUser;
import com.pajakbpkad.pajakbpkad.models.Sp2d;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PajakData {

    private UUID id;

    @NotNull(message = "SP2D tidak boleh kosong")
    private Sp2d sp2d;

    @NotNull(message = "Kode Akun Pajak tidak boleh kosong")
    private KodeAkunPajak kodeAkunPajak;

    private Long nilaiPajak;

    @NotEmpty(message = "NTPN tidak boleh kosong")
    private String ntpn;

    @NotEmpty(message = "Billing tidak boleh kosong")
    private String billing;

    @NotEmpty(message = "Tanggal Setor Pajak tidak boleh kosong")
    private String tanggalSetorPajak;

    @NotEmpty(message = "NPWP tidak boleh kosong")
    private String npwp;

    @NotEmpty(message = "Penerima tidak boleh kosong")
    private String penerima;

    @NotEmpty(message = "Tahun tidak boleh kosong")
    private String tahun;

    private String buktiBayar;

    @NotEmpty(message = "Validate Status tidak boleh kosong")
    private String validateStatus;

    private PajakUser validatedBy;

    private String catatanValidasi;

    private PajakUser createdBy;

    private PajakUser updatedBy;
    
}

package com.pajakbpkad.pajakbpkad.dto.request.sp2dgu;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FindSp2dGuByCreatedBy {

    @NotNull(message = "createdById tidak boleh kosong")
    UUID createdById;
    
}

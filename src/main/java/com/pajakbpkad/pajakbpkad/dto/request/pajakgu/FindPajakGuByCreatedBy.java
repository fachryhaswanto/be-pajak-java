package com.pajakbpkad.pajakbpkad.dto.request.pajakgu;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FindPajakGuByCreatedBy {

    @NotNull(message = "createdById tidak boleh kosong")
    UUID createdById;
    
}

package com.pajakbpkad.pajakbpkad.dto.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ResponseLogin {
    private Boolean status;
    private List<String> messages = new ArrayList<>();
}

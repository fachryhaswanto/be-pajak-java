package com.pajakbpkad.pajakbpkad.dto.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ResponseData<T> {
    private Boolean status;
    private List<String> messages = new ArrayList<>();
    private T payload;
}

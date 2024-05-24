package com.pajakbpkad.pajakbpkad.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "key")
public class KeyProperties {

    private String jwtKey;
    
}

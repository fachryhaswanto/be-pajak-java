package com.pajakbpkad.pajakbpkad.services.Interface;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3Object;

public interface S3Service {
    
    void uploadFile(String keyName, MultipartFile file);
    S3Object getFile(String keyName);
    void deleteFile(String keyName);

}

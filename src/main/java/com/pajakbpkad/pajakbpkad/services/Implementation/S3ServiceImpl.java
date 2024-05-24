package com.pajakbpkad.pajakbpkad.services.Implementation;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.pajakbpkad.pajakbpkad.services.Interface.S3Service;

@Service
public class S3ServiceImpl implements S3Service {

    private AmazonS3 s3client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public S3ServiceImpl(AmazonS3 s3client) {
        this.s3client = s3client;
    }

    @Override
    public S3Object getFile(String keyName) {
            Boolean result = s3client.doesObjectExist(bucketName, keyName);

            if(result == true) {
                return s3client.getObject(bucketName, keyName);
            } else {
                return null;
            }

    }

    @Override
    public void uploadFile(String keyName, MultipartFile file) {
        try {
            // var putObjectResult = s3client.putObject(bucketName, keyName, file.getInputStream(), null);
            s3client.putObject(bucketName, keyName, file.getInputStream(), null);
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteFile(String keyName) {
        s3client.deleteObject(bucketName, keyName);
    }

}

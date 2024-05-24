package com.pajakbpkad.pajakbpkad.controllers;

import java.io.IOException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.pajakbpkad.pajakbpkad.services.Interface.S3Service;

@RestController
@RequestMapping("/s3")
public class S3Controller {

    @Autowired
    private S3Service s3Service;

    @PostMapping(path = "/upload/{fileName}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    private ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("fileName") String fileName) throws IOException {
        
        s3Service.uploadFile(fileName, file);

        return ResponseEntity.ok().body("File uploaded");
    }

    @GetMapping("/download/{fileName}")
    private ResponseEntity<byte[]> downloadFile(@PathVariable("fileName") String fileName) throws IOException {

        S3Object s3Object = s3Service.getFile(fileName);

        if(s3Object == null) {
            new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

        byte[] bytes = IOUtils.toByteArray(objectInputStream);

        String namaFile = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentLength(bytes.length);
        httpHeaders.setContentDispositionFormData("attachment", namaFile);

        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);


        // return ResponseEntity.ok()
        //         .contentType(MediaType.APPLICATION_OCTET_STREAM)
        //         .body(new InputStreamResource(s3Service.getFile(fileName).getObjectContent()));
    }

    @GetMapping("/view/{fileName}")
    private ResponseEntity<InputStreamResource> viewFile(@PathVariable("fileName") String fileName) {

        S3Object s3Object = s3Service.getFile(fileName);
        S3ObjectInputStream content = s3Object.getObjectContent();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+fileName+"\"")
                .body(new InputStreamResource(content));
    }

    @DeleteMapping("/delete/{fileName}")
    private ResponseEntity<String> deleteFile(@PathVariable("fileName") String fileName) {
            s3Service.deleteFile(fileName);
            return ResponseEntity.ok().body("Delete file berhasil");
    }
    
}

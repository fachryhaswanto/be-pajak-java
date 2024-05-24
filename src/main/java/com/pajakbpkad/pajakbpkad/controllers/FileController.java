package com.pajakbpkad.pajakbpkad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pajakbpkad.pajakbpkad.services.Interface.FileService;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    //SAVING TO SYSTEM FILE
    @PostMapping("/upload")
    private void upload(@RequestParam MultipartFile file) {
        fileService.save(file);
    }

    //GET FILE FROM SYSTEM
    @GetMapping("/get/{fileName}")
    private ResponseEntity<Resource> getFile(@PathVariable("fileName") String filenName) {
        Resource resource = fileService.getFile(filenName);

        if(resource != null) {
            return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                                .body(resource);
        }

        return ResponseEntity.internalServerError().build();
    }
    
}

package com.pajakbpkad.pajakbpkad.services.Implementation;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pajakbpkad.pajakbpkad.services.Interface.FileService;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.path}")
    private String filePath;

    //SAVING FILE TO SYSTEM FILE
    @Override
    public void save(MultipartFile file) {
        String dir = System.getProperty("user.dir") + "/" + filePath;

        try {
            file.transferTo(new File(dir + "/" + file.getOriginalFilename()));
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
        
    }

    //GET FILE FROM SYSTEM FILE
    @Override
    public Resource getFile(String filename) {
        String dir = System.getProperty("user.dir") + "/" + filePath + "/" + filename;

        Path path = Paths.get(dir);

        try {
            Resource resource = new UrlResource(path.toUri());

            return resource;
        } catch(MalformedURLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    


    
}

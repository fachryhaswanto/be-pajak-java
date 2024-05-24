package com.pajakbpkad.pajakbpkad.controllers;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pajakbpkad.pajakbpkad.dto.request.pajakuser.PajakUserData;
import com.pajakbpkad.pajakbpkad.dto.response.ResponseData;
import com.pajakbpkad.pajakbpkad.models.PajakUser;
import com.pajakbpkad.pajakbpkad.services.Interface.PajakUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class PajakUserController {

    @Autowired
    private PajakUserService pajakUserService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    private Iterable<PajakUser> findAll() {
        return pajakUserService.findAll();
    }

    @GetMapping("/{id}")
    private ResponseEntity<PajakUser> findById(@PathVariable("id") UUID id) {
        PajakUser user = pajakUserService.findById(id);

        if(user != null)  {
            // return ResponseEntity.ok().body(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    private ResponseEntity<ResponseData<PajakUser>> createUser(@Valid @RequestBody PajakUserData userData, Errors errors) {
        
        ResponseData<PajakUser> responseData = new ResponseData<>();

        if(errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            
            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);
        }

        PajakUser userMapped = modelMapper.map(userData, PajakUser.class);

        PajakUser user = pajakUserService.createOrUpdate(userMapped);
        
        if(user == null) {
            responseData.setStatus(false);
            responseData.getMessages().add("Username sudah ada");
            responseData.setPayload(user);

            return ResponseEntity.badRequest().body(responseData);
        }

        responseData.setStatus(true);
        responseData.setPayload(user);

        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }

    @PutMapping
    private ResponseEntity<ResponseData<PajakUser>> updateUser(@Valid @RequestBody PajakUserData userData, Errors errors) {

        ResponseData<PajakUser> responseData = new ResponseData<>();

        if(errors.hasErrors()) {
            for(ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }

            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);
        }

        PajakUser user = modelMapper.map(userData, PajakUser.class);

        responseData.setStatus(true);
        responseData.setPayload(pajakUserService.createOrUpdate(user));

        return ResponseEntity.ok().body(responseData);

    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteUser(@PathVariable("id") UUID id) {
        try {
            Boolean result = pajakUserService.delete(id);
            
            if(result) {
                return ResponseEntity.ok().body("Data berhasil dihapus");
            } else {
                return new ResponseEntity<>("Data tidak ditemukan", HttpStatus.NOT_FOUND);
            }
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Data gagal dihapus : related data exist");
        }
            
    }

    // @PostMapping("/login")
    // private ResponseEntity<ResponseLogin> userLogin(@Valid @RequestBody LoginData loginData, Errors errors) {

    //     ResponseLogin responseLogin = new ResponseLogin();

    //     if(errors.hasErrors()) {
    //         for(ObjectError error : errors.getAllErrors()) {
    //             responseLogin.getMessages().add(error.getDefaultMessage());
    //         }

    //         responseLogin.setStatus(false);
    //         return ResponseEntity.badRequest().body(responseLogin);
    //     }

    //     try {
    //         if(pajakUserService.login(loginData.getUsername(), loginData.getPassword())) {
    //             responseLogin.setStatus(true);
    //             return ResponseEntity.ok().body(responseLogin);
    //         }
    //     } catch(UsernameNotFoundException e) {
    //         responseLogin.setStatus(false);
    //         responseLogin.getMessages().add(e.getMessage());
    //         return ResponseEntity.badRequest().body(responseLogin);
    //     } catch(InvalidPasswordException e)  {
    //         responseLogin.setStatus(false);
    //         responseLogin.getMessages().add(e.getMessage());
    //         return ResponseEntity.badRequest().body(responseLogin);
    //     }

    //     responseLogin.setStatus(false);
    //     responseLogin.getMessages().add("Login failed");
    //     return ResponseEntity.badRequest().body(responseLogin);
    // }
    
}

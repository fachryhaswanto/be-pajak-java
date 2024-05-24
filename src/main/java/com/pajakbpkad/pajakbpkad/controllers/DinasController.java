package com.pajakbpkad.pajakbpkad.controllers;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.pajakbpkad.pajakbpkad.dto.request.dinas.DinasData;
import com.pajakbpkad.pajakbpkad.dto.response.ResponseData;
import com.pajakbpkad.pajakbpkad.models.Dinas;
import com.pajakbpkad.pajakbpkad.services.Interface.DinasService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/dinas")
public class DinasController {
    
    @Autowired
    private DinasService dinasService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    private Iterable<Dinas> findAll() {
        return dinasService.findAll();
    }

    @GetMapping("/{id}")
    private ResponseEntity<Dinas> findById(@PathVariable("id") UUID id) {
        Dinas dinas = dinasService.findById(id);

        if(dinas != null) {
            return ResponseEntity.ok().body(dinas);
        } else {
            return new ResponseEntity<>(dinas, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    private ResponseEntity<ResponseData<Dinas>> createDinas(@Valid @RequestBody DinasData dinasData, Errors errors) {

        ResponseData<Dinas> responseData = new ResponseData<>();

        if(errors.hasErrors()) {
            for(ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }

            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);
        }

        Dinas dinasMapped = modelMapper.map(dinasData, Dinas.class);

        Dinas dinas = dinasService.createOrUpdate(dinasMapped);

        if(dinas == null) {
            responseData.getMessages().add("Nama Dinas sudah ada");
            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);
        }

        responseData.setStatus(true);
        responseData.setPayload(dinas);

        return new ResponseEntity<>(responseData, HttpStatus.CREATED);

    }
    
    @PutMapping
    private ResponseEntity<ResponseData<Dinas>> updateDinas(@Valid @RequestBody DinasData dinasData, Errors errors) {

        ResponseData<Dinas> responseData = new ResponseData<>();

        if(errors.hasErrors()) {
            for(ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }

            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);
        }

        Dinas dinasMapped = modelMapper.map(dinasData, Dinas.class);

        responseData.setStatus(true);
        responseData.setPayload(dinasService.createOrUpdate(dinasMapped));

        return ResponseEntity.ok().body(responseData);

    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteDinas(@PathVariable("id") UUID id) {

        Boolean result = dinasService.delete(id);

        if(result) {
            return ResponseEntity.ok().body("Data berhasil dihapus");
        } else {
            return new ResponseEntity<>("Data tidak ditemukan", HttpStatus.NOT_FOUND);
        }

    }

}

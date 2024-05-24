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

import com.pajakbpkad.pajakbpkad.dto.request.kodeakunpajak.KodeAkunPajakData;
import com.pajakbpkad.pajakbpkad.dto.response.ResponseData;
import com.pajakbpkad.pajakbpkad.models.KodeAkunPajak;
import com.pajakbpkad.pajakbpkad.services.Interface.KodeAkunPajakService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/kodeakunpajak")
public class KodeAkunPajakController {

    @Autowired
    private KodeAkunPajakService kodeAkunPajakService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    private Iterable<KodeAkunPajak> findAll() {
        return kodeAkunPajakService.findAll();
    }

    @GetMapping("/{id}")
    private ResponseEntity<KodeAkunPajak> findById(@PathVariable("id") UUID id) {
        KodeAkunPajak kodeAkunPajak = kodeAkunPajakService.findById(id);

        if(kodeAkunPajak != null) {
            return ResponseEntity.ok().body(kodeAkunPajak);
        } else {
            return new ResponseEntity<>(kodeAkunPajak, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    private ResponseEntity<ResponseData<KodeAkunPajak>> createKodeAkunPajak(@Valid @RequestBody KodeAkunPajakData kodeAkunPajakData, Errors errors) {

        ResponseData<KodeAkunPajak> responseData = new ResponseData<>();

        if(errors.hasErrors()) {
            for(ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }

            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);
        }

        KodeAkunPajak kodeAkunPajakMapped = modelMapper.map(kodeAkunPajakData, KodeAkunPajak.class);

        responseData.setStatus(true);
        responseData.setPayload(kodeAkunPajakService.createOrUpdate(kodeAkunPajakMapped));

        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }

    @PutMapping
    private ResponseEntity<ResponseData<KodeAkunPajak>> updateKodeAkunPajak(@Valid @RequestBody KodeAkunPajakData kodeAkunPajakData, Errors errors) {

        ResponseData<KodeAkunPajak> responseData = new ResponseData<>();

        if(errors.hasErrors()) {
            for(ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }

            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);
        }

        KodeAkunPajak kodeAkunPajakMapped = modelMapper.map(kodeAkunPajakData, KodeAkunPajak.class);

        responseData.setStatus(true);
        responseData.setPayload(kodeAkunPajakService.createOrUpdate(kodeAkunPajakMapped));

        return ResponseEntity.ok().body(responseData);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteKodeAkunPajak(@PathVariable("id") UUID id) {
        try {
            Boolean result = kodeAkunPajakService.delete(id);
            
            if(result) {
                return ResponseEntity.ok().body("Data berhasil dihapus");
            } else {
                return new ResponseEntity<>("Data tidak ditemukan", HttpStatus.NOT_FOUND);
            }
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Data gagal dihapus : related data exist");
        }

    }
    
}

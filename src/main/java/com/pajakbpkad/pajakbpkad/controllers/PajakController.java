package com.pajakbpkad.pajakbpkad.controllers;

import java.util.List;
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

import com.pajakbpkad.pajakbpkad.dto.request.pajak.FindPajakByKategoriSp2dAndOrCreatedByData;
import com.pajakbpkad.pajakbpkad.dto.request.pajak.PajakData;
import com.pajakbpkad.pajakbpkad.dto.response.ResponseData;
import com.pajakbpkad.pajakbpkad.exception.BillingExistException;
import com.pajakbpkad.pajakbpkad.exception.NtpnExistException;
import com.pajakbpkad.pajakbpkad.models.Pajak;
import com.pajakbpkad.pajakbpkad.services.Interface.PajakService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pajak")
public class PajakController {

    @Autowired
    private PajakService pajakService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    private Iterable<Pajak> findAll() {
        return pajakService.findAll();
    }

    @GetMapping("/{id}")
    private ResponseEntity<Pajak> findById(@PathVariable("id") UUID id) {
        Pajak pajak = pajakService.findById(id);

        if(pajak != null) {
            return ResponseEntity.ok().body(pajak);
        } else {
            return new ResponseEntity<>(pajak, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    private ResponseEntity<ResponseData<Pajak>> createPajak(@Valid @RequestBody PajakData pajakData,  Errors errors) {

        ResponseData<Pajak> responseData = new ResponseData<>();

        if(errors.hasErrors()) {
            for(ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }

            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);
        }

        Pajak pajakMapped = modelMapper.map(pajakData, Pajak.class);

        try {
            Pajak pajak = pajakService.createOrUpdate(pajakMapped);

            responseData.setStatus(true);
            responseData.setPayload(pajak);

            return new ResponseEntity<>(responseData, HttpStatus.CREATED);

        } catch (NtpnExistException e) {
            responseData.getMessages().add(e.getMessage());
            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);

        } catch (BillingExistException e) {
            responseData.getMessages().add(e.getMessage());
            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);
        }

    }

    @PutMapping
    private ResponseEntity<ResponseData<Pajak>> updatePajak(@Valid @RequestBody PajakData pajakData, Errors errors) {

        ResponseData<Pajak> responseData = new ResponseData<>();

        if(errors.hasErrors()) {
            for(ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }

            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);
        }

        Pajak pajakMapped = modelMapper.map(pajakData, Pajak.class);

        responseData.setStatus(true);
        responseData.setPayload(pajakService.createOrUpdate(pajakMapped));

        return ResponseEntity.ok().body(responseData);

    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deletePajak(@PathVariable("id") UUID id) {
        Boolean result = pajakService.delete(id);

        if(result){
            return ResponseEntity.ok().body("Data berhasil dihapus");
        } else {
            return new ResponseEntity<>("Data tidak ditemukan", HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/search/kategorisp2d")
    private ResponseEntity<ResponseData<List<Pajak>>> findPajakByKategoriSp2d(@Valid @RequestBody FindPajakByKategoriSp2dAndOrCreatedByData params, Errors errors) {

        ResponseData<List<Pajak>> responseData = new ResponseData<>();

        if(errors.hasErrors()) {
            for(ObjectError error : errors.getAllErrors()){
                responseData.getMessages().add(error.getDefaultMessage());
            }

            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);
        }

        responseData.setStatus(true);
        responseData.setPayload(pajakService.findPajakByKategoriSp2dAndOrCreatedBy(params.getKategoriSp2dId(), params.getCreatedById()));

        return ResponseEntity.ok().body(responseData);

    }

    
}

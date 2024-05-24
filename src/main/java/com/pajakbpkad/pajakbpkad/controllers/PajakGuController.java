package com.pajakbpkad.pajakbpkad.controllers;

import java.util.List;
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

import com.pajakbpkad.pajakbpkad.dto.request.pajakgu.FindPajakGuByCreatedBy;
import com.pajakbpkad.pajakbpkad.dto.request.pajakgu.PajakGuData;
import com.pajakbpkad.pajakbpkad.dto.response.ResponseData;
import com.pajakbpkad.pajakbpkad.exception.BillingExistException;
import com.pajakbpkad.pajakbpkad.exception.NomorBuktiExistException;
import com.pajakbpkad.pajakbpkad.exception.NtpnExistException;
import com.pajakbpkad.pajakbpkad.models.PajakGu;
import com.pajakbpkad.pajakbpkad.services.Interface.PajakGuService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pajakgu")
public class PajakGuController {

    @Autowired
    private PajakGuService pajakGuService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    private Iterable<PajakGu> findAll() {
        return pajakGuService.findAll();
    }

    @GetMapping("/{id}")
    private ResponseEntity<PajakGu> findById(@PathVariable("id") UUID id) {
        
        PajakGu pajakGu = pajakGuService.findById(id);

        if(pajakGu != null) {
            return ResponseEntity.ok().body(pajakGu);
        } else {
            return new ResponseEntity<>(pajakGu, HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping
    private ResponseEntity<ResponseData<PajakGu>> createPajakGu(@Valid @RequestBody PajakGuData pajakGuData, Errors errors) {

        ResponseData<PajakGu> responseData = new ResponseData<>();

        if(errors.hasErrors()) {
            for(ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }

            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);
        }

        PajakGu pajakGuMapped = modelMapper.map(pajakGuData, PajakGu.class);

        try {

            PajakGu pajakGu = pajakGuService.createOrUpdate(pajakGuMapped);

            responseData.setStatus(true);
            responseData.setPayload(pajakGu);

            return new ResponseEntity<>(responseData, HttpStatus.CREATED);

        } catch (NomorBuktiExistException e) {
            responseData.getMessages().add(e.getMessage());
            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);

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
    private ResponseEntity<ResponseData<PajakGu>> updatePajakGu(@Valid @RequestBody PajakGuData pajakGuData, Errors errors) {

        ResponseData<PajakGu> responseData = new ResponseData<>();

        if(errors.hasErrors()) {
            for(ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }

            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);
        }

        PajakGu pajakGuMapped = modelMapper.map(pajakGuData, PajakGu.class);

        responseData.setStatus(false);
        responseData.setPayload(pajakGuService.createOrUpdate(pajakGuMapped));

        return ResponseEntity.ok().body(responseData);

    }
    
    @DeleteMapping("/{id}")
    private ResponseEntity<String> deletePajakGu(@PathVariable("id") UUID id) {

        try {

            Boolean result = pajakGuService.delete(id);

            if(result) {
                return ResponseEntity.ok().body("Data berhasil dihapus");
            } else {
                return new ResponseEntity<>("Data tidak ditemukan", HttpStatus.NOT_FOUND);
            }
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Data gagal dihapus : data related exist");

        }

    }

    @PostMapping("/search/createdby")
    private ResponseEntity<ResponseData<List<PajakGu>>> findPajakGuByCreatedBy(@Valid @RequestBody FindPajakGuByCreatedBy findPajakGuByCreatedBy, Errors errors) {

        ResponseData<List<PajakGu>> responseData = new ResponseData<>();

        if(errors.hasErrors()) {
            for(ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }

            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);
        }

        responseData.setStatus(true);
        responseData.setPayload(pajakGuService.findByCreatedBy(findPajakGuByCreatedBy.getCreatedById()));

        return ResponseEntity.ok().body(responseData);

    }
    
}

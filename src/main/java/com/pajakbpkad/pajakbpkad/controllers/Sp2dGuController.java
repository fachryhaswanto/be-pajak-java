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

import com.pajakbpkad.pajakbpkad.dto.request.sp2dgu.FindSp2dGuByCreatedBy;
import com.pajakbpkad.pajakbpkad.dto.request.sp2dgu.Sp2dGuData;
import com.pajakbpkad.pajakbpkad.dto.response.ResponseData;
import com.pajakbpkad.pajakbpkad.exception.NomorSp2dExistException;
import com.pajakbpkad.pajakbpkad.exception.NomorSpmExistException;
import com.pajakbpkad.pajakbpkad.models.Sp2dGu;
import com.pajakbpkad.pajakbpkad.services.Interface.Sp2dGuService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/sp2dgu")
public class Sp2dGuController {

    @Autowired
    private Sp2dGuService sp2dGuService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    private Iterable<Sp2dGu> findAll() {
        return sp2dGuService.findAll();
    }

    @GetMapping("/{id}")
    private ResponseEntity<Sp2dGu> findById(@PathVariable("id") UUID id) {
        Sp2dGu sp2dGu = sp2dGuService.findById(id);

        if(sp2dGu != null) {
            return ResponseEntity.ok().body(sp2dGu);
        } else {
            return new ResponseEntity<>(sp2dGu, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    private ResponseEntity<ResponseData<Sp2dGu>> createSp2dGu(@Valid @RequestBody Sp2dGuData sp2dGuData, Errors errors) {

        ResponseData<Sp2dGu> responseData = new ResponseData<>();

        if(errors.hasErrors()) {
            for(ObjectError error: errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }

            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);
        }

        Sp2dGu sp2dGuMapped = modelMapper.map(sp2dGuData, Sp2dGu.class);

        try {

            Sp2dGu sp2dGu = sp2dGuService.createOrUpdate(sp2dGuMapped);

            responseData.setStatus(true);
            responseData.setPayload(sp2dGu);

            return new ResponseEntity<>(responseData, HttpStatus.CREATED);

        } catch (NomorSpmExistException e) {
            responseData.getMessages().add(e.getMessage());
            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);

        } catch (NomorSp2dExistException e) {
            responseData.getMessages().add(e.getMessage());
            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);
        }
    }

    @PutMapping
    private ResponseEntity<ResponseData<Sp2dGu>> updateSp2dGu(@Valid @RequestBody Sp2dGuData sp2dGuData, Errors errors) {

        ResponseData<Sp2dGu> responseData = new ResponseData<>();

        if(errors.hasErrors()) {
            for(ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            
            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);
        }

        Sp2dGu sp2dGuMapped = modelMapper.map(sp2dGuData, Sp2dGu.class);

        responseData.setStatus(true);
        responseData.setPayload(sp2dGuService.createOrUpdate(sp2dGuMapped));

        return ResponseEntity.ok().body(responseData);

    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteSp2dGu(@PathVariable("id") UUID id) {

        try {
            Boolean result = sp2dGuService.delete(id);

            if(result) {
                return ResponseEntity.ok().body("Data berhasil dihapus");
            } else {
                return new ResponseEntity<>("Data tidak ditemukan", HttpStatus.NOT_FOUND);
            }

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Data gagal dihapus : related data exist");
        }

    }

    @PostMapping("/search/createdby")
    private ResponseEntity<ResponseData<List<Sp2dGu>>> findByCreatedBy(@Valid @RequestBody FindSp2dGuByCreatedBy findSp2dGuByCreatedBy, Errors errors) {

        ResponseData<List<Sp2dGu>> responseData = new ResponseData<>();

        if(errors.hasErrors()) {
            for(ObjectError error : errors.getAllErrors()){
                responseData.getMessages().add(error.getDefaultMessage());
            }

            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);
        }

        responseData.setStatus(true);
        responseData.setPayload(sp2dGuService.findByCreatedBy(findSp2dGuByCreatedBy.getCreatedById()));

        return ResponseEntity.ok().body(responseData);

    }
    
}

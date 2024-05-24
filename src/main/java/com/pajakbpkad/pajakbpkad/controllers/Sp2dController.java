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

import com.pajakbpkad.pajakbpkad.dto.request.sp2d.FindSp2dByKategoriSp2dAndOrUserData;
import com.pajakbpkad.pajakbpkad.dto.request.sp2d.Sp2dData;
import com.pajakbpkad.pajakbpkad.dto.response.ResponseData;
import com.pajakbpkad.pajakbpkad.exception.NomorSp2dExistException;
import com.pajakbpkad.pajakbpkad.exception.NomorSpmExistException;
import com.pajakbpkad.pajakbpkad.models.Sp2d;
import com.pajakbpkad.pajakbpkad.services.Interface.Sp2dService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/sp2d")
public class Sp2dController {
    
    @Autowired
    private Sp2dService sp2dService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    private Iterable<Sp2d> findAll() {
        return sp2dService.findAll();
    }

    @GetMapping("/{id}")
    private ResponseEntity<Sp2d> findById(@PathVariable("id") UUID id) {
        Sp2d sp2d = sp2dService.findById(id);

        if(sp2d != null) {
            return ResponseEntity.ok().body(sp2d);
        } else {
            return new ResponseEntity<>(sp2d, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    private ResponseEntity<ResponseData<Sp2d>> createSp2d(@Valid @RequestBody Sp2dData sp2dData, Errors errors) {

        ResponseData<Sp2d> responseData = new ResponseData<>();

        if(errors.hasErrors()) {
            for(ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }

            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);
        }

        Sp2d sp2dMapped = modelMapper.map(sp2dData, Sp2d.class);

        try {
            Sp2d sp2d = sp2dService.createOrUpdate(sp2dMapped);
            
            responseData.setStatus(true);
            responseData.setPayload(sp2d);

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
    private ResponseEntity<ResponseData<Sp2d>> updateSp2d(@Valid @RequestBody Sp2dData sp2dData, Errors errors) {

        ResponseData<Sp2d> responseData = new ResponseData<>();

        if(errors.hasErrors()) {
            for(ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }

            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);
        }

        Sp2d sp2dMapped = modelMapper.map(sp2dData, Sp2d.class);
        
        responseData.setStatus(true);
        responseData.setPayload(sp2dService.createOrUpdate(sp2dMapped));

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteSp2d(@PathVariable("id") UUID id) {
        try {
            Boolean result = sp2dService.delete(id);
            
            if(result) {
                return ResponseEntity.ok().body("Data berhasil dihapus");
            } else {
                return new ResponseEntity<>("Data tidak ditemukan", HttpStatus.NOT_FOUND);
            }
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Data gagal dihapus : related data exist");
        }
    }

    @PostMapping("/search/kategorisp2d")
    private ResponseEntity<ResponseData<List<Sp2d>>> findByKategoriSp2dIdAndOrPajakUser(@RequestBody FindSp2dByKategoriSp2dAndOrUserData params, Errors errors) {

        ResponseData<List<Sp2d>> responseData = new ResponseData<>();

        if(errors.hasErrors()) {
            for(ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }

            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);
        }

        responseData.setStatus(true);
        responseData.setPayload(sp2dService.findByKategoriSp2dAndOrCreatedBy(params.getKategoriSp2dId(), params.getCreateById()));

        return ResponseEntity.ok().body(responseData);

       
    }

}

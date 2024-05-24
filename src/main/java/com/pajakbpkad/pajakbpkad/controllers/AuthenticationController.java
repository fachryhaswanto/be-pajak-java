package com.pajakbpkad.pajakbpkad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pajakbpkad.pajakbpkad.dto.request.pajakuser.LoginData;
import com.pajakbpkad.pajakbpkad.dto.response.ResponseData;
import com.pajakbpkad.pajakbpkad.dto.response.ResponseToken;
import com.pajakbpkad.pajakbpkad.exception.UsernameNotFoundException;
import com.pajakbpkad.pajakbpkad.services.PajakUserDetailsService;
import com.pajakbpkad.pajakbpkad.services.Interface.AuthenticationService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private PajakUserDetailsService pajakUserDetailsService;

    //LOGIN MENGGUNAKAN COOKIES
    @PostMapping("/login")
    private ResponseEntity<ResponseData<ResponseToken>> loginUser(@Valid @RequestBody LoginData loginData, HttpServletResponse response, Errors errors) {
        
        ResponseData<ResponseToken> responseData = new ResponseData<>();

        if(errors.hasErrors()) {
            for(ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }

            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);
        }

        try {

            ResponseToken responseToken = authenticationService.login(loginData.getUsername(), loginData.getPassword());

            ResponseCookie cookie = ResponseCookie.from("pajak-cookie", responseToken.getToken())
                        .httpOnly(true)
                        .secure(false)
                        .path("/")
                        .maxAge(18000) //5 hours
                        .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            responseData.getMessages().add("Berhasil login");
            responseData.setStatus(true);
            responseData.setPayload(null);

            return ResponseEntity.ok().body(responseData);

        } catch (UsernameNotFoundException e) {
            responseData.getMessages().add(e.getMessage());
            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);
        }

    }

    //LOGIN MENGGUNAKAN AUTHORIZATION HEADER (BEARER TOKEN)
    // @PostMapping("/login")
    // private ResponseEntity<ResponseData<ResponseToken>> loginUser(@Valid @RequestBody LoginData loginData, Errors errors) {
        
    //     ResponseData<ResponseToken> responseData = new ResponseData<>();

    //     if(errors.hasErrors()) {
    //         for(ObjectError error : errors.getAllErrors()) {
    //             responseData.getMessages().add(error.getDefaultMessage());
    //         }

    //         responseData.setStatus(false);
    //         responseData.setPayload(null);

    //         return ResponseEntity.badRequest().body(responseData);
    //     }

    //     try {

    //         ResponseToken responseToken = authenticationService.login(loginData.getUsername(), loginData.getPassword());
            
    //         responseData.setStatus(true);
    //         responseData.setPayload(responseToken);

    //         return ResponseEntity.ok().body(responseData);

    //     } catch (UsernameNotFoundException e) {
    //         responseData.getMessages().add(e.getMessage());
    //         responseData.setStatus(false);
    //         responseData.setPayload(null);

    //         return ResponseEntity.badRequest().body(responseData);
    //     }

    // }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletResponse response) {

        Cookie deleteCookie = new Cookie("pajak-cookie", null);

        deleteCookie.setMaxAge(0);
        deleteCookie.setPath("/");

        response.addCookie(deleteCookie);

        return ResponseEntity.ok().body("Berhasil logout");

    }

    @GetMapping("/session")
    public ResponseEntity<UserDetails> sessionUser(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();

        String cookieValue = "";

        if(cookies != null)  {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("pajak-cookie")) {
                    cookieValue = cookie.getValue();
                }
            }

            if(cookieValue == "") {
                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
            }

            String username = authenticationService.extractUsername(cookieValue);

            UserDetails userDetails = pajakUserDetailsService.loadUserByUsername(username);

            return ResponseEntity.ok().body(userDetails);


        }

        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);



    }
    
}

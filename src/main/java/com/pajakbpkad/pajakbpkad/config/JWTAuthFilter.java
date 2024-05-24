package com.pajakbpkad.pajakbpkad.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pajakbpkad.pajakbpkad.services.PajakUserDetailsService;
import com.pajakbpkad.pajakbpkad.utils.JWTUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PajakUserDetailsService pajakUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //IMPLEMENTASI JWT MENGGUNAKAN COOKIE
        String token = null;
        String username = null;

        if(request.getCookies() != null) {
            for(Cookie cookie : request.getCookies()) {
                if(cookie.getName().equals("pajak-cookie")) {
                    token = cookie.getValue();
                }
            }
        }

        if(token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        username = jwtUtils.extractUsername(token);

        if(username != null) {
            UserDetails userDetails = pajakUserDetailsService.loadUserByUsername(username);
            if(jwtUtils.isTokenValid(token, userDetails)) {

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);



        //IMPLEMENTASI JWT YANG DISERTAKAN MENGGUNAKAN BEARER TOKEN
        // final String authHeader = request.getHeader("Authorization");

        // final String jwtToken;
        // final String username;

        // if(authHeader == null || authHeader.isBlank()) {
        //     filterChain.doFilter(request, response);
        //     return;
        // }

        // jwtToken = authHeader.substring(7);
        // username = jwtUtils.extractUsername(jwtToken);

        // if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        //     UserDetails userDetails = pajakUserDetailsService.loadUserByUsername(username);

        //     if(jwtUtils.isTokenValid(jwtToken, userDetails)) {
        //         SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        //         UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        //         token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        //         securityContext.setAuthentication(token);

        //         SecurityContextHolder.setContext(securityContext);
        //     }
        // }

        // filterChain.doFilter(request, response);
    }
}

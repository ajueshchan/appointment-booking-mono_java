package com.arjun.appointment.controller;

import com.arjun.appointment.security.token.JwtTokenGeneration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/v1")
public class TokenProviderController {

    private final JwtTokenGeneration jwtTokenGeneration;

    public TokenProviderController(JwtTokenGeneration jwtTokenGeneration){
        this.jwtTokenGeneration = jwtTokenGeneration;
    }

    @GetMapping("/token/{id}")
    public String getToken(@PathVariable("id") String emailId){
      return jwtTokenGeneration.generateToken(emailId);
    }
}

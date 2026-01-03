package com.arjun.appointment.controller;

import com.arjun.appointment.security.token.JwtTokenExtraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth/v1")
public class TokenValidatorController {

    @Autowired
    JwtTokenExtraction jwtTokenExtraction;

    @GetMapping("/test")
    public String getUserName(){
        String token = "eyJhbGciOiJIUzM4NCJ9.eyJmaXJzdE5hbWUiOiJBbml0YSIsImxhc3ROYW1lIjoiR3VwdGEiLCJyb2xlIjoiQ1VTVE9NRVIiLCJwaG9uZSI6Iis5MTYwNjk5MDUwNTciLCJ1c2VySWQiOjk5OCwic3ViIjoidXNlcjk5OEBleGFtcGxlLmNvbSIsImlhdCI6MTc2NzQxNTA5MiwiZXhwIjoxNzY3NDE1MTUyfQ.0-MHVSNPQqtkh2GUqTVtMbnUyl4RvyOcy58RWCcOYlZ5rq55lvkt37HtE4Us9z5w";
        return jwtTokenExtraction.extractUserName(token);
    }

}

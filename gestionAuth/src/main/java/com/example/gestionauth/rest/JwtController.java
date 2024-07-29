package com.example.gestionauth.rest;

import com.example.gestionauth.persistence.entity.JwtRequest;
import com.example.gestionauth.persistence.entity.JwtResponse;
import com.example.gestionauth.services.Implementation.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @PostMapping({"/authenticate"})
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        return jwtService.createJwtToken(jwtRequest);
    }
}

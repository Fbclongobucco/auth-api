package com.buccodev.auth_api.controller;

import com.buccodev.auth_api.dto.AuthDTO;
import com.buccodev.auth_api.model.User;
import com.buccodev.auth_api.services.AuthenticatorService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final AuthenticatorService authenticatorService;

    public AuthenticationController(AuthenticationManager authenticationManager, AuthenticatorService authenticatorService) {
        this.authenticationManager = authenticationManager;
        this.authenticatorService = authenticatorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String auth(@RequestBody AuthDTO authDTO){

        var userAuthenticationToken = new UsernamePasswordAuthenticationToken(authDTO.email(), authDTO.password());

        authenticationManager.authenticate(userAuthenticationToken);

        return authenticatorService.getToken(authDTO) ;
    }

}

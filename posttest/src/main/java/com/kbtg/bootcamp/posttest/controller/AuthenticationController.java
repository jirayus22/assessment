package com.kbtg.bootcamp.posttest.controller;
import com.google.gson.Gson;
import com.kbtg.bootcamp.posttest.entity.UsersEntity;
import com.kbtg.bootcamp.posttest.repository.UsersRepository;
import com.kbtg.bootcamp.posttest.service.AuthenticationUserDetailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

@RestController
@Log4j2
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationUserDetailService authenticationUserDetailService;

    public AuthenticationController(AuthenticationManager authenticationManager, AuthenticationUserDetailService authenticationUserDetailService) {
        this.authenticationManager = authenticationManager;
        this.authenticationUserDetailService = authenticationUserDetailService;
    }

    @PostMapping(value = "/authenticate")
    public String authenticated(@RequestBody UsersEntity usersEntity) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usersEntity.getUsername(), usersEntity.getPassword()));
        return authenticationUserDetailService.generateJwt(usersEntity.getUsername());
    }

}
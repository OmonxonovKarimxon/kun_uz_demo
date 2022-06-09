package com.company.controller;


import com.company.dto.AuthDto;
import com.company.dto.ProfileDto;
import com.company.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDto authDTO){
        ProfileDto login = authService.login(authDTO);
        return ResponseEntity.ok(). body(login);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody ProfileDto profileDto){
        String registration = authService.registration(profileDto);
        return ResponseEntity.ok().body(registration);
    }
}

package com.company.controller;

import com.company.dto.*;
import com.company.service.AuthService;
import com.company.service.ProfileService;
import com.company.util.HttpHeaderUtil;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationDTO dto) {
        String response = authService.registration(dto);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ProfileDTO> login(@RequestBody AuthDTO dto) {
        ProfileDTO profileDto = authService.login(dto);
        return ResponseEntity.ok(profileDto);
    }
    @PostMapping("/verification")
    public ResponseEntity<String> login(@RequestBody VerificationDTO dto) {
        String response = authService.verification(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/email/verification/{id}")
    public ResponseEntity<String> login2(@PathVariable("id") Integer id) {
        String response = authService.emailVerification(id);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/resendSms/{phone}")
    public ResponseEntity<ResponseInfoDTO> resendSms(@PathVariable("phone") String phone) {
        ResponseInfoDTO response = authService.resendSms(phone);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resendEmail/{jwt}")
    public ResponseEntity<?> resendEmail(@PathVariable("jwt") String jwt) {
        Integer profileId = JwtUtil.decode(jwt);

        ResponseInfoDTO responseInfoDTO = authService.resendEmail(profileId);
        return ResponseEntity.ok().body(responseInfoDTO);
    }
}

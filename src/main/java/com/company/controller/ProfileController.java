package com.company.controller;

import com.company.dto.ProfileDto;
import com.company.enums.ProfileRole;
import com.company.service.ProfileService;
import com.company.utils.JwtUtil;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/create")
    public ResponseEntity<?> registration(@RequestBody ProfileDto dto,
                                          @RequestHeader("Authorization") String jwt) {
         JwtUtil.decode(jwt, ProfileRole.ADMIN);
        ProfileDto profileDTO = profileService.create(dto);
        return ResponseEntity.ok(profileDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody ProfileDto dto,
                                          @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        ProfileDto profileDTO = profileService.update(dto);
        return ResponseEntity.ok(profileDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
       List<ProfileDto> list = profileService.profileList();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/changeStatus")
    public ResponseEntity<?> changeStatus(@RequestBody ProfileDto dto,
                                    @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        ProfileDto profileDTO = profileService.changeStatus(dto);
        return ResponseEntity.ok(profileDTO);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody ProfileDto dto,
                                          @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        ProfileDto profileDTO = profileService.delete(dto);
        return ResponseEntity.ok(profileDTO);
    }

}

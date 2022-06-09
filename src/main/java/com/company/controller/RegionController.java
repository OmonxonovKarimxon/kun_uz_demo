package com.company.controller;

import com.company.dto.ProfileDto;
import com.company.dto.RegionDto;
import com.company.enums.ProfileRole;
import com.company.service.RegioneService;
import com.company.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegioneService regioneService;

    @PostMapping("/create")
    public ResponseEntity<?> registration(@RequestBody RegionDto dto,
                                          @RequestHeader("Authorization") String jwt) {
         JwtUtil.decode(jwt, ProfileRole.ADMIN);
        RegionDto regionDto = regioneService.create(dto);
        return ResponseEntity.ok(regionDto);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody RegionDto dto,
                                          @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        RegionDto regionDto = regioneService.update(dto);
        return ResponseEntity.ok(regionDto);
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
       List<RegionDto> list = regioneService.regionList();
        return ResponseEntity.ok(list);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody RegionDto dto,
                                          @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
       String javob =regioneService.delete(dto);
        return ResponseEntity.ok( ).body(javob);
    }

}

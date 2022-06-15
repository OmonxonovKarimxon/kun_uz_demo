package com.company.controller;

import com.company.dto.ProfileDTO;
import com.company.enums.LangEnum;
import com.company.enums.ProfileRole;
import com.company.service.ProfileService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/profile")
@RestController
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody ProfileDTO profileDto,
                                    @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        ProfileDTO dto = profileService.create(profileDto);
        return ResponseEntity.ok().body(dto);
    }


    @PutMapping("/detail")
    public ResponseEntity<?> update(@RequestBody ProfileDTO dto,
                                    @RequestHeader("Authorization") String jwt) {
        Integer profileId = JwtUtil.decode(jwt);
        profileService.update(profileId, dto);
        return ResponseEntity.ok().body("Sucsessfully updated");
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Integer id,
                                     @RequestBody ProfileDTO dto,
                                     @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        profileService.update(id, dto);
        return ResponseEntity.ok().body("Succsessfully updated");
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                     @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        profileService.delete(id);
        return ResponseEntity.ok().body("Sucsessfully deleted");
    }

    @GetMapping("/list")
    private ResponseEntity<?> pagination(@RequestHeader("authorization") String jwt,
                                         @RequestParam(value = "page", defaultValue = "1") Integer page,
                                         @RequestParam(value = "size", defaultValue = "5") Integer size) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        PageImpl response = profileService.pagination(page, size );
        return ResponseEntity.ok().body(response);

    }

}

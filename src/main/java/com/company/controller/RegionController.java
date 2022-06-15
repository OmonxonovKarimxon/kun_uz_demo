package com.company.controller;

import com.company.dto.RegionDto;
import com.company.enums.LangEnum;
import com.company.enums.ProfileRole;
import com.company.service.RegionService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/region")
@RestController
public class RegionController {
    @Autowired
    private RegionService regionService;

    // PUBLIC
    @GetMapping("")
    public ResponseEntity<List<RegionDto>> getListRegion(@RequestHeader(value = "Accept-Laguage", defaultValue = "uz")LangEnum lang)
    {
        List<RegionDto> list = regionService.getList(lang);
        return ResponseEntity.ok().body(list);
    }

    // SECURE
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody RegionDto regionDto, @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        regionService.create(regionDto);
        return ResponseEntity.ok().body("SuccsessFully created");
    }

    @GetMapping("/admin")
    public ResponseEntity<List<RegionDto>> getlist(@RequestHeader("Authorization") String jwt,
                                                   @RequestHeader(value = "Accept-Laguage", defaultValue = "uz")LangEnum lang) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        List<RegionDto> list = regionService.getListOnlyForAdmin(lang);
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Integer id,
                                     @RequestBody RegionDto dto,
                                     @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        regionService.update(id, dto);
        return ResponseEntity.ok().body("Succsessfully updated");
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                     @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        regionService.delete(id);
        return ResponseEntity.ok().body("Successfully deleted");
    }
    @GetMapping("/pagination")
    public ResponseEntity<?> getPagination(@RequestParam(value = "page", defaultValue = "2") int page,
                                           @RequestParam(value = "size", defaultValue = "5") int size,
                                           @RequestHeader(value = "Accept-Laguage", defaultValue = "uz")LangEnum lang) {
        PageImpl response = regionService.pagination(page, size, lang);
        return ResponseEntity.ok().body(response);
    }


}

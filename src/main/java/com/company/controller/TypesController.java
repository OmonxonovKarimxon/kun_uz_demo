package com.company.controller;

import com.company.dto.article.TypesDTO;
import com.company.enums.LangEnum;
import com.company.enums.ProfileRole;
import com.company.service.TypesService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/types")
@RestController
public class TypesController {
    @Autowired
    private TypesService typesService;

    // PUBLIC
    @GetMapping("")
    public ResponseEntity<?> getListRegion(@RequestHeader(value = "Accept-Laguage", defaultValue = "uz")LangEnum lang)
    {
        List<TypesDTO> list = typesService.getList(lang);
        return ResponseEntity.ok().body(list);
    }

    // SECURE
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody TypesDTO dto, @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        typesService.create(dto);
        return ResponseEntity.ok().body("SuccsessFully created");
    }

    @GetMapping( "/admin" +
            "" +
            "" +
            "")
    public ResponseEntity<?> getlist(@RequestHeader("Authorization") String jwt,
                                     @RequestHeader(value = "Accept-Laguage", defaultValue = "uz")LangEnum lang) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        List<TypesDTO> list = typesService.getListOnlyForAdmin(lang);
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Integer id,
                                     @RequestBody TypesDTO dto,
                                     @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        typesService.update(id, dto);
        return ResponseEntity.ok().body("Succsessfully updated");
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                     @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        typesService.delete(id);
        return ResponseEntity.ok().body("Successfully deleted");
    }
    @GetMapping("/pagination")
    public ResponseEntity<?> getPagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                           @RequestParam(value = "size", defaultValue = "5") int size,
                                           @RequestHeader(value = "Accept-Laguage", defaultValue = "uz")LangEnum lang) {
        PageImpl response = typesService.pagination(page, size, lang);
        return ResponseEntity.ok().body(response);
    }


}

package com.company.controller;

import com.company.dto.CategoryDTO;
import com.company.dto.RegionDto;
import com.company.enums.LangEnum;
import com.company.enums.ProfileRole;
import com.company.service.CategoryService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/category")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    // PUBLIC
    @GetMapping("")
    public ResponseEntity<?> getListRegion(@RequestHeader(value = "Accept-Laguage", defaultValue = "uz") LangEnum lang)
    {
        List<CategoryDTO> list = categoryService.getList(lang);
        return ResponseEntity.ok().body(list);
    }

    // SECURED
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CategoryDTO categoryDto,
                                    @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        categoryService.create(categoryDto);
        return ResponseEntity.ok().body("SuccsessFully created");
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getlist(@RequestHeader("Authorization") String jwt,
                                                   @RequestHeader(value = "Accept-Laguage", defaultValue = "uz")LangEnum lang) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        List<CategoryDTO> list = categoryService.getListOnlyForAdmin(lang);
        return ResponseEntity.ok().body(list);
    }


    @PutMapping("/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Integer id,
                                     @RequestBody CategoryDTO dto,
                                     @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        categoryService.update(id, dto);
        return ResponseEntity.ok().body("Succsessfully updated");
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                     @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        categoryService.delete(id);
        return ResponseEntity.ok().body("Sucsessfully deleted");
    }

    @GetMapping("/pagination")
    public ResponseEntity<?> getPagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                           @RequestParam(value = "size", defaultValue = "5") int size,
                                           @RequestHeader(value = "Accept-Laguage", defaultValue = "uz")LangEnum lang) {
        PageImpl response = categoryService.pagination(page, size, lang);
        return ResponseEntity.ok().body(response);
    }

}

package com.company.controller;

import com.company.dto.CategoryDto;
import com.company.dto.RegionDto;
import com.company.enums.ProfileRole;
import com.company.service.CategoryService;
import com.company.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<?> registration(@RequestBody CategoryDto dto,
                                          @RequestHeader("Authorization") String jwt) {
         JwtUtil.decode(jwt, ProfileRole.ADMIN);
        CategoryDto regionDto = categoryService.create(dto);
        return ResponseEntity.ok(regionDto);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody CategoryDto dto,
                                          @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        CategoryDto regionDto = categoryService.update(dto);
        return ResponseEntity.ok(regionDto);
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
       List<CategoryDto> list = categoryService.categoryList();
        return ResponseEntity.ok(list);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody CategoryDto dto,
                                          @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
       String javob = categoryService.delete(dto);
        return ResponseEntity.ok( ).body(javob);
    }

}

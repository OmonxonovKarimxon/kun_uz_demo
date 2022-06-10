package com.company.controller;

import com.company.dto.CategoryDto;
import com.company.dto.RegionDto;
import com.company.enums.ProfileRole;
import com.company.service.CategoryService;
import com.company.service.RegionService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<CategoryDto>> getListCategory() {
        List<CategoryDto> list = categoryService.getList();
        return ResponseEntity.ok().body(list);
    }

    // SECURED
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CategoryDto categoryDto,
                                    @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        categoryService.create(categoryDto);
        return ResponseEntity.ok().body("SuccsessFully created");
    }

    @GetMapping("/admin")
    public ResponseEntity<List<CategoryDto>> getList(@RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        List<CategoryDto> list = categoryService.getListOnlyForAdmin();
        return ResponseEntity.ok().body(list);
    }


    @PutMapping("/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Integer id,
                                     @RequestBody CategoryDto dto,
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


}

package com.company.controller;

import com.company.dto.ArticleTypeDto;
import com.company.dto.CategoryDto;
import com.company.dto.RegionDto;
import com.company.enums.ProfileRole;
import com.company.service.ArticleTypeService;
import com.company.service.CategoryService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/article_type")
@RestController
public class ArticleTypeController {
    @Autowired
    private ArticleTypeService articleTypeService;

    // PUBLIC

    @GetMapping("/public")
    public ResponseEntity<List<ArticleTypeDto>> getArticleList() {
        List<ArticleTypeDto> list = articleTypeService.getList();
        return ResponseEntity.ok().body(list);
    }

    // SECURED

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody ArticleTypeDto articleTypeDto, @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        articleTypeService.create(articleTypeDto);
        return ResponseEntity.ok().body("SuccsessFully created");
    }


    @GetMapping("")
    public ResponseEntity<List<ArticleTypeDto>> getlist(@RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        List<ArticleTypeDto> list = articleTypeService.getListOnlyForAdmin();
        return ResponseEntity.ok().body(list);
    }


    @PutMapping("/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Integer id,
                                     @RequestBody RegionDto dto,
                                     @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        articleTypeService.update(id, dto);
        return ResponseEntity.ok().body("Succsessfully updated");
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                     @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        articleTypeService.delete(id);
        return ResponseEntity.ok().body("Sucsessfully deleted");
    }


}

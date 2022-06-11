package com.company.controller;

import com.company.dto.article.ArticleTypeDTO;
import com.company.dto.RegionDto;
import com.company.enums.ProfileRole;
import com.company.service.TypesService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/article_type")
@RestController
public class ArticleTypeController {
    @Autowired
    private TypesService typesService;

    // PUBLIC

    @GetMapping("/public")
    public ResponseEntity<List<ArticleTypeDTO>> getArticleList() {
        List<ArticleTypeDTO> list = typesService.getList();
        return ResponseEntity.ok().body(list);
    }

    // SECURED

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody ArticleTypeDTO articleTypeDto, @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        typesService.create(articleTypeDto);
        return ResponseEntity.ok().body("SuccsessFully created");
    }


    @GetMapping("")
    public ResponseEntity<List<ArticleTypeDTO>> getlist(@RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        List<ArticleTypeDTO> list = typesService.getListOnlyForAdmin();
        return ResponseEntity.ok().body(list);
    }


    @PutMapping("/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Integer id,
                                     @RequestBody RegionDto dto,
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
        return ResponseEntity.ok().body("Sucsessfully deleted");
    }


}

package com.company.controller;

import com.company.dto.ArticleTypeDto;
import com.company.dto.RegionDto;
import com.company.enums.ProfileRole;
import com.company.service.ArticleTypeService;
import com.company.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articleType")
public class ArticleTypeController {
    @Autowired
    private ArticleTypeService articleTypeService;

    @PostMapping("/create")
    public ResponseEntity<?> registration(@RequestBody ArticleTypeDto dto,
                                          @RequestHeader("Authorization") String jwt) {
         JwtUtil.decode(jwt, ProfileRole.ADMIN);
        ArticleTypeDto articleTypeDto = articleTypeService.create(dto);
        return ResponseEntity.ok(articleTypeDto);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody ArticleTypeDto dto,
                                    @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        ArticleTypeDto articleTypeDto = articleTypeService.update(dto);
        return ResponseEntity.ok(articleTypeDto);
    }


    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
       List<ArticleTypeDto> list = articleTypeService.articleTypeList();
        return ResponseEntity.ok(list);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody ArticleTypeDto dto,
                                          @RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
       String javob = articleTypeService.delete(dto);
        return ResponseEntity.ok( ).body(javob);
    }

}

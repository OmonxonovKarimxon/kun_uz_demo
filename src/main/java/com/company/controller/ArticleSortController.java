package com.company.controller;

import com.company.dto.CategoryDTO;
import com.company.dto.RegionDto;
import com.company.dto.article.ArticleDTO;
import com.company.dto.article.TypesDTO;
import com.company.service.ArticleSortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sort")
public class ArticleSortController {
    @Autowired
    private ArticleSortService articleSortService;


    @GetMapping("/RegionList")
    public ResponseEntity<?> articleList(@RequestBody RegionDto dto) {

        List<ArticleDTO> list = articleSortService.sortByRegion(dto);

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/categoryList")
    public ResponseEntity<?> articleList(@RequestBody CategoryDTO dto) {

        List<ArticleDTO> list = articleSortService.sortByCategory(dto);

        return ResponseEntity.ok().body(list);
    }
    @GetMapping("/typeList")
    public ResponseEntity<?> articleList(@RequestBody TypesDTO dto) {

        List<ArticleDTO> list = articleSortService.sortBytype(dto);

        return ResponseEntity.ok().body(list);
    }
}

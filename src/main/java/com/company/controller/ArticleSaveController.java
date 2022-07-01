package com.company.controller;

import com.company.dto.article.ArticleSaveDTO;
import com.company.dto.article.ArticleSaveResponseDTO;
import com.company.service.ArticleSaveService;
import com.company.util.HttpHeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/save")
public class ArticleSaveController {
    @Autowired
    private ArticleSaveService articleSaveService;
    @PostMapping("/create")
    public ResponseEntity<?>create(@RequestBody @Valid ArticleSaveDTO dto,
                                   HttpServletRequest request){
        Integer profileId = HttpHeaderUtil.getId(request);
        articleSaveService.create(profileId,dto);
        return ResponseEntity.ok().body("successfully created");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?>delete(@RequestBody ArticleSaveDTO dto,
                                   HttpServletRequest request){
        Integer profileId = HttpHeaderUtil.getId(request);
        articleSaveService.delete(profileId,dto);
        return ResponseEntity.ok().body("successfully deleted");
    }
    @GetMapping("/list")
    public ResponseEntity<?> getList(HttpServletRequest request){
        Integer profileId = HttpHeaderUtil.getId(request);
        List<ArticleSaveResponseDTO> list = articleSaveService.getList(profileId);
        return ResponseEntity.ok().body(list);
    }
}

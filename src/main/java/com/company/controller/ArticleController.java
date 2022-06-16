package com.company.controller;

import com.company.dto.article.ArticleCreateDTO;
import com.company.dto.article.ArticleDTO;
import com.company.enums.ProfileRole;
import com.company.service.ArticleService;
import com.company.util.HttpHeaderUtil;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/article")
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;


    @PostMapping("/adm")
    public ResponseEntity<ArticleDTO> create(@RequestBody ArticleCreateDTO dto,
                                             HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);

        ArticleDTO response = articleService.create(dto, profileId);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/adm")
    public ResponseEntity<?> update(@RequestBody ArticleDTO dto,
                                    HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        String javob = articleService.update(dto, profileId);
        return ResponseEntity.ok().body(javob);
    }

    @PutMapping("adm/publish")
    public ResponseEntity<?> publish(@RequestBody ArticleDTO dto,
                                     HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.PUBLISHER);
        String javob = articleService.publish(dto, profileId);
        return ResponseEntity.ok().body(javob);
    }

    @GetMapping("/list")
    public ResponseEntity<?> articleList() {
        List<ArticleDTO> list = articleService.articleList();

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/read")
    public ResponseEntity<?> readArticleForUser(@RequestBody ArticleDTO dto) {

        ArticleDTO articleDTO = articleService.readArticleForUser(dto);

        return ResponseEntity.ok().body(articleDTO);
    }

    @DeleteMapping("/adm")
    public ResponseEntity<?> delete(@RequestBody ArticleDTO dto,
                                    HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        String javob = articleService.delete(dto);
        return ResponseEntity.ok().body(javob);
    }



}

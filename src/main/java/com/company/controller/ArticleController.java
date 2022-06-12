package com.company.controller;

import com.company.dto.article.ArticleCreateDTO;
import com.company.dto.article.ArticleDTO;
import com.company.enums.ProfileRole;
import com.company.repository.ArticleTagRepository;
import com.company.repository.ArticleTypeRepository;
import com.company.service.ArticleService;
import com.company.util.JwtUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/article")
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;



    @PostMapping("")
    public ResponseEntity<ArticleDTO> create(@RequestBody ArticleCreateDTO dto,
                                             @RequestHeader("Authorization") String jwt) {
        Integer profileId = JwtUtil.decode(jwt, ProfileRole.MODERATOR);
        ArticleDTO response = articleService.create(dto, profileId);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody ArticleDTO dto,
                                    @RequestHeader("Authorization") String jwt) {
        Integer profileId = JwtUtil.decode(jwt, ProfileRole.MODERATOR);
        String javob = articleService.update(dto, profileId);
        return ResponseEntity.ok().body(javob);
    }
    @PutMapping("/publish")
    public ResponseEntity<?> publish(@RequestBody ArticleDTO dto,
                                    @RequestHeader("Authorization") String jwt) {
        Integer profileId = JwtUtil.decode(jwt, ProfileRole.PUBLISHER);
        String javob = articleService.publish(dto, profileId);
        return ResponseEntity.ok().body(javob);
    }

    @GetMapping("/list")
    public ResponseEntity<?> articleList() {
        List<ArticleDTO> list = articleService.articleList();

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/read")
    public ResponseEntity<?>  readArticleForUser(@RequestBody ArticleDTO dto) {

         ArticleDTO articleDTO  = articleService.readArticleForUser(dto);

        return ResponseEntity.ok().body(articleDTO);
    }

    @DeleteMapping("")
    public ResponseEntity<?>delete(@RequestBody ArticleDTO dto,
    @RequestHeader("Authorization") String jwt) {
        Integer profileId = JwtUtil.decode(jwt, ProfileRole.MODERATOR);
        String javob = articleService.delete(dto, profileId);
        return ResponseEntity.ok().body(javob);
    }
    @PutMapping("/like")
    public ResponseEntity<?> like(@RequestBody ArticleDTO dto,
                                    @RequestHeader("Authorization") String jwt) {
        Integer profileId = JwtUtil.decode(jwt, ProfileRole.USER);
        ArticleDTO articleDTO = articleService.like(dto, profileId);
        return ResponseEntity.ok().body(articleDTO);
    }

}

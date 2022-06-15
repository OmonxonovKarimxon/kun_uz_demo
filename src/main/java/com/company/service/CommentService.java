package com.company.service;

import com.company.dto.CommentDTO;
import com.company.entity.ArticleEntity;
import com.company.entity.CommentEntity;
import com.company.entity.ProfileEntity;
import com.company.exps.ItemNotFoundEseption;
import com.company.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ArticleService articleService;

    public void create(CommentDTO dto, Integer userId) {
        ProfileEntity profile = profileService.getProfile(userId);
        ArticleEntity article = articleService.getArticle(dto.getArticleId());



        CommentEntity entity = new CommentEntity();
        entity.setContent(dto.getContent());
        entity.setArticle(article);
        entity.setProfile(profile);
        commentRepository.save(entity);

    }

    public void update(CommentDTO dto, Integer profileId) {
        ProfileEntity profile = profileService.getProfile(profileId);
        ArticleEntity article = articleService.getArticle(dto.getArticleId());

        Optional<CommentEntity> commentEntity = commentRepository.findByIdAndVisible(dto.getId(), true);

        if (profile == null || article == null || commentEntity.isEmpty()) {
            throw new ItemNotFoundEseption("we hava not this comment");
        }

        CommentEntity entity = commentEntity.get();
        entity.setContent(dto.getContent());
        entity.setUpdateDate(LocalDateTime.now());
        commentRepository.save(entity);

    }

    public List<CommentDTO> list(CommentDTO commentDTO) {
        ArticleEntity article = articleService.getArticle(commentDTO.getArticleId());
        List<CommentDTO> dtoList = new LinkedList<>();
        List<CommentEntity> list = commentRepository.findByArticleAndVisible(article, true);
        for (CommentEntity entity : list) {
            CommentDTO dto = new CommentDTO();
            dto.setContent(entity.getContent());
            dto.setArticleId(entity.getArticle().getId());
            dto.setProfileId(entity.getProfile().getId());
            dto.setId(entity.getId());
            dtoList.add(dto);

        }

        return dtoList;
    }

    public void delete(CommentDTO dto, Integer profileId) {
        ProfileEntity profile = profileService.getProfile(profileId);
        ArticleEntity article = articleService.getArticle(dto.getArticleId());

        Optional<CommentEntity> commentEntity = commentRepository.findById(dto.getId());

        if (profile == null || article == null || commentEntity.isEmpty()) {
            throw new ItemNotFoundEseption("something wrong");
        }

        CommentEntity entity = commentEntity.get();
        entity.setVisible(false);
        commentRepository.save(entity);
    }

    public void deleteForAdmin(CommentDTO dto ) {

        Optional<CommentEntity> commentEntity = commentRepository.findById(dto.getId());
        if (commentEntity.isEmpty()) {
            throw new ItemNotFoundEseption("something wrong");
        }
        CommentEntity entity = commentEntity.get();
        entity.setVisible(false);
        commentRepository.save(entity);
    }
}

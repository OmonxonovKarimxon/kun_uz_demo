package com.company.service;

import com.company.dto.comment.CommentDTO;
import com.company.dto.comment.CommentResponseDTO;
import com.company.dto.ProfileDTO;
import com.company.dto.article.ArticleDTO;
import com.company.entity.ArticleEntity;
import com.company.entity.CommentEntity;
import com.company.entity.ProfileEntity;
import com.company.exps.ItemNotFoundEseption;
import com.company.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        entity.setReplyId(dto.getReplyId());
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
        entity.setReplyId(entity.getReplyId());
        entity.setUpdateDate(LocalDateTime.now());
        commentRepository.save(entity);

    }

    public List<CommentResponseDTO> list(CommentDTO commentDTO) {
        ArticleEntity article = articleService.getArticle(commentDTO.getArticleId());

        List<CommentResponseDTO> dtoList = new LinkedList<>();
        List<CommentEntity> list = commentRepository.findByArticleAndVisible(article, true);
        for (CommentEntity entity : list) {
            CommentResponseDTO dto = new CommentResponseDTO();

            dto.setContent(entity.getContent());
            dto.setProfile(getProfileForComment(entity));
            dto.setId(entity.getId());
            dto.setCreateDate(entity.getCreatedDate());
            dto.setUpdateDate(entity.getUpdateDate());

            dtoList.add(dto);

        }

        return dtoList;
    }


    public void delete(CommentDTO dto, Integer profileId) {
        ProfileEntity profile = profileService.getProfile(profileId);
        ArticleEntity article = articleService.getArticle(dto.getArticleId());

        Optional<CommentEntity> optional = commentRepository.findById(dto.getId());


        if (profile == null || article == null || optional.isEmpty()) {
            throw new ItemNotFoundEseption("something wrong");
        }
        CommentEntity entity = optional.get();

        if (!entity.getProfile().equals(profile)) {
            throw new ItemNotFoundEseption("something wrong");
        }

        entity.setVisible(false);
        commentRepository.save(entity);
    }

    public void deleteForAdmin(CommentDTO dto) {

        Optional<CommentEntity> commentEntity = commentRepository.findById(dto.getId());
        if (commentEntity.isEmpty()) {
            throw new ItemNotFoundEseption("something wrong");
        }
        CommentEntity entity = commentEntity.get();
        entity.setVisible(false);
        commentRepository.save(entity);
    }


    public PageImpl pagination(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<CommentEntity> list = commentRepository.findAll(pageable);

        List<CommentEntity> all = list.getContent();

        List<CommentResponseDTO> dtoList =  entityToDto(all);

        return new PageImpl(dtoList, pageable, list.getTotalElements());
    }

    private List<CommentResponseDTO> entityToDto(List<CommentEntity> commentEntities) {
        List<CommentResponseDTO> dtoList = new ArrayList<>();

        commentEntities.forEach(entity -> {
            CommentResponseDTO dto = new CommentResponseDTO();
            dto.setId(entity.getId());
            dto.setContent(entity.getContent());
            dto.setReplyId(entity.getReplyId());
            dto.setProfile(getProfileForComment(entity));
            dto.setArticle(getArticleForComment(entity));
            dto.setCreateDate(entity.getCreatedDate());
            dto.setUpdateDate(entity.getUpdateDate());
            dtoList.add(dto);
        });
        return dtoList;
    }

    private ProfileDTO getProfileForComment(CommentEntity entity) {

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(entity.getProfile().getId());
        profileDTO.setName(entity.getProfile().getName());
        profileDTO.setSurname(entity.getProfile().getSurname());
        return profileDTO;
    }

    private ArticleDTO getArticleForComment(CommentEntity entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getArticle().getId());
        dto.setTitle(entity.getArticle().getTitle());

        return dto;
    }

    public List<CommentResponseDTO> listByCommentId(CommentDTO commentDTO) {
        ArticleEntity article = articleService.getArticle(commentDTO.getArticleId());

        List<CommentResponseDTO> dtoList = new LinkedList<>();
        List<CommentEntity> list = commentRepository.findByReplyIdAndVisible(commentDTO.getReplyId(), true);
        for (CommentEntity entity : list) {
            CommentResponseDTO dto = new CommentResponseDTO();

            dto.setContent(entity.getContent());
            dto.setProfile(getProfileForComment(entity));
            dto.setId(entity.getId());
            dto.setCreateDate(entity.getCreatedDate());
            dto.setUpdateDate(entity.getUpdateDate());

            dtoList.add(dto);

        }

        return dtoList;
    }

}

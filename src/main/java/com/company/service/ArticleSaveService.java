package com.company.service;

import com.company.dto.ArticleSaveDTO;
import com.company.dto.ArticleSaveResponseDTO;
import com.company.dto.article.ArticleDTO;
import com.company.entity.ArticleEntity;
import com.company.entity.ArticleSaveEntity;
import com.company.entity.ProfileEntity;
import com.company.repository.ArticleRepository;
import com.company.repository.ArticleSaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleSaveService {
    @Autowired
    private ArticleSaveRepository articleSaveRepository;
    @Autowired
    ArticleService articleService;
    @Autowired
    private ProfileService profileService;


    public void create(Integer profileId, ArticleSaveDTO dto) {
        ArticleEntity article = articleService.getArticle(dto.getArticleId());
        ProfileEntity profile = profileService.getProfile(profileId);

        ArticleSaveEntity entity = new ArticleSaveEntity();
        entity.setArticle(article);
        entity.setProfile(profile);
        articleSaveRepository.save(entity);

    }

    public void delete(Integer profileId, ArticleSaveDTO dto) {
        articleSaveRepository.delete(dto.getId(), profileId);
    }

    public  List<ArticleSaveResponseDTO> getList(Integer profileId) {
        ProfileEntity profile = profileService.getProfile(profileId);
        List<ArticleSaveEntity> saveList = articleSaveRepository.findByProfile(profile);
        List<ArticleSaveResponseDTO> dtoList = new ArrayList<>();

        saveList.forEach(articleSaveEntity -> {
            ArticleSaveResponseDTO dto = new ArticleSaveResponseDTO();
            dto.setId(articleSaveEntity.getId());
            dto.setArticle(entityToDto(articleSaveEntity.getArticle()));
            dtoList.add(dto);
        });
        return dtoList;
    }


    private ArticleDTO entityToDto(ArticleEntity entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        return dto;
    }
}

package com.company.service;

import com.company.entity.ArticleEntity;
import com.company.entity.ArticleLikeEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.LikeStatus;
import com.company.repository.ArticleLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ArticleLikeService {

    @Autowired
    private ArticleLikeRepository articleLikeRepository;


    public void create(ArticleEntity article, ProfileEntity profile) {
        ArticleLikeEntity entity  =new ArticleLikeEntity();

        entity.setStatus(LikeStatus.ACTIVE);
        entity.setProfile(profile);
        entity.setArticle(article);
        articleLikeRepository.save(entity);

    }
}

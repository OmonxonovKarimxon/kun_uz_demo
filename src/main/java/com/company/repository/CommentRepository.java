package com.company.repository;

import com.company.entity.ArticleEntity;
import com.company.entity.ArticleLikeEntity;
import com.company.entity.CommentEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends CrudRepository<CommentEntity, Integer> {


    Optional<CommentEntity> findByIdAndVisible(Integer integer, Boolean visible);

    List<CommentEntity> findByArticleAndVisible(ArticleEntity entity, Boolean visible);
}

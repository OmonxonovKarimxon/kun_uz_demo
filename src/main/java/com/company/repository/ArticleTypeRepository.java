package com.company.repository;

import com.company.entity.ArticleEntity;
import com.company.entity.ArticleTypeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity, Integer> {

    List<ArticleTypeEntity> findByArticle(ArticleEntity entity);
}

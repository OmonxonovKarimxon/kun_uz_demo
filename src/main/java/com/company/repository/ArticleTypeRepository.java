package com.company.repository;

import com.company.entity.ArticleEntity;
import com.company.entity.ArticleTypeEntity;
import com.company.entity.TypesEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity, Integer> {

    List<ArticleTypeEntity> findByArticle(ArticleEntity entity);
    List<ArticleTypeEntity> findByTypes(TypesEntity entity);

//    @Query("SELECT new ArticleEntity (p.article) From ArticleTypeEntity p")
//    ArticleEntity getArticleList();

}

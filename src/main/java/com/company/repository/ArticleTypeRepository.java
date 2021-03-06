package com.company.repository;

import com.company.entity.ArticleEntity;
import com.company.entity.ArticleTypeEntity;
import com.company.entity.TypesEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity, Integer> {



    @Query("SELECT u.types.id FROM ArticleTypeEntity u WHERE u.article = ?1  ")
    List<Integer> getTypeIdList( ArticleEntity entity);


    @Query( "SELECT u.article  FROM ArticleTypeEntity u" +
            " WHERE u.types=:entity  and  u.article.status= 'PUBLISHED' " +
            " ORDER BY u.article.publishDate  DESC " )
    List<ArticleEntity> findByTypes( TypesEntity entity);





}




package com.company.repository;

import com.company.entity.ArticleEntity;
import com.company.entity.ArticleTagEntity;
import com.company.entity.ArticleTypeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleTagRepository extends CrudRepository<ArticleTagEntity, Integer> {



    @Query("SELECT u.tag.name  FROM ArticleTagEntity u WHERE u.article = ?1  ")
    List<String> getTagNameList( ArticleEntity entity);

}

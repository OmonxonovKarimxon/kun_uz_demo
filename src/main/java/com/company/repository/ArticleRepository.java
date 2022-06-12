package com.company.repository;

import com.company.entity.ArticleEntity;
import com.company.entity.CategoryEntity;
import com.company.entity.RegionEntity;
import com.company.enums.ArticleStatus;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends CrudRepository<ArticleEntity, Integer> {


    List<ArticleEntity> findByRegion(RegionEntity entity);

    List<ArticleEntity> findByCategory(CategoryEntity entity);


    Optional<ArticleEntity> getById(String id);

    Optional<ArticleEntity> getByStatusAndId(ArticleStatus status , String id);


    List<ArticleEntity> findByPublishDateIsNotNull( );
}

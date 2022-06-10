package com.company.repository;

import com.company.entity.ArticleTypeEntity;
import com.company.entity.CategoryEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity, Integer> {

    Optional<ArticleTypeEntity> findByKey(String key);

    List<ArticleTypeEntity> findAllByVisible(Boolean b);


}

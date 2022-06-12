package com.company.service;

import com.company.dto.article.TypesDTO;
import com.company.entity.ArticleEntity;
import com.company.entity.ArticleTypeEntity;
import com.company.entity.TypesEntity;
import com.company.repository.ArticleRepository;
import com.company.repository.ArticleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;
    @Autowired
    private ArticleRepository articleRepository;

    public void create(ArticleEntity article, List<Integer> typesList) {
        for (Integer typesId : typesList) {
            ArticleTypeEntity articleTypeEntity = new ArticleTypeEntity();
            articleTypeEntity.setArticle(article);
            articleTypeEntity.setTypes(new TypesEntity(typesId));
            articleTypeRepository.save(articleTypeEntity);
        }
    }

    public List<Integer> getTypeList(ArticleEntity articleEntity) {

        List<ArticleTypeEntity> typesList = articleTypeRepository.findByArticle(articleEntity);
        List<Integer> idList = new LinkedList<>();

        for (ArticleTypeEntity entity : typesList) {
            Integer id = entity.getTypes().getId();

            idList.add(id);


        }

        return idList;
    }

}

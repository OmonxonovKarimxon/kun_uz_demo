package com.company.service;

import com.company.entity.ArticleEntity;
import com.company.entity.ArticleTypeEntity;
import com.company.entity.TypesEntity;
import com.company.repository.ArticleRepository;
import com.company.repository.ArticleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<Integer>  getTypeList(ArticleEntity articleEntity) {
        List<Integer> idList = articleTypeRepository.getTypeIdList(articleEntity);

        return idList;
    }

    public List<ArticleEntity> sortByTypeFiveArticle(TypesEntity typesEntity) {
        List<ArticleEntity> entities = articleTypeRepository.findByTypes(typesEntity);
        List<ArticleEntity> list = new ArrayList<>();
        for (int i=0; i<5; i++) {
            list.add(entities.get(i));
        }
        return list;
    }

    public List<ArticleEntity> sortByTypeThreeArticle(TypesEntity typesEntity) {
        List<ArticleEntity> entities = articleTypeRepository.findByTypes(typesEntity);
        List<ArticleEntity> list = new ArrayList<>();
        for (int i=0; i<3; i++) {
            list.add(entities.get(i));
        }
        return list;
    }

    public List<ArticleEntity> sortByTypeEightArticle(TypesEntity typesEntity) {
        List<ArticleEntity> entities = articleTypeRepository.findByTypes(typesEntity);
        List<ArticleEntity> list = new ArrayList<>();
        for (int i=3; i<8; i++) {
            list.add(entities.get(i));
        }
        return list;
    }
}

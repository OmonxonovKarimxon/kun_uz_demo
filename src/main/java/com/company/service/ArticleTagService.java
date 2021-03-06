package com.company.service;

import com.company.entity.*;
import com.company.repository.ArticleTagRepository;
import com.company.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ArticleTagService {
    @Autowired
    private ArticleTagRepository articleTagRepository;
    @Autowired
    private TagService tagService;


    public void create(ArticleEntity article, List<String> tagList) {
        // ["#maymunChechak","#kasallik","#epidemiya"]
        for (String tagName : tagList) {
            TagEntity tag = tagService.createIfNotExists(tagName);

            ArticleTagEntity articleTagEntity = new ArticleTagEntity();
            articleTagEntity.setArticle(article);
            articleTagEntity.setTag(tag);

            articleTagRepository.save(articleTagEntity);

        }

    }

    public List<String> getTagList(ArticleEntity articleEntity) {

       return  articleTagRepository.getTagNameList(articleEntity);

    }
}

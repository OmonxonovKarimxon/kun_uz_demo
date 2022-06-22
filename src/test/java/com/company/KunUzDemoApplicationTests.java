package com.company;

import com.company.entity.TypesEntity;
import com.company.repository.ArticleRepository;
import com.company.repository.ArticleTypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
class KunUzDemoApplicationTests {

    @Autowired
    private ArticleTypeRepository articleTypeRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Test
    void contextLoads() {

        articleRepository.getArticleByRegionKay("t78kt78k67t8",  PageRequest.of(1, 5));
    }

}

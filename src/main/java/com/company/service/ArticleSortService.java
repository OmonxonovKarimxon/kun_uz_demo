package com.company.service;

import com.company.dto.CategoryDTO;
import com.company.dto.RegionDto;
import com.company.dto.article.ArticleDTO;
import com.company.dto.article.TypesDTO;
import com.company.entity.ArticleEntity;
import com.company.entity.CategoryEntity;
import com.company.entity.RegionEntity;
import com.company.entity.TypesEntity;
import com.company.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ArticleSortService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private RegionService regionService;
    @Autowired
    private ArticleTypeService articleTypeService;
    @Autowired
    private ArticleTagService articleTagService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TypesService typesService;


    public List<ArticleDTO> sortByRegion(RegionDto regionDto) {
        RegionEntity RegionEntity = regionService.get(regionDto.getId());
        List<ArticleEntity> list = articleRepository.findByRegion(RegionEntity);

        return createList(list);

    }

    public List<ArticleDTO> sortByCategory(CategoryDTO dto) {
        CategoryEntity categoryEntity = categoryService.get(dto.getId());
        List<ArticleEntity> list = articleRepository.findByCategory(categoryEntity);

        return createList(list);
    }

    public List<ArticleDTO> createList(List<ArticleEntity> list) {

        List<ArticleDTO> dtoList = new LinkedList<>();
        for (ArticleEntity entity : list) {
            ArticleDTO dto = new ArticleDTO();
            dto.setTitle(entity.getTitle());
            dto.setDescription(entity.getDescription());
            dto.setContent(entity.getContent());
            dto.setId(entity.getId());
            dto.setRegionId(entity.getRegion().getId());
            dto.setCategoryId(entity.getCategory().getId());
            dto.setViewCount(entity.getViewCount());
            dto.setLikeCount(entity.getLikeCount());

            List<Integer> typeList = articleTypeService.getTypeList(entity);
            dto.setTypesList(typeList);

            List<String> tagList = articleTagService.getTagList(entity);
            dto.setTagList(tagList);

            dtoList.add(dto);
        }
        return dtoList;
    }

    public List<ArticleDTO> sortBytype(TypesDTO dto) {

        TypesEntity typesEntity = typesService.getType(dto.getId());
        List<ArticleEntity> list = articleTypeService.sortByType(typesEntity);
        return createList(list);

    }
}

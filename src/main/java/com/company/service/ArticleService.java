package com.company.service;

import com.company.dto.article.ArticleCreateDTO;
import com.company.dto.article.ArticleDTO;
import com.company.entity.*;
import com.company.enums.ArticleStatus;
import com.company.repository.ArticleRepository;
import com.company.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private RegionService regionService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleTypeService articleTypeService;
    @Autowired
    private ArticleTagService articleTagService;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ArticleLikeService articleLikeService;

    public ArticleDTO create(ArticleCreateDTO dto, Integer profileId) {
        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setDescription(dto.getDescription());

        RegionEntity region = regionService.get(dto.getRegionId());
        entity.setRegion(region);

        CategoryEntity category = categoryService.get(dto.getCategoryId());
        entity.setCategory(category);

        ProfileEntity moderator = new ProfileEntity();
        moderator.setId(profileId);
        entity.setModerator(moderator);
        entity.setStatus(ArticleStatus.NOT_PUBLISHED);

        articleRepository.save(entity);

        articleTypeService.create(entity, dto.getTypesList()); // type

        articleTagService.create(entity, dto.getTagList());  // tag

        ArticleDTO articleDTO = new ArticleDTO();


        articleDTO.setId(entity.getId());
        articleDTO.setContent(dto.getContent());
        articleDTO.setTitle(dto.getTitle());
        articleDTO.setDescription(dto.getDescription());
        articleDTO.setCategoryId(dto.getCategoryId());
        articleDTO.setRegionId(dto.getRegionId());
        articleDTO.setTagList(dto.getTagList());
        articleDTO.setTypesList(dto.getTypesList());


        return articleDTO;
    }

    public String update(ArticleDTO dto, Integer profileId) {
        Optional<ArticleEntity> optional = articleRepository.getById(dto.getId());

        ArticleEntity entity = optional.get();

        entity.setContent(dto.getContent());

        entity.setDescription(dto.getDescription());

        RegionEntity region = regionService.get(dto.getRegionId());
        entity.setRegion(region);

        CategoryEntity category = categoryService.get(dto.getCategoryId());
        entity.setCategory(category);

        ProfileEntity moderator = new ProfileEntity();
        moderator.setId(profileId);
        entity.setModerator(moderator);
        entity.setStatus(ArticleStatus.NOT_PUBLISHED);

        articleRepository.save(entity);

        articleTypeService.create(entity, dto.getTypesList()); // type

        articleTagService.create(entity, dto.getTagList());  // tag

        return "successfully";
    }

    public List<ArticleDTO> articleList() {
        Iterable<ArticleEntity> list = articleRepository.findByPublishDateIsNotNull();
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

    public String delete(ArticleDTO dto, Integer profileId) {
        Optional<ArticleEntity> optional = articleRepository.getById(dto.getId());

        ArticleEntity entity = optional.get();
        entity.setVisible(false);

        articleRepository.save(entity);
        return "successfully deleted";
    }

//maqolani o`qiladi va count bittaga oshadi

    public ArticleDTO readArticleForUser(ArticleDTO articleDTO) {

        Optional<ArticleEntity> optional = articleRepository.getByStatusAndId(ArticleStatus.PUBLISHED, articleDTO.getId());

        ArticleEntity entity = optional.get();
        entity.setViewCount(entity.getViewCount() + 1);

        articleRepository.save(entity);

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

        return dto;

    }

    //publisher maqolani chop etishi uchun
    public String publish(ArticleDTO dto, Integer profileId) {
        Optional<ArticleEntity> optional = articleRepository.getById(dto.getId());
        Optional<ProfileEntity> publisher = profileRepository.findById(profileId);

        ArticleEntity entity = optional.get();
        entity.setPublishDate(LocalDateTime.now());
        entity.setStatus(ArticleStatus.PUBLISHED);
        entity.setPublisher(publisher.get());

        articleRepository.save(entity);
        return "successfully published";
    }

    public ArticleDTO like(ArticleDTO articleDTO, Integer profileId) {

        Optional<ArticleEntity> articleOptional = articleRepository.getByStatusAndId(ArticleStatus.PUBLISHED, articleDTO.getId());
        Optional<ProfileEntity> userOptional = profileRepository.findById(profileId);




        ArticleEntity article = articleOptional.get();
        ProfileEntity profile = userOptional.get();
        article.setLikeCount(article.getLikeCount() + 1);

        articleLikeService.create(article,profile);

        articleRepository.save(article);

        ArticleDTO dto = new ArticleDTO();
        dto.setTitle(article.getTitle());
        dto.setDescription(article.getDescription());
        dto.setContent(article.getContent());
        dto.setId(article.getId());
        dto.setRegionId(article.getRegion().getId());
        dto.setCategoryId(article.getCategory().getId());
        dto.setViewCount(article.getViewCount());
        dto.setLikeCount(article.getLikeCount());
        List<Integer> typeList = articleTypeService.getTypeList(article);
        dto.setTypesList(typeList);

        List<String> tagList = articleTagService.getTagList(article);
        dto.setTagList(tagList);

        return dto;

    }
}

package com.company.service;

import com.company.dto.article.ArticleCreateDTO;
import com.company.dto.article.ArticleDTO;
import com.company.entity.*;
import com.company.enums.ArticleStatus;
import com.company.exps.ItemNotFoundEseption;
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
        Optional<ArticleEntity> optional = articleRepository.findByIdAndStatus(dto.getId(), ArticleStatus.NOT_PUBLISHED);

        if (optional.isEmpty()) {

            throw new ItemNotFoundEseption("article not found");
        }

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

        return "successfully created";
    }

    public List<ArticleDTO> articleList() {
        Iterable<ArticleEntity> list = articleRepository.findByStatus(ArticleStatus.PUBLISHED);

        if (list == null) {
            throw new ItemNotFoundEseption("articles not found");
        }

        List<ArticleDTO> dtoList = new LinkedList<>();
        for (ArticleEntity entity : list) {
            ArticleDTO dto = entityToDto(entity);
            dtoList.add(dto);
        }

        return dtoList;
    }

    public String delete(ArticleDTO dto) {
        Optional<ArticleEntity> optional = articleRepository.getById(dto.getId());
        if (optional.isEmpty()) {
            throw new ItemNotFoundEseption("article not found");
        }
        ArticleEntity entity = optional.get();
        entity.setVisible(false);
        articleRepository.save(entity);
        return "successfully deleted";
    }

//maqolani o`qiladi va count bittaga oshadi

    public ArticleDTO readArticleForUser(ArticleDTO articleDTO) {

        Optional<ArticleEntity> optional = articleRepository.getByStatusAndId(ArticleStatus.PUBLISHED, articleDTO.getId());
        if (optional.isEmpty()) {

            throw new ItemNotFoundEseption("article not found");
        }
        ArticleEntity entity = optional.get();
        entity.setViewCount(entity.getViewCount() + 1);
        articleRepository.save(entity);


        return entityToDto(entity);

    }

    //publisher maqolani chop etishi uchun
    public String publish(ArticleDTO dto, Integer profileId) {
        Optional<ArticleEntity> optional = articleRepository.getById(dto.getId());
        Optional<ProfileEntity> publisher = profileRepository.findById(profileId);
        if (optional.isEmpty() || publisher.isEmpty()) {
            throw new ItemNotFoundEseption("no access");
        }

        ArticleEntity entity = optional.get();
        entity.setPublishDate(LocalDateTime.now());
        entity.setPublisher(publisher.get());

        if(entity.getStatus().equals(ArticleStatus.NOT_PUBLISHED)){
            entity.setStatus(ArticleStatus.PUBLISHED);
        }else{
            entity.setStatus(ArticleStatus.NOT_PUBLISHED);
        }
        articleRepository.save(entity);
        return "successfully published";
    }



    public ArticleEntity getArticle(String articleId) {

        Optional<ArticleEntity> articleEntity = articleRepository.getById(articleId);

        if (articleEntity.isEmpty()) {
            throw new ItemNotFoundEseption(" article not found");
        }
        return articleEntity.get();
    }

    private ArticleDTO entityToDto(ArticleEntity entity) {


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
}

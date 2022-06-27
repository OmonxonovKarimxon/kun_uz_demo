package com.company.service;

import com.company.dto.ArticleFilterDTO;
import com.company.dto.RegionDTO;
import com.company.dto.article.ArticleCreateDTO;
import com.company.dto.article.ArticleDTO;
import com.company.dto.article.ArticleLikeDTO;
import com.company.entity.*;
import com.company.enums.ArticleStatus;
import com.company.enums.LangEnum;
import com.company.exps.ItemNotFoundEseption;
import com.company.repository.ArticleRepository;
import com.company.repository.CustomArticleRepository;
import com.company.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    @Autowired
    private CustomArticleRepository customArticleRepository;

    public String create(ArticleCreateDTO dto, Integer profileId) {
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

        return "successfully" ;
    }

    public String update(ArticleDTO dto, Integer profileId) {
        Optional<ArticleEntity> optional = articleRepository.findByIdAndStatus(dto.getId(), ArticleStatus.NOT_PUBLISHED);

        if (optional.isEmpty()) {

            throw new ItemNotFoundEseption("article not found");
        }

        ArticleEntity entity = optional.get();


        entity.setContent(dto.getContent());

        entity.setDescription(dto.getDescription());

        RegionEntity region = regionService.get(dto.getRegion().getId());
        entity.setRegion(region);

        CategoryEntity category = categoryService.get(dto.getCategory().getId());
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


    public ArticleDTO getPublishedArticleById(String id, LangEnum lang) {
        Optional<ArticleEntity> optional = articleRepository.getPublishedById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundEseption("Article Not Found");
        }

        ArticleEntity entity = optional.get();
        ArticleDTO dto = fullDTO(entity);

        dto.setRegion(regionService.get(entity.getRegion(), lang));
        dto.setCategory(categoryService.get(entity.getCategory(), lang));

        ArticleLikeDTO likeDTO = articleLikeService.likeCountAndDislikeCount(entity.getId());
        dto.setLike(likeDTO);

        List<String> tagList = articleTagService.getTagList(entity);
        dto.setTagList(tagList);
        return dto;
    }

    //publisher maqolani chop etishi uchun
    public String publish(ArticleDTO dto, Integer profileId) {
        Optional<ArticleEntity> optional = articleRepository.getById(dto.getId());
        Optional<ProfileEntity> publisher = profileRepository.findById(profileId);
        if (optional.isEmpty() || publisher.isEmpty()) {
            throw new ItemNotFoundEseption("no access");
        }

        ArticleEntity entity = optional.get();

        if (entity.getStatus().equals(ArticleStatus.NOT_PUBLISHED)) {

            entity.setPublishDate(LocalDateTime.now());
            entity.setPublisher(publisher.get());
            entity.setStatus(ArticleStatus.PUBLISHED);
        } else {
            entity.setStatus(ArticleStatus.NOT_PUBLISHED);
        }
        articleRepository.updateStatus(entity.getStatus(), entity.getPublishDate(), entity.getId());
        return "successfully published";
    }


    public ArticleEntity getArticle(String articleId) {

        Optional<ArticleEntity> articleEntity = articleRepository.getById(articleId);

        if (articleEntity.isEmpty()) {
            throw new ItemNotFoundEseption(" article not found");
        }
        return articleEntity.get();
    }


    //  Get Last 5 Article By Types  ordered_by_created_date
    //        (Berilgan types bo'yicha oxirgi 5ta pubished bo'lgan article ni return qiladi.)

    public List<ArticleDTO> getLast5ArticleByType(String typeKey) {
        Pageable pageable = PageRequest.of(0, 5);
        Page<ArticleEntity> articlePage = articleRepository.findLast5ByType(typeKey, pageable);

        List<ArticleDTO> dtoList = new LinkedList<>();
        articlePage.getContent().forEach(article -> {
            dtoList.add(shortDTOInfo(article));
        });
        return dtoList;
    }

    public List<ArticleDTO> getLast3ArticleByType(String typeKey) {
        Pageable pageable = PageRequest.of(0, 3);
        Page<ArticleEntity> articlePage = articleRepository.findLast3ByType(typeKey, pageable);

        List<ArticleDTO> dtoList = new LinkedList<>();
        articlePage.getContent().forEach(article -> {
            dtoList.add(shortDTOInfo(article));
        });
        return dtoList;

    }


    public List<ArticleDTO> getLat8ArticleNotIn(List<String> articleIdList) {
        Pageable pageable = PageRequest.of(0, 5);
        Page<ArticleEntity> articlePage = articleRepository.findLast8NotIn(articleIdList, pageable);

        List<ArticleDTO> dtoList = new LinkedList<>();
        articlePage.getContent().forEach(article -> {
            dtoList.add(shortDTOInfo(article));
        });
        return dtoList;
    }

    public List<ArticleDTO> theBestArticle() {
        Pageable pageable = PageRequest.of(0, 4);
        Page<ArticleEntity>articlePage = articleRepository.theMostRead(pageable);
        List<ArticleDTO> dtoList = new LinkedList<>();
        articlePage.getContent().forEach(article -> {
            dtoList.add(shortDTOInfo(article));
        });
        return dtoList;
    }
    public List<ArticleDTO> get4ArticleByTagName(String tag) {
        Pageable pageable = PageRequest.of(0, 4);
        Page<ArticleEntity> articlePage = articleRepository.get4ArticleByTagName(tag, pageable);
        List<ArticleDTO> dtoList = new LinkedList<>();
        articlePage.getContent().forEach(article -> {
            dtoList.add(shortDTOInfo(article));
        });
        return dtoList;
    }

        public List<ArticleDTO> get4ArticleBytypesAndRegion(Integer typeId, RegionDTO dto) {
            Pageable pageable = PageRequest.of(0, 4);
            Page<ArticleEntity> articlePage = articleRepository.get4ArticleBytypesAndRegion(typeId, dto.getKey(), pageable);
            List<ArticleDTO> dtoList = new LinkedList<>();
            articlePage.getContent().forEach(article -> {
                dtoList.add(shortDTOInfo(article));
            });
            return dtoList;
        }

        public PageImpl<ArticleDTO> getArticleByRegionKey(Integer page, Integer size, String regionKay) {

            Pageable pageable = PageRequest.of(page, size);
            Page<ArticleEntity> articlePage = articleRepository.getArticleByRegionKay(regionKay, pageable);
            List<ArticleDTO> dtoList = new LinkedList<>();
            articlePage.getContent().forEach(article -> {
                dtoList.add(shortDTOInfo(article));
            });
            return  new PageImpl(dtoList,pageable, articlePage.getTotalElements());
    }

    public List<ArticleDTO> last5ByCategoryKey(  String key) {
        Pageable pageable = PageRequest.of(0, 5);
        Page<ArticleEntity> articlePage = articleRepository.last5ByCategoryKey( key, pageable);
        List<ArticleDTO> dtoList = new LinkedList<>();
        articlePage.getContent().forEach(article -> {
            dtoList.add(shortDTOInfo(article));
        });
        return dtoList;
    }


    public PageImpl<ArticleDTO> getArticleByCategoryKey(Integer page, Integer size, String categoryKey) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ArticleEntity> articlePage = articleRepository.getArticleByCategoryKey(categoryKey, pageable);
        List<ArticleDTO> dtoList = new LinkedList<>();
        articlePage.getContent().forEach(article -> {
            dtoList.add(shortDTOInfo(article));
        });
        return  new PageImpl(dtoList,pageable, articlePage.getTotalElements());
    }

    public List<ArticleDTO> filter(ArticleFilterDTO filterDTO) {
        List<ArticleEntity> entityList = customArticleRepository.filter(filterDTO);
        List<ArticleDTO> dtolist = new LinkedList<>();

        for (ArticleEntity entity : entityList) {
            ArticleDTO dto = fullDTO(entity);
            dtolist.add(dto);
        }

        return dtolist;
    }

    private ArticleDTO fullDTO(ArticleEntity entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setDescription(entity.getDescription());
        dto.setSharedCount(entity.getSharedCount());
        dto.setPublishDate(entity.getPublishDate());
        dto.setViewCount(entity.getViewCount());
        return dto;
    }

    public ArticleDTO shortDTOInfo(ArticleEntity entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setPublishDate(entity.getPublishDate());
        List<String> tagList = articleTagService.getTagList(entity);
        dto.setTagList(tagList);

        return dto;
    }


    public void increase(String articleId) {
        Optional<ArticleEntity> optional = articleRepository.getByStatusAndId(ArticleStatus.PUBLISHED, articleId);
        if (optional.isEmpty()) {
            throw new ItemNotFoundEseption("article not found");
        }
        ArticleEntity entity = optional.get();
        entity.setViewCount(entity.getViewCount() + 1);
        articleRepository.updateViewCount(entity.getViewCount());
    }



}

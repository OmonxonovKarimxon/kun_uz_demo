package com.company.repository;

import com.company.entity.ArticleEntity;
import com.company.entity.CategoryEntity;
import com.company.entity.RegionEntity;
import com.company.enums.ArticleStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends PagingAndSortingRepository<ArticleEntity, String> {


    Optional<ArticleEntity> findByIdAndStatus(String id, ArticleStatus status);

    List<ArticleEntity> findByRegion(RegionEntity entity);

    List<ArticleEntity> findByCategory(CategoryEntity entity);


    Optional<ArticleEntity> getById(String id);

    Optional<ArticleEntity> getByStatusAndId(ArticleStatus status, String id);


    List<ArticleEntity> findByStatus(ArticleStatus status);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set status=:status , publishDate=:publishdate where  id=:id")
    void updateStatus(@Param("status") ArticleStatus status, @Param("publishdate") LocalDateTime publishdate, @Param("id") String id);


    @Query("SELECT new ArticleEntity(art.id, art.title, art.description, art.publishDate) " +
            " from ArticleTypeEntity as a " +
            " inner join a.article as art " +
            " inner join a.types as t " +
            " Where t.key =:typeKey and art.visible = true and art.status = 'PUBLISHED' " +
            " order by art.publishDate ")
    Page<ArticleEntity> findLast5ByType(@Param("typeKey") String typeKey, Pageable pageable);


    @Query("SELECT new ArticleEntity(art.id, art.title, art.description, art.publishDate) " +
            " from ArticleTypeEntity as a " +
            " inner join a.article as art " +
            " inner join a.types as t " +
            " Where t.key =:typeKey and art.visible = true and art.status = 'PUBLISHED' " +
            " order by art.publishDate ")
    Page<ArticleEntity> findLast3ByType(@Param("typeKey") String typeKey, Pageable pageable);


    @Query("SELECT new ArticleEntity(art.id,art.title,art.description,art.publishDate) " +
            " from ArticleEntity as art " +
            " Where art.visible = true and art.status = 'PUBLISHED' and art.id not in (:idList) " +
            " order by art.publishDate ")
    Page<ArticleEntity> findLast8NotIn(@Param("idList") List<String> idList, Pageable pageable);


    @Transactional
    @Modifying
    @Query("update ArticleEntity  set viewCount =:viewCount")
    void updateViewCount(Integer viewCount);

    @Query("SELECT new ArticleEntity(art.id, art.title, art.description, art.publishDate) " +
            " from ArticleEntity as art " +
            " Where art.visible = true and art.status = 'PUBLISHED' " +
            " order by art.viewCount desc ")
    Page<ArticleEntity> theMostRead(Pageable pageable);

    @Query("SELECT new ArticleEntity(art.id,art.title,art.description,art.publishDate) " +
            " from ArticleTagEntity as ate " +
            "inner join ate.article as art " +
            "inner join ate.tag as tags   " +
            " Where  tags.name=:tag and  art.visible = true and art.status = 'PUBLISHED' " +
            " order by art.publishDate ")
    Page<ArticleEntity> get4ArticleByTagName(String tag, Pageable pageable);


    @Query("SELECT new ArticleEntity(art.id,art.title,art.description,art.publishDate) " +
            " from ArticleTypeEntity as ate " +
            "inner join ate.article as art " +
            "inner join ate.types as type   " +
            " Where  type.id=:typeId  and art.region.key=:regionKay and art.visible = true and art.status = 'PUBLISHED' " +
            " order by art.publishDate ")
    Page<ArticleEntity> get4ArticleBytypesAndRegion(Integer typeId, String regionKay, Pageable pageable);


    @Query("SELECT new ArticleEntity(art.id,art.title,art.description,art.publishDate) " +
            " from ArticleTypeEntity as ate " +
            "inner join ate.article as art " +
            " Where  art.region.key=:regionKay and art.visible = true and art.status = 'PUBLISHED' " +
            " order by art.publishDate ")
    Page<ArticleEntity> getArticleByRegionKay(String regionKay, Pageable pageable);


    @Query("SELECT new ArticleEntity(art.id,art.title,art.description,art.publishDate) " +
            " from ArticleTypeEntity as ate " +
            "inner join ate.article as art " +
            " Where art.category.key=:key and art.visible = true and art.status = 'PUBLISHED' " +
            " order by art.publishDate ")
    Page<ArticleEntity> last5ByCategoryKey(String key, Pageable pageable);


    @Query("SELECT new ArticleEntity(art.id,art.title,art.description,art.publishDate) " +
            " from ArticleTypeEntity as ate " +
            "inner join ate.article as art " +
            " Where  art.category.key=:categoryKey and art.visible = true and art.status = 'PUBLISHED' " +
            " order by art.publishDate ")
    Page<ArticleEntity> getArticleByCategoryKey(String categoryKey, Pageable pageable);


    @Query("FROM ArticleEntity art where art.visible = true and art.status = 'PUBLISHED' and art.id =?1")
    Optional<ArticleEntity> getPublishedById(String id);



    //hali servise yozmadim
//    @Query(value = "SELECT  art.id as id ,art.title as title, art.description as description,art.publish_date as publishDate " +
//            " FROM article as art " +
//            " inner join article_type as a on a.article_id = art.id " +
//            " inner join types as t on t.id = a.types_id " +
//            " where  t.key =:key and art.visible = true and art.status = 'PUBLISHED' and art.id not in (:id) " +
//            " order by art.publish_date " +
//            " limit 5 ",
//            nativeQuery = true)
//    List<ArticleShortInfo> getLast4ArticleByType(@Param("key") String key, @Param("id") String id);
}

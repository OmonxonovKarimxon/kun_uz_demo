package com.company.controller;

import com.company.dto.article.ArticleFilterDTO;
import com.company.dto.region.RegionDTO;
import com.company.dto.article.ArticleCreateDTO;
import com.company.dto.article.ArticleDTO;
import com.company.dto.article.ArticleRequestDTO;
import com.company.enums.LangEnum;
import com.company.enums.ProfileRole;
import com.company.service.ArticleService;
import com.company.util.HttpHeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/article")
@RestController
@Slf4j
@Api(tags = "article's APIs")
public class ArticleController {
    @Autowired
    private ArticleService articleService;


    //  1. CREATE (Moderator) status(NotPublished)
    @ApiOperation(value = "article yaratish", notes = "articleni faqat publisher Statusli" +
            " odamgina yarata oladi boshqalarga not access deb chiqadi")
    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody @Valid ArticleCreateDTO dto,
                                    HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);

        String response = articleService.create(dto, profileId);
        return ResponseEntity.ok().body(response);
    }

    //  2. Update (Moderator (status to not publish))
    @ApiOperation(value = "update article", notes = "articleni yangilash")
    @PutMapping("/adm")
    public ResponseEntity<?> update(@RequestBody @Valid ArticleDTO dto, HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        String javob = articleService.update(dto, profileId);
        return ResponseEntity.ok().body(javob);
    }

    //  3. Delete Article (MODERATOR)
    @DeleteMapping("/adm")
    public ResponseEntity<?> delete(@RequestBody ArticleDTO dto, HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        String javob = articleService.delete(dto);
        return ResponseEntity.ok().body(javob);
    }

    // 4.Change status by id (PUBLISHER) (publish,not_publish)
    @ApiOperation(value = "publish", notes = "Articleni publish qilish")
    @PutMapping("adm/publish")
    public ResponseEntity<?> publish(@RequestBody ArticleDTO dto, HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.PUBLISHER);
        String javob = articleService.publish(dto, profileId);
        return ResponseEntity.ok().body(javob);
    }


    //  5. Get Last 5 Article By Types  ordered_by_created_date
    @ApiOperation(value = "getLast5ArticleByType", notes = "oxirgi 5 ta articlenni type boycha oladigan API")
    @GetMapping("/type/{typeKey}")
    public ResponseEntity<List<ArticleDTO>> getLast5ArticleByType(@PathVariable("typeKey") String typeKey) {
        List<ArticleDTO> response = articleService.getLast5ArticleByType(typeKey);
        return ResponseEntity.ok().body(response);
    }

    //  5. Get Last 3 Article By Types  ordered_by_created_date
    @ApiOperation(value = "get Last 3 Article By Type", notes = "faqar type key kiradi va va type boycha oxirgi uchta maqolani qaytaradi")
    @GetMapping("/type3/{typeKey}")
    public ResponseEntity<List<ArticleDTO>> getLast3ArticleByType(@PathVariable("typeKey") String typeKey) {
        List<ArticleDTO> response = articleService.getLast3ArticleByType(typeKey);
        return ResponseEntity.ok().body(response);
    }

    //  7. Get Last 8  Articles witch id not included in given list.
    @ApiOperation(
            value = "get Last 8 No Include last 3 article", notes = "eng songi 8 articleni olish"
    )
    @PostMapping("/last8")
    public ResponseEntity<List<ArticleDTO>> getLast8NoIn(@RequestBody ArticleRequestDTO dto) {
        List<ArticleDTO> response = articleService.getLat8ArticleNotIn(dto.getIdList());
        return ResponseEntity.ok().body(response);
    }

    // 8. Get Article By Id And Lang  ArticleFullInfo
    @ApiOperation(value = "get Last 5 Article By Id", notes = "Article Full Info with language")
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getLast5ArticleById(@PathVariable("id") String id,
                                                            @ApiParam(value = "lang", required = true)
                                                            @RequestHeader(value = "Accept-Language", defaultValue = "uz") LangEnum lang) {
        ArticleDTO response = articleService.getPublishedArticleById(id, lang);
        return ResponseEntity.ok().body(response);
    }

    //   9. Get Last 4 Article By Types and except given article id.   ArticleShortInfo
//    @PostMapping("/last8")
//    public ResponseEntity<List<ArticleDTO>> last4ArticleId(@RequestBody ArticleRequestDTO dto) {
//        List<ArticleDTO> response = articleService.getLat8ArticleNotIn(dto.getIdList());
//        return ResponseEntity.ok().body(response);
//
    //   }
    //  10. Get 4 most read articles  ArticleShortInfo
    @ApiOperation(value = "get Last 5 Article By Id", notes = "Article Full Info with language")
    @GetMapping("/theBestArticle")
    public ResponseEntity<?> theBestArticle() {
        List<ArticleDTO> response = articleService.theBestArticle();
        return ResponseEntity.ok().body(response);
    }


    //    11. Get Last 4 Article By TagName (Bitta article ni eng ohirida chiqib turadi.)    ArticleShortInfo
@ApiOperation(value = "search for tag", notes = "tag name input")
    @GetMapping("/tagName/{tag}")
    public ResponseEntity<?> tagName(@PathVariable("tag") String tag) {
        List<ArticleDTO> list = articleService.get4ArticleByTagName(tag);
        return ResponseEntity.ok().body(list);
    }


    // 12. Get Last 5 Article By Types  And By Region Key   ArticleShortInfo
    @GetMapping("/last4types/{id}")
    public ResponseEntity<?> last4types(@PathVariable("id") Integer typeId,
                                        @RequestBody RegionDTO dto) {
        List<ArticleDTO> list = articleService.get4ArticleBytypesAndRegion(typeId, dto);
        return ResponseEntity.ok().body(list);
    }


    //   13. Get Article list by Region Key (Pagination)

    @GetMapping("/region/{regionKay}")
    public ResponseEntity<?> getArticleByRegionKay(@PathVariable("regionKay") String regionKay,
                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "size", defaultValue = "5") int size) {

        PageImpl<ArticleDTO> list = articleService.getArticleByRegionKey(page, size, regionKay);
        return ResponseEntity.ok().body(list);
    }

    //  14. Get Last 5 Article Category Key  ArticleShortInfo
    @GetMapping("/last5ByCategoryKey/{key}")
    public ResponseEntity<?> last5ByCategoryKey(@PathVariable("key") String key) {
        List<ArticleDTO> list = articleService.last5ByCategoryKey(key);
        return ResponseEntity.ok().body(list);
    }

    //    15. Get Article By Category Key (Pagination)


    @GetMapping("/category/{categoryKay}")
    public ResponseEntity<?> getArticleByCategoryKay(@PathVariable("categoryKay") String categoryKay,
                                                     @RequestParam(value = "page") int page,
                                                     @RequestParam(value = "size") int size) {

        PageImpl<ArticleDTO> list = articleService.getArticleByCategoryKey(page, size, categoryKay);
        return ResponseEntity.ok().body(list);
    }


    // 19. Increase Article View Count by Article Id  (article_id)
    @GetMapping("/increase/{articleId}")
    public ResponseEntity<?> increase(@PathVariable("articleId") String articleId) {
        articleService.increase(articleId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "filter", notes = "filter qilish")
    @PostMapping("/filter")
    public ResponseEntity<?> filter(@RequestBody ArticleFilterDTO dto) {
        List<ArticleDTO> response = articleService.filter(dto);
        return ResponseEntity.ok().body(response);
    }
}

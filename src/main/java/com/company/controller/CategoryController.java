package com.company.controller;

import com.company.dto.CategoryDTO;
import com.company.dto.JwtDTO;
import com.company.dto.RegionDto;
import com.company.dto.article.TypesDTO;
import com.company.enums.LangEnum;
import com.company.enums.ProfileRole;
import com.company.service.CategoryService;
import com.company.util.HttpHeaderUtil;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/category")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    // SECURE
    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody CategoryDTO dto,
                                    HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        categoryService.create(dto);
        return ResponseEntity.ok().body("SuccsessFully created");
    }

    @GetMapping("/adm/admin")
    public ResponseEntity<?> getlist(@RequestHeader(value = "Accept-Laguage", defaultValue = "uz") LangEnum lang,
                                     HttpServletRequest request) {

        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        List<CategoryDTO> list = categoryService.getListOnlyForAdmin(lang);
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("/adm/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Integer id,
                                     @RequestBody CategoryDTO dto,
                                     HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        categoryService.update(id, dto);
        return ResponseEntity.ok().body("Succsessfully updated");
    }

    @DeleteMapping("/adm/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                     HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        categoryService.delete(id);
        return ResponseEntity.ok().body("Successfully deleted");
    }


    @GetMapping("/adm/pagination")
    public ResponseEntity<?> getPagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                           @RequestParam(value = "size", defaultValue = "5") int size,
                                           @RequestHeader(value = "Accept-Laguage", defaultValue = "uz") LangEnum lang,
                                           HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        PageImpl response = categoryService.pagination(page, size, lang);
        return ResponseEntity.ok().body(response);
    }

    //PUBLIC
    @GetMapping("/getByLang")
    public ResponseEntity<?> getPagination(@RequestBody CategoryDTO dto,
                                           @RequestHeader(value = "Accept-Laguage", defaultValue = "uz") LangEnum lang) {
        List<CategoryDTO> list = categoryService.getList(lang);
        return ResponseEntity.ok().body(list);
    }

}

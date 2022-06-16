package com.company.controller;

import com.company.dto.article.TypesDTO;
import com.company.entity.TypesEntity;
import com.company.enums.LangEnum;
import com.company.enums.ProfileRole;
import com.company.service.TypesService;
import com.company.util.HttpHeaderUtil;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/types")
@RestController
public class TypesController {
    @Autowired
    private TypesService typesService;

    // SECURE
    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody TypesDTO dto,
                                    HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        typesService.create(dto);
        return ResponseEntity.ok().body("SuccsessFully created");
    }

    @GetMapping("/adm/admin")
    public ResponseEntity<?> getlist(@RequestHeader(value = "Accept-Laguage", defaultValue = "uz") LangEnum lang,
                                     HttpServletRequest request) {

        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        List<TypesDTO> list = typesService.getListOnlyForAdmin(lang);
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("/adm/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Integer id,
                                     @RequestBody TypesDTO dto,
                                     HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        typesService.update(id, dto);
        return ResponseEntity.ok().body("Succsessfully updated");
    }

    @DeleteMapping("/adm/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                     HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        typesService.delete(id);
        return ResponseEntity.ok().body("Successfully deleted");
    }


    @GetMapping("/adm/pagination")
    public ResponseEntity<?> getPagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                           @RequestParam(value = "size", defaultValue = "5") int size,
                                           @RequestHeader(value = "Accept-Laguage", defaultValue = "uz") LangEnum lang,
                                           HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        PageImpl response = typesService.pagination(page, size, lang);
        return ResponseEntity.ok().body(response);
    }

    //PUBLIC
    @GetMapping("/getByLang")
    public ResponseEntity<?> getPagination(@RequestBody TypesDTO dto,
                                           @RequestHeader(value = "Accept-Laguage", defaultValue = "uz") LangEnum lang) {
        List<TypesDTO> list = typesService.getList(lang);
        return ResponseEntity.ok().body(list);
    }
}

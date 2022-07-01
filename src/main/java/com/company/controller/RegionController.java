package com.company.controller;

import com.company.dto.region.RegionCreateDTO;
import com.company.dto.region.RegionDTO;
import com.company.dto.types.TypesDTO;
import com.company.enums.LangEnum;
import com.company.enums.ProfileRole;
import com.company.service.RegionService;
import com.company.util.HttpHeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/region")
@RestController
public class RegionController {
    @Autowired
    private RegionService regionService;

    // SECURE
    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody @Valid RegionCreateDTO dto,
                                    HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        regionService.create(dto);
        return ResponseEntity.ok().body("SuccsessFully created");
    }

    @GetMapping("/adm/admin")
    public ResponseEntity<?> getlist(@RequestHeader(value = "Accept-Laguage", defaultValue = "uz") LangEnum lang,
                                     HttpServletRequest request) {

        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        List<RegionDTO> list = regionService.getListOnlyForAdmin(lang);
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("/adm/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Integer id,
                                     @RequestBody @Valid RegionDTO dto,
                                     HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        regionService.update(id, dto);
        return ResponseEntity.ok().body("Succsessfully updated");
    }

    @DeleteMapping("/adm/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                     HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        regionService.delete(id);
        return ResponseEntity.ok().body("Successfully deleted");
    }


    @GetMapping("/adm/pagination")
    public ResponseEntity<?> getPagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                           @RequestParam(value = "size", defaultValue = "5") int size,
                                           @RequestHeader(value = "Accept-Laguage", defaultValue = "uz") LangEnum lang,
                                           HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        PageImpl response = regionService.pagination(page, size, lang);
        return ResponseEntity.ok().body(response);
    }

    //PUBLIC
    @GetMapping("/getByLang")
    public ResponseEntity<?> getPagination(@RequestBody @Valid TypesDTO dto,
                                           @RequestHeader(value = "Accept-Laguage", defaultValue = "uz") LangEnum lang) {
        List<RegionDTO> list = regionService.getList(lang);
        return ResponseEntity.ok().body(list);
    }


}

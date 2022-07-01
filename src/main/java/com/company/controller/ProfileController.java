package com.company.controller;

import com.company.dto.profile.ProfileCreateDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.dto.profile.ProfileFilterDTO;
import com.company.enums.ProfileRole;
import com.company.service.ProfileService;
import com.company.util.HttpHeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/profile")
@RestController
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/adm")
    public ResponseEntity<String> create(@RequestBody @Valid ProfileCreateDTO profileDto,
                                    HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        String response = profileService.create(profileDto);
        return ResponseEntity.ok().body(response);
    }


    @PutMapping("/adm/detail")
    public ResponseEntity<?> update(@RequestBody @Valid ProfileDTO dto,
                                    HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request);
        profileService.update(profileId, dto);
        return ResponseEntity.ok().body("Sucsessfully updated");
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody ProfileDTO dto,
                                    HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        profileService.update(id, dto);
        return ResponseEntity.ok().body("Succsessfully updated");
    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        profileService.delete(id);
        return ResponseEntity.ok().body("Sucsessfully deleted");
    }

    @GetMapping("/adm/list")
    public ResponseEntity<?> pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                        @RequestParam(value = "size", defaultValue = "5") Integer size,
                                        HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        PageImpl response = profileService.pagination(page, size);
        return ResponseEntity.ok().body(response);

    }

    @GetMapping("/adm/filter")
    public  ResponseEntity<?>filter(@RequestBody ProfileFilterDTO dto,
                                    HttpServletRequest request){
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        List<ProfileDTO> profileDTOList =  profileService.filter(dto);

        return  ResponseEntity.ok().body(profileDTOList);
    }
}

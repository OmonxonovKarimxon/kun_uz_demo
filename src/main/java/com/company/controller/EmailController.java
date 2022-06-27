package com.company.controller;

import com.company.entity.EmailHistoryEntity;
import com.company.enums.ProfileRole;
import com.company.service.EmailService;
import com.company.util.HttpHeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;


    @GetMapping("/getEmail")
    public ResponseEntity<?> getEmail(@RequestParam(value = "size", defaultValue = "5") Integer size,
                                      @RequestParam(value = "page", defaultValue = "1") Integer page,
                                      HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);

      PageImpl<EmailHistoryEntity> list= emailService.pegination(size,page);
      return ResponseEntity.ok().body(list);

    }
}

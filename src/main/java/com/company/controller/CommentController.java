package com.company.controller;

import com.company.dto.CommentDTO;
import com.company.dto.RegionDto;
import com.company.enums.ProfileRole;
import com.company.service.CommentService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/comment")
@RestController

public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CommentDTO dto,
                                    @RequestHeader("Authorization") String jwt) {
        Integer userId = JwtUtil.decode(jwt);
        commentService.create(dto, userId);
        return ResponseEntity.ok().body("SuccsessFully created");
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody CommentDTO dto,
                                    @RequestHeader("Authorization") String jwt) {
        Integer profileId = JwtUtil.decode(jwt);
        commentService.update(dto, profileId);
        return ResponseEntity.ok().body("SuccsessFully update");
    }

    @PutMapping("/list")
    public ResponseEntity<?> list(@RequestBody CommentDTO dto ) {

        List<CommentDTO> list =  commentService.list(dto);
        return ResponseEntity.ok().body(list);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody CommentDTO dto,
                                    @RequestHeader("Authorization") String jwt) {
        Integer profileId = JwtUtil.decode(jwt);
        commentService.delete(dto, profileId);
        return ResponseEntity.ok().body("SuccsessFully deleted");
    }


    @DeleteMapping("/deleteForAdmin")
    public ResponseEntity<?> deleteForAdmin(@RequestBody CommentDTO dto,
                                            @RequestHeader("Authorization") String jwt) {
          JwtUtil.decode(jwt,ProfileRole.ADMIN);
        commentService.deleteForAdmin(dto);
        return ResponseEntity.ok().body("SuccsessFully deleted");
    }
}

package com.company.controller;

import com.company.dto.comment.CommentDTO;
import com.company.dto.comment.CommentResponseDTO;
import com.company.enums.ProfileRole;
import com.company.service.CommentService;
import com.company.util.HttpHeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/comment")
@RestController

public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody CommentDTO dto,
                                    HttpServletRequest request) {
        Integer userId = HttpHeaderUtil.getId(request);
        commentService.create(dto, userId);
        return ResponseEntity.ok().body("SuccsessFully created");
    }

    @PutMapping("/adm")
    public ResponseEntity<?> update(@RequestBody CommentDTO dto,
                                    HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request);
        commentService.update(dto, profileId);
        return ResponseEntity.ok().body("SuccsessFully update");
    }

    //get list by Article ID mandatory give Article ID
    @PutMapping("/listByArticleId")
    public ResponseEntity<?> list(@RequestBody CommentDTO dto ) {

        List<CommentResponseDTO> list =  commentService.list(dto);
        return ResponseEntity.ok().body(list);
    }

    //comment list for admin with pagination
    @PutMapping("/adm/commentListForAdmin")
    public ResponseEntity<?> pagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "size", defaultValue = "2") int size,
                                        HttpServletRequest request){
         HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        PageImpl list = commentService.pagination(page,size);
         return ResponseEntity.ok().body(list);
    }


    @DeleteMapping("/adm/delete")
    public ResponseEntity<?> delete(@RequestBody CommentDTO dto,
                                    HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request);
        commentService.delete(dto, profileId);
        return ResponseEntity.ok().body("SuccsessFully deleted");
    }


    @DeleteMapping("/adm/deleteForAdmin")
    public ResponseEntity<?> deleteForAdmin(@RequestBody CommentDTO dto,
                                            HttpServletRequest request) {
        HttpHeaderUtil.getId(request);
        commentService.deleteForAdmin(dto);
        return ResponseEntity.ok().body("SuccsessFully deleted");
    }
    @PutMapping("/listByCommentId")
    public ResponseEntity<?> listByCommentId(@RequestBody CommentDTO dto ) {

        List<CommentResponseDTO> list =  commentService.listByCommentId(dto);
        return ResponseEntity.ok().body(list);
    }
}

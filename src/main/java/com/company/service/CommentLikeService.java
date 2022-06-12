package com.company.service;

import com.company.entity.*;
import com.company.enums.LikeStatus;
import com.company.repository.ArticleLikeRepository;
import com.company.repository.CommentLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentLikeService {

    @Autowired
    private CommentLikeRepository commentLikeRepository;


    public void create(CommentEntity comment, ProfileEntity profile) {
        CommentLikeEntity entity = new CommentLikeEntity();

        entity.setStatus(LikeStatus.ACTIVE);
        entity.setProfile(profile);
        entity.setComment(comment);

        commentLikeRepository.save(entity);

    }
}

package com.company.service;

import com.company.entity.*;
import com.company.enums.LikeStatus;
import com.company.exps.ItemNotFoundEseption;
import com.company.repository.CommentLikeRepository;
import com.company.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentLikeService {

     @Autowired
    private CommentLikeRepository commentLikeRepository;
    @Autowired
    private CommentRepository commentRepository;

    public void articleLike(Integer commentId, Integer pId) {
        likeDislike(commentId, pId, LikeStatus.LIKE);
    }

    public void articleDisLike(Integer commentId, Integer pId) {
        likeDislike(commentId, pId, LikeStatus.DISLIKE);
    }

    private void likeDislike(Integer commentId, Integer pId, LikeStatus status) {
        Optional<CommentLikeEntity> optional = commentLikeRepository.findExists(commentId, pId);
        if (optional.isPresent()) {
            CommentLikeEntity like = optional.get();
            like.setStatus(status);
            commentLikeRepository.save(like);
            return;
        }
        boolean articleExists = commentRepository.existsById(commentId);
        if (!articleExists) {
            throw new ItemNotFoundEseption("Comment Not Found");
        }

        CommentLikeEntity like = new CommentLikeEntity();
        like.setComment(new CommentEntity(commentId));
        like.setProfile(new ProfileEntity(pId));
        like.setStatus(status);
        commentLikeRepository.save(like);
    }

    public void removeLike(Integer commentId, Integer pId) {
       /* Optional<ArticleLikeEntity> optional = articleLikeRepository.findExists(articleId, pId);
        optional.ifPresent(articleLikeEntity -> {
            articleLikeRepository.delete(articleLikeEntity);
        });*/
        commentLikeRepository.delete(commentId, pId);
    }
}

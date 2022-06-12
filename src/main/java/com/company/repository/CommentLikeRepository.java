package com.company.repository;

import com.company.entity.ArticleLikeEntity;
import com.company.entity.CommentEntity;
import com.company.entity.CommentLikeEntity;
import org.springframework.data.repository.CrudRepository;

public interface CommentLikeRepository extends CrudRepository<CommentLikeEntity, Integer> {




}

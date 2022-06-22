package com.company.repository;

import com.company.entity.ArticleSaveEntity;
import com.company.entity.ProfileEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ArticleSaveRepository extends CrudRepository<ArticleSaveEntity, Integer> {


    List<ArticleSaveEntity> findByProfile(ProfileEntity entity);

    @Transactional
    @Modifying
    @Query("DELETE FROM ArticleSaveEntity a where  a.id=:id and a.profile.id =:profileId")
    void delete(Integer id, Integer profileId);
}

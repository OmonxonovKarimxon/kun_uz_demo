package com.company.repository;

import com.company.entity.ProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends PagingAndSortingRepository<ProfileEntity, Integer> {

    List<ProfileEntity> findAllByVisible(Boolean b);

    Optional<ProfileEntity> findByEmail(String email);

    Page<ProfileEntity> findByVisible(boolean b, Pageable pageable);
}


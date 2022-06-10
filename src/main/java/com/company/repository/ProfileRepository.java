package com.company.repository;

import com.company.entity.ProfileEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {

    List<ProfileEntity> findAllByVisible(Boolean b);

    Optional<ProfileEntity> findByEmail(String email);
}

package com.company.repository;

import com.company.entity.TypesEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TypesRepository extends CrudRepository<TypesEntity, Integer> {

    Optional<TypesEntity> findByKey(String key);

    List<TypesEntity> findAllByVisible(Boolean b);


}

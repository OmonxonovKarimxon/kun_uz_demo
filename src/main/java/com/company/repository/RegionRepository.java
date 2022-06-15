package com.company.repository;

import com.company.entity.ProfileEntity;
import com.company.entity.RegionEntity;
import com.company.entity.TypesEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RegionRepository extends PagingAndSortingRepository<RegionEntity, Integer> {

    Optional<RegionEntity> findByKey(String key);

    List<RegionEntity> findAllByVisible(Boolean b);


}

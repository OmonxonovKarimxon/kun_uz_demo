package com.company.repository;

import com.company.entity.EmailHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface EmailHistoryRepository extends PagingAndSortingRepository<EmailHistoryEntity, Integer> {


    @Query( value = "select count(*) " +
            " from email_history " +
            " where email =:email " +
            " and created_date + INTERVAL '1 MINUTE' > now() ", nativeQuery = true)
    Long getEmailCount(@Param("email") String email);


    Page<EmailHistoryEntity> findAll(Pageable pageable);


}

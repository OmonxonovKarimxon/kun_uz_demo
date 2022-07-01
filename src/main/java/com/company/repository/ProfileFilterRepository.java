package com.company.repository;

import com.company.dto.profile.ProfileFilterDTO;
import com.company.entity.ProfileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ProfileFilterRepository {

    @Autowired
    private EntityManager entityManager;

    public List<ProfileEntity> filter(ProfileFilterDTO dto) {

        StringBuilder builder = new StringBuilder();
        builder.append(" SELECT a FROM ProfileEntity a ");
        builder.append(" where visible = true  ");

        if (dto.getId() != null) {
            builder.append(" and a.id = '" + dto.getId() + "' ");
        }

        if (dto.getEmail() != null) {
            builder.append(" and a.email = '" + dto.getEmail() + "' ");
        }

        if (dto.getName() != null) {
            builder.append(" and a.name = '" +dto.getName() + "' ");
        }

        if (dto.getSurname() != null) {
            builder.append(" and a.surname='" + dto.getSurname() + "' ");
        }

        if (dto.getRole() != null) {
            builder.append(" and a.role='" + dto.getRole() + "'");
        }
        if (dto.getRegisterDateFrom() != null && dto.getRegisterDateTo() == null) {
            // builder.append(" and a.publishDate = '" + dto.getPublishedDateFrom() + "' ");
            LocalDate localDate = LocalDate.parse(dto.getRegisterDateFrom());
            LocalDateTime fromTime = LocalDateTime.of(localDate, LocalTime.MIN); // yyyy-MM-dd 00:00:00
            LocalDateTime toTime = LocalDateTime.of(localDate, LocalTime.MAX); // yyyy-MM-dd 23:59:59
            builder.append(" and a.publishDate between '" + fromTime + "' and '" + toTime + "' ");
        } else if (dto.getRegisterDateFrom()  != null && dto.getRegisterDateTo() != null) {
            builder.append(" and a.publishDate between '" + dto.getRegisterDateFrom() + "' and '" + dto.getRegisterDateTo() + "' ");
        }

        Query query = entityManager.createQuery(builder.toString());
        List<ProfileEntity> profileList = query.getResultList();

        return profileList;
    }

}

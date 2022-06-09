package com.company.service;

import com.company.dto.ArticleTypeDto;
import com.company.dto.RegionDto;
import com.company.entity.ArticleTypeEntity;
import com.company.entity.RegionEntity;
import com.company.exception.BadRequestException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.ArticleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;


    public ArticleTypeDto create(ArticleTypeDto dto) {

        Optional<ArticleTypeEntity> optional = articleTypeRepository.findByKey(dto.getKey());

        if (optional.isPresent()) {
            throw new BadRequestException("region already exists");
        }

        ArticleTypeEntity entity = new ArticleTypeEntity();
        entity.setCreatedDate(LocalDateTime.now());
        entity.setKey(dto.getKey());
        entity.setNameEng(dto.getNameENG());
        entity.setNameRu(dto.getNameRU());
        entity.setNameUz(dto.getNameUZ());
        entity.setVisible(true);

        articleTypeRepository.save(entity);

        dto.setId(entity.getId());

        return dto;
    }


    public ArticleTypeDto update(ArticleTypeDto dto) {

        ArticleTypeEntity entity = isValid(dto);


        entity.setNameEng(dto.getNameENG());
        entity.setNameRu(dto.getNameRU());
        entity.setNameUz(dto.getNameUZ());

        articleTypeRepository.save(entity);

        dto.setId(entity.getId());

        return dto;

    }


    public List<ArticleTypeDto> articleTypeList() {
        List<ArticleTypeDto> list = new LinkedList<>();
        for (ArticleTypeEntity entity : articleTypeRepository.findAll()) {
            ArticleTypeDto dto = new ArticleTypeDto();
            dto.setId(entity.getId());
            dto.setKey(entity.getKey());
            dto.setNameENG(entity.getNameEng());
            dto.setNameRU(entity.getNameRu());
            dto.setNameUZ(entity.getNameUz());

            list.add(dto);
        }

        return list;
    }


    public String delete(ArticleTypeDto dto) {

        ArticleTypeEntity entity = isValid(dto);

        if (entity.getVisible()) {
            entity.setVisible(false);
        }


        articleTypeRepository.save(entity);

        dto.setId(entity.getId());
        return "succesfully deleted";
    }


    public ArticleTypeEntity isValid(ArticleTypeDto dto) {
        Optional<ArticleTypeEntity> optional = articleTypeRepository.findById(dto.getId());

        ArticleTypeEntity entity = optional.get();

        if (optional.isEmpty()) {

            throw new ItemNotFoundException("we have not this region");

        }
        return entity;
    }

}

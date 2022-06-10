package com.company.service;

import com.company.dto.ArticleTypeDto;
import com.company.dto.CategoryDto;
import com.company.dto.RegionDto;
import com.company.entity.ArticleTypeEntity;
import com.company.entity.CategoryEntity;
import com.company.exps.AlreadyExist;
import com.company.exps.BadRequestException;
import com.company.exps.ItemNotFoundEseption;
import com.company.repository.ArticleTypeRepository;
import com.company.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleTypeService {

    @Autowired
    private ArticleTypeRepository articleTypeRepository;

    public void create(ArticleTypeDto articleTypeDto) {

        Optional<ArticleTypeEntity> articleTypeEntity = articleTypeRepository.findByKey(articleTypeDto.getKey());

        if (articleTypeEntity.isPresent()) {
            throw new AlreadyExist("Already exist");
        }

        isValid(articleTypeDto);


        ArticleTypeEntity entity = new ArticleTypeEntity();
        entity.setKey(articleTypeDto.getKey());
        entity.setNameUz(articleTypeDto.getNameUz());
        entity.setNameRu(articleTypeDto.getNameRu());
        entity.setNameEn(articleTypeDto.getNameEn());

        articleTypeRepository.save(entity);
    }
    private void isValid(ArticleTypeDto dto) {
        if (dto.getKey().length() < 5) {
            throw new BadRequestException("key to short");
        }

        if (dto.getNameUz() == null || dto.getNameUz().length() < 3) {
            throw new BadRequestException("wrong name uz");
        }

        if (dto.getNameRu() == null || dto.getNameRu().length() < 3) {
            throw new BadRequestException("wrong name ru");
        }

        if (dto.getNameEn() == null || dto.getNameEn().length() < 3) {
            throw new BadRequestException("wrong name en");
        }
    }

    public List<ArticleTypeDto> getList() {

        Iterable<ArticleTypeEntity> all = articleTypeRepository.findAllByVisible(true);
        List<ArticleTypeDto> dtoList = new LinkedList<>();

        all.forEach(articleTypeEntity -> {
            ArticleTypeDto dto = new ArticleTypeDto();
            dto.setKey(articleTypeEntity.getKey());
            dto.setNameUz(articleTypeEntity.getNameUz());
            dto.setNameRu(articleTypeEntity.getNameRu());
            dto.setNameEn(articleTypeEntity.getNameEn());
            dtoList.add(dto);
        });
        return dtoList;
    }
    public List<ArticleTypeDto> getListOnlyForAdmin() {

        Iterable<ArticleTypeEntity> all = articleTypeRepository.findAll();
        List<ArticleTypeDto> dtoList = new LinkedList<>();

        all.forEach(articleTypeEntity -> {
            ArticleTypeDto dto = new ArticleTypeDto();
            dto.setKey(articleTypeEntity.getKey());
            dto.setNameUz(articleTypeEntity.getNameUz());
            dto.setNameRu(articleTypeEntity.getNameRu());
            dto.setNameEn(articleTypeEntity.getNameEn());
            dtoList.add(dto);
        });
        return dtoList;
    }

    public void update(Integer id, RegionDto dto) {
        Optional<ArticleTypeEntity> articleTypeEntity = articleTypeRepository.findById(id);

        if (articleTypeEntity.isEmpty()) {
            throw new ItemNotFoundEseption("not found articleType");
        }

        ArticleTypeEntity entity = articleTypeEntity.get();

        entity.setKey(dto.getKey());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        articleTypeRepository.save(entity);
    }

    public void delete(Integer id) {

        Optional<ArticleTypeEntity> entity = articleTypeRepository.findById(id);

        if (entity.isEmpty()) {
            throw new ItemNotFoundEseption("not found articleType");
        }

        if (entity.get().getVisible().equals(Boolean.FALSE)) {
            throw new AlreadyExist("this articleType already visible false");
        }

        ArticleTypeEntity articleType = entity.get();

        articleType.setVisible(Boolean.FALSE);

        articleTypeRepository.save(articleType);
    }
}

package com.company.service;

import com.company.dto.article.ArticleTypeDTO;
import com.company.dto.RegionDto;
import com.company.entity.TypesEntity;
import com.company.exps.AlreadyExist;
import com.company.exps.BadRequestException;
import com.company.exps.ItemNotFoundEseption;
import com.company.repository.TypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class TypesService {

    @Autowired
    private TypesRepository typesRepository;

    public void create(ArticleTypeDTO articleTypeDto) {

        Optional<TypesEntity> articleTypeEntity = typesRepository.findByKey(articleTypeDto.getKey());

        if (articleTypeEntity.isPresent()) {
            throw new AlreadyExist("Already exist");
        }

        isValid(articleTypeDto);


        TypesEntity entity = new TypesEntity();
        entity.setKey(articleTypeDto.getKey());
        entity.setNameUz(articleTypeDto.getNameUz());
        entity.setNameRu(articleTypeDto.getNameRu());
        entity.setNameEn(articleTypeDto.getNameEn());

        typesRepository.save(entity);
    }
    private void isValid(ArticleTypeDTO dto) {
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

    public List<ArticleTypeDTO> getList() {

        Iterable<TypesEntity> all = typesRepository.findAllByVisible(true);
        List<ArticleTypeDTO> dtoList = new LinkedList<>();

        all.forEach(typesEntity -> {
            ArticleTypeDTO dto = new ArticleTypeDTO();
            dto.setKey(typesEntity.getKey());
            dto.setNameUz(typesEntity.getNameUz());
            dto.setNameRu(typesEntity.getNameRu());
            dto.setNameEn(typesEntity.getNameEn());
            dtoList.add(dto);
        });
        return dtoList;
    }
    public List<ArticleTypeDTO> getListOnlyForAdmin() {

        Iterable<TypesEntity> all = typesRepository.findAll();
        List<ArticleTypeDTO> dtoList = new LinkedList<>();

        all.forEach(typesEntity -> {
            ArticleTypeDTO dto = new ArticleTypeDTO();
            dto.setKey(typesEntity.getKey());
            dto.setNameUz(typesEntity.getNameUz());
            dto.setNameRu(typesEntity.getNameRu());
            dto.setNameEn(typesEntity.getNameEn());
            dtoList.add(dto);
        });
        return dtoList;
    }

    public void update(Integer id, RegionDto dto) {
        Optional<TypesEntity> articleTypeEntity = typesRepository.findById(id);

        if (articleTypeEntity.isEmpty()) {
            throw new ItemNotFoundEseption("not found articleType");
        }

        TypesEntity entity = articleTypeEntity.get();

        entity.setKey(dto.getKey());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        typesRepository.save(entity);
    }

    public void delete(Integer id) {

        Optional<TypesEntity> entity = typesRepository.findById(id);

        if (entity.isEmpty()) {
            throw new ItemNotFoundEseption("not found articleType");
        }

        if (entity.get().getVisible().equals(Boolean.FALSE)) {
            throw new AlreadyExist("this articleType already visible false");
        }

        TypesEntity articleType = entity.get();

        articleType.setVisible(Boolean.FALSE);

        typesRepository.save(articleType);
    }
}

package com.company.service;

import com.company.dto.article.TypesDTO;
import com.company.entity.TypesEntity;
import com.company.enums.LangEnum;
import com.company.exps.AlreadyExist;
import com.company.exps.BadRequestException;
import com.company.exps.ItemNotFoundEseption;
import com.company.repository.TypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class TypesService {

    @Autowired
    private TypesRepository typesRepository;

    public void create(TypesDTO typesDto) {

        Optional<TypesEntity> articleTypeEntity = typesRepository.findByKey(typesDto.getKey());

        if (articleTypeEntity.isPresent()) {
            throw new AlreadyExist("Already exist");
        }

        isValid(typesDto);


        TypesEntity entity = new TypesEntity();
        entity.setKey(typesDto.getKey());
        entity.setNameUz(typesDto.getNameUz());
        entity.setNameRu(typesDto.getNameRu());
        entity.setNameEn(typesDto.getNameEn());

        typesRepository.save(entity);
    }

    public void update(Integer id, TypesDTO dto) {
        Optional<TypesEntity> articleTypeEntity = typesRepository.findById(id);

        if (articleTypeEntity.isEmpty()) {
            throw new ItemNotFoundEseption("not found articleType");
        }

        TypesEntity entity = articleTypeEntity.get();

        entity.setKey(dto.getKey());
//        entity.setNameUz(dto.getNameUz());
//        entity.setNameRu(dto.getNameRu());
//        entity.setNameEn(dto.getNameEn());
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

    public TypesEntity getType(Integer typeId) {
        Optional<TypesEntity> entity = typesRepository.findById(typeId);
        if (entity.isEmpty()){
            throw new ItemNotFoundEseption("this type is not");
        }
        return entity.get();
    }

    public List<TypesDTO> getList(LangEnum lang) {

        Iterable<TypesEntity> all = typesRepository.findAllByVisible(true);
        return entityToDto(all, lang);
    }

    public List<TypesDTO> getListOnlyForAdmin(LangEnum lang) {

        Iterable<TypesEntity> all = typesRepository.findAll();
        return entityToDto(all, lang);
    }



    public PageImpl pagination(int page, int size, LangEnum lang) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TypesEntity> list = typesRepository.findAll(pageable) ;

        List<TypesEntity> all = list.getContent();

        List<TypesDTO> dtoList = entityToDto(all, lang);

        return new PageImpl(dtoList,pageable, list.getTotalElements());
    }

    private List<TypesDTO> entityToDto( Iterable<TypesEntity> all,LangEnum lang){
        List<TypesDTO> dtoList = new LinkedList<>();

        all.forEach(typesEntity -> {
            TypesDTO dto = new TypesDTO();
            dto.setKey(typesEntity.getKey());

            switch (lang) {
                case ru -> dto.setLang(typesEntity.getNameRu());
                case en -> dto.setLang(typesEntity.getNameEn());
                case uz -> dto.setLang(typesEntity.getNameUz());
            }
            dtoList.add(dto);
        });
        return  dtoList;
    }


    private void isValid(TypesDTO dto) {
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
}


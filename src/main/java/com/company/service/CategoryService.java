package com.company.service;

import com.company.dto.category.CategoryCreateDTO;
import com.company.dto.category.CategoryDTO;
import com.company.entity.CategoryEntity;
import com.company.enums.LangEnum;
import com.company.exps.BadRequestException;
import com.company.exps.ItemNotFoundEseption;
import com.company.exps.NotPermissionException;
import com.company.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public void create(CategoryCreateDTO categoryDto) {

        Optional<CategoryEntity> category = categoryRepository.findByKey(categoryDto.getKey());

        if (category.isPresent()) {
            throw new NotPermissionException("Already exist");
        }


        CategoryEntity entity = new CategoryEntity();
        entity.setKey(categoryDto.getKey());
        entity.setNameUz(categoryDto.getNameUz());
        entity.setNameRu(categoryDto.getNameRu());
        entity.setNameEn(categoryDto.getNameEn());

        categoryRepository.save(entity);
    }

    public List<CategoryDTO> getList(LangEnum lang) {

        Iterable<CategoryEntity> all = categoryRepository.findAllByVisible(true);
        return entityToDto(all, lang);
    }

    public List<CategoryDTO> getListOnlyForAdmin(LangEnum lang) {

        Iterable<CategoryEntity> all = categoryRepository.findAllByVisible(true);
        return entityToDto(all, lang);
    }


    private List<CategoryDTO> entityToDto( Iterable<CategoryEntity> all,LangEnum lang){
        List<CategoryDTO> dtoList = new LinkedList<>();

        all.forEach(categoryEntity -> {
            CategoryDTO dto = new CategoryDTO();
            dto.setKey(categoryEntity.getKey());
            dto.setId(categoryEntity.getId());

            switch (lang) {
                case ru -> dto.setLang(categoryEntity.getNameRu());
                case en -> dto.setLang(categoryEntity.getNameEn());
                case uz -> dto.setLang(categoryEntity.getNameUz());
            }
            dtoList.add(dto);
        });
        return  dtoList;
    }


    public void update(Integer id, CategoryDTO dto) {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);

        if (categoryEntity.isEmpty()) {
            throw new ItemNotFoundEseption("not found category");
        }

        CategoryEntity entity = categoryEntity.get();
        entity.setKey(dto.getKey());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        categoryRepository.save(entity);
    }

    public void delete(Integer id) {

        Optional<CategoryEntity> entity = categoryRepository.findById(id);

        if (entity.isEmpty()) {
            throw new ItemNotFoundEseption("not found category");
        }

        if (entity.get().getVisible().equals(Boolean.FALSE)) {
            throw new NotPermissionException("this category already visible false");
        }

        CategoryEntity category = entity.get();
        category.setVisible(Boolean.FALSE);
        categoryRepository.save(category);
    }

    public CategoryEntity get(Integer id) {
        return categoryRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundEseption("category not found");
        });
    }
    public CategoryDTO get(CategoryEntity entity, LangEnum lang) {
        CategoryDTO dto = new CategoryDTO();
        dto.setKey(entity.getKey());
        switch (lang) {
            case ru:
                dto.setLang(entity.getNameRu());
                break;
            case en:
                dto.setLang(entity.getNameEn());
                break;
            case uz:
                dto.setLang(entity.getNameUz());
                break;
        }
        return dto;
    }

    public PageImpl pagination(int page, int size, LangEnum lang) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<CategoryEntity> list = categoryRepository.findAll(pageable) ;

        List<CategoryEntity> all = list.getContent();

        List<CategoryDTO> dtoList = entityToDto(all, lang);

        return new PageImpl(dtoList,pageable, list.getTotalElements());
    }




}

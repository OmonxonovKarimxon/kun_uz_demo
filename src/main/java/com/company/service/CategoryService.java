package com.company.service;

import com.company.dto.CategoryDto;
import com.company.dto.RegionDto;
import com.company.entity.CategoryEntity;
import com.company.entity.RegionEntity;
import com.company.exps.AlreadyExist;
import com.company.exps.BadRequestException;
import com.company.exps.ItemNotFoundEseption;
import com.company.repository.CategoryRepository;
import com.company.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public void create(CategoryDto categoryDto) {

        Optional<CategoryEntity> category = categoryRepository.findByKey(categoryDto.getKey());

        if (category.isPresent()) {
            throw new AlreadyExist("Already exist");
        }

        isValid(categoryDto);

        CategoryEntity entity = new CategoryEntity();
        entity.setKey(categoryDto.getKey());
        entity.setNameUz(categoryDto.getNameUz());
        entity.setNameRu(categoryDto.getNameRu());
        entity.setNameEn(categoryDto.getNameEn());

        categoryRepository.save(entity);
    }

    public List<CategoryDto> getList() {

        Iterable<CategoryEntity> all = categoryRepository.findAllByVisible(true);
        List<CategoryDto> dtoList = new LinkedList<>();

        all.forEach(categoryEntity -> {
            dtoList.add(toDTO(categoryEntity));
        });
        return dtoList;
    }

    public List<CategoryDto> getListOnlyForAdmin() {

        Iterable<CategoryEntity> all = categoryRepository.findAll();
        List<CategoryDto> dtoList = new LinkedList<>();

        all.forEach(categoryEntity -> {
            dtoList.add(toDTO(categoryEntity));
        });
        return dtoList;
    }

    public void update(Integer id, CategoryDto dto) {
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
            throw new AlreadyExist("this category already visible false");
        }

        CategoryEntity category = entity.get();
        category.setVisible(Boolean.FALSE);
        categoryRepository.save(category);
    }

    public CategoryDto toDTO(CategoryEntity entity) {
        CategoryDto dto = new CategoryDto();
        dto.setId(entity.getId());
        dto.setKey(entity.getKey());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        return dto;
    }

    private void isValid(CategoryDto dto) {
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

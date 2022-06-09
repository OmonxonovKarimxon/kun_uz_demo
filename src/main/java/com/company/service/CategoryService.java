package com.company.service;

import com.company.dto.CategoryDto;
import com.company.dto.RegionDto;
import com.company.entity.CategoryEntity;
import com.company.entity.RegionEntity;
import com.company.exception.BadRequestException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;


    public CategoryDto create(CategoryDto catigoryDto) {

        Optional<CategoryEntity> optional = categoryRepository.findByKey(catigoryDto.getKey());

        if (optional.isPresent()) {
            throw new BadRequestException("region already exists");
        }

        CategoryEntity entity = new CategoryEntity();
        entity.setCreatedDate(LocalDateTime.now());
        entity.setKey(catigoryDto.getKey());
        entity.setNameEng(catigoryDto.getNameENG());
        entity.setNameRu(catigoryDto.getNameRU());
        entity.setNameUz(catigoryDto.getNameUZ());
        entity.setVisible(true);

        categoryRepository.save(entity);

        catigoryDto.setId(entity.getId());

        return catigoryDto;
    }


    public CategoryDto update(CategoryDto categoryDto) {

        CategoryEntity entity = isValid(categoryDto);


        entity.setNameEng(categoryDto.getNameENG());
        entity.setNameRu(categoryDto.getNameRU());
        entity.setNameUz(categoryDto.getNameUZ());

        categoryRepository.save(entity);

        categoryDto.setId(entity.getId());

        return categoryDto;

    }


    public List<CategoryDto> categoryList() {
        List<CategoryDto> list = new LinkedList<>();
        for (CategoryEntity entity : categoryRepository.findAll()) {
            CategoryDto dto = new CategoryDto();
            dto.setId(entity.getId());
            dto.setKey(entity.getKey());
            dto.setNameENG(entity.getNameEng());
            dto.setNameRU(entity.getNameRu());
            dto.setNameUZ(entity.getNameUz());

            list.add(dto);
        }

        return list;
    }


    public String delete(CategoryDto dto) {

        CategoryEntity entity = isValid(dto);

        if (entity.getVisible()) {
            entity.setVisible(false);
        }


        categoryRepository.save(entity);

        dto.setId(entity.getId());
        return "succesfully deleted";
    }


    public CategoryEntity isValid(CategoryDto dto) {
        Optional<CategoryEntity> optional = categoryRepository.findById(dto.getId());

        CategoryEntity entity = optional.get();

        if (optional.isEmpty()) {

            throw new ItemNotFoundException("we have not this region");

        }
        return entity;
    }

}

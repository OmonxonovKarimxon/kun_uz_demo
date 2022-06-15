package com.company.service;

import com.company.dto.CategoryDTO;
import com.company.dto.RegionDto;
import com.company.dto.article.TypesDTO;
import com.company.entity.CategoryEntity;
import com.company.entity.TypesEntity;
import com.company.enums.LangEnum;
import com.company.exps.AlreadyExist;
import com.company.exps.BadRequestException;
import com.company.exps.ItemNotFoundEseption;
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

    public void create(CategoryDTO categoryDto) {

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

    public List<CategoryDTO> getList(LangEnum lang) {

        Iterable<CategoryEntity> all = categoryRepository.findAllByVisible(true);
     return entityToDto(all, lang);
    }

    public List<CategoryDTO> getListOnlyForAdmin(LangEnum lang) {

        Iterable<CategoryEntity> all = categoryRepository.findAll();
        return entityToDto(all, lang);
    }


    private List<CategoryDTO> entityToDto( Iterable<CategoryEntity> all,LangEnum lang){
        List<CategoryDTO> dtoList = new LinkedList<>();

        all.forEach(categoryEntity -> {
            CategoryDTO dto = new CategoryDTO();
            dto.setKey(categoryEntity.getKey());

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
            throw new AlreadyExist("this category already visible false");
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

    public PageImpl pagination(int page, int size, LangEnum lang) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<CategoryEntity> list = categoryRepository.findAll(pageable) ;

        List<CategoryEntity> all = list.getContent();

        List<CategoryDTO> dtoList = entityToDto(all, lang);

        return new PageImpl(dtoList,pageable, list.getTotalElements());
    }


    private void isValid(CategoryDTO dto) {
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

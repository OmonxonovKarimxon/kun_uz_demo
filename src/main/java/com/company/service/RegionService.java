package com.company.service;

import com.company.dto.CategoryDTO;
import com.company.dto.RegionDto;
import com.company.dto.article.TypesDTO;
import com.company.entity.CategoryEntity;
import com.company.entity.RegionEntity;
import com.company.enums.LangEnum;
import com.company.exps.AlreadyExist;
import com.company.exps.BadRequestException;
import com.company.exps.ItemNotFoundEseption;
import com.company.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public void create(RegionDto regionDto) {

        Optional<RegionEntity> region = regionRepository.findByKey(regionDto.getKey());

        if (region.isPresent()) {
            throw new AlreadyExist("Already exist");
        }

        isValid(regionDto);

        RegionEntity regionEntity = new RegionEntity();
        regionEntity.setKey(regionDto.getKey());
//        regionEntity.setNameUz(regionDto.getNameUz());
//        regionEntity.setNameRu(regionDto.getNameRu());
//        regionEntity.setNameEn(regionDto.getNameEn());

        regionRepository.save(regionEntity);
    }


    public RegionEntity get(Integer id) {
        return regionRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundEseption("Region not found");
        });
    }


    public void update(Integer id, RegionDto dto) {
        Optional<RegionEntity> regionEntity = regionRepository.findById(id);

        if (regionEntity.isEmpty()) {
            throw new ItemNotFoundEseption("not found region");
        }

        if (regionEntity.get().getVisible().equals(Boolean.FALSE)) {
            throw new BadRequestException("is visible false");
        }

        RegionEntity entity = regionEntity.get();

        entity.setKey(dto.getKey());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
       regionRepository.save(entity);
    }

    public void delete(Integer id) {

        Optional<RegionEntity> regionEntity = regionRepository.findById(id);

        if (regionEntity.isEmpty()) {
            throw new ItemNotFoundEseption("not found region");
        }

        if (regionEntity.get().getVisible().equals(Boolean.FALSE)) {
            throw new AlreadyExist("this region already visible false");
        }

        RegionEntity region = regionEntity.get();

        region.setVisible(Boolean.FALSE);

        regionRepository.save(region);
    }

    public List<RegionDto> getList(LangEnum lang) {

        Iterable<RegionEntity> all = regionRepository.findAllByVisible(true);
        return entityToDto(all, lang);
    }

    public List<RegionDto> getListOnlyForAdmin(LangEnum lang) {

        Iterable<RegionEntity> all = regionRepository.findAll();
        return entityToDto(all, lang);
    }

    public PageImpl pagination(int page, int size, LangEnum lang) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<RegionEntity> list = regionRepository.findAll(pageable) ;

        List<RegionEntity> all = list.getContent();

        List<RegionDto> dtoList = entityToDto(all, lang);

        return new PageImpl(dtoList,pageable, list.getTotalElements());
    }








    private List<RegionDto> entityToDto( Iterable<RegionEntity> all,LangEnum lang){
        List<RegionDto> dtoList = new LinkedList<>();

        all.forEach(regionEntity -> {
            RegionDto dto = new RegionDto();
            dto.setKey(regionEntity.getKey());
            dto.setId(regionEntity.getId());

            switch (lang) {
                case ru -> dto.setLang(regionEntity.getNameRu());
                case en -> dto.setLang(regionEntity.getNameEn());
                case uz -> dto.setLang(regionEntity.getNameUz());
            }
            dtoList.add(dto);
        });
        return  dtoList;
    }



    private void isValid(RegionDto dto) {
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

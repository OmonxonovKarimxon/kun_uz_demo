package com.company.service;

import com.company.dto.RegionDTO;
import com.company.entity.RegionEntity;
import com.company.enums.LangEnum;
import com.company.exps.NotPermissionException;
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

    public void create(RegionDTO regionDto) {

        Optional<RegionEntity> region = regionRepository.findByKey(regionDto.getKey());

        if (region.isPresent()) {
            throw new NotPermissionException("Already exist");
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
    public RegionDTO get(RegionEntity entity, LangEnum lang) {
        RegionDTO dto = new RegionDTO();
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

    public void update(Integer id, RegionDTO dto) {
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
            throw new NotPermissionException("this region already visible false");
        }

        RegionEntity region = regionEntity.get();

        region.setVisible(Boolean.FALSE);

        regionRepository.save(region);
    }

    public List<RegionDTO> getList(LangEnum lang) {

        Iterable<RegionEntity> all = regionRepository.findAllByVisible(true);
        return entityToDto(all, lang);
    }

    public List<RegionDTO> getListOnlyForAdmin(LangEnum lang) {

        Iterable<RegionEntity> all = regionRepository.findAllByVisible(true);
        return entityToDto(all, lang);
    }

    public PageImpl pagination(int page, int size, LangEnum lang) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<RegionEntity> list = regionRepository.findAll(pageable) ;

        List<RegionEntity> all = list.getContent();

        List<RegionDTO> dtoList = entityToDto(all, lang);

        return new PageImpl(dtoList,pageable, list.getTotalElements());
    }








    private List<RegionDTO> entityToDto(Iterable<RegionEntity> all, LangEnum lang){
        List<RegionDTO> dtoList = new LinkedList<>();

        all.forEach(regionEntity -> {
            RegionDTO dto = new RegionDTO();
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



    private void isValid(RegionDTO dto) {
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

package com.company.service;

import com.company.dto.RegionDto;
import com.company.entity.ProfileEntity;
import com.company.entity.RegionEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.exception.BadRequestException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RegioneService {
    @Autowired
    private RegionRepository regionRepository;


    public RegionDto create(RegionDto regionDto) {

        Optional<RegionEntity> optional = regionRepository.findByKey(regionDto.getKey());

        if (optional.isPresent()) {
            throw new BadRequestException("region already exists");
        }

        RegionEntity entity = new RegionEntity();
        entity.setCreatedDate(LocalDateTime.now());
        entity.setKey(regionDto.getKey());
        entity.setNameEng(regionDto.getNameENG());
        entity.setNameRu(regionDto.getNameRU());
        entity.setNameUz(regionDto.getNameUZ());
        entity.setVisible(true);

        regionRepository.save(entity);

        regionDto.setId(entity.getId());

        return regionDto;
    }


    public RegionDto update(RegionDto regionDto) {

        RegionEntity entity = isValid(regionDto);


        entity.setNameEng(regionDto.getNameENG());
        entity.setNameRu(regionDto.getNameRU());
        entity.setNameUz(regionDto.getNameUZ());

        regionRepository.save(entity);

        regionDto.setId(entity.getId());

        return regionDto;

    }


    public List<RegionDto> regionList() {
        List<RegionDto> list = new LinkedList<>();
        for (RegionEntity entity : regionRepository.findAll()) {
            RegionDto dto = new RegionDto();
            dto.setId(entity.getId());
            dto.setKey(entity.getKey());
            dto.setNameENG(entity.getNameEng());
            dto.setNameRU(entity.getNameRu());
            dto.setNameUZ(entity.getNameUz());

            list.add(dto);
        }

        return list;
    }


    public String delete(RegionDto dto) {

        RegionEntity entity = isValid(dto);

        if (entity.getVisible()) {
            entity.setVisible(false);
        }


        regionRepository.save(entity);

        dto.setId(entity.getId());
        return "succesfully deleted";
    }


    public RegionEntity isValid(RegionDto dto) {
        Optional<RegionEntity> optional = regionRepository.findById(dto.getId());

        RegionEntity entity = optional.get();

        if (optional.isEmpty()) {

            throw new ItemNotFoundException("we have not this region");

        }
        return entity;
    }

}

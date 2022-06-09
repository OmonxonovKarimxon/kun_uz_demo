package com.company.service;

import com.company.dto.ProfileDto;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.exception.BadRequestException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;


    public ProfileDto create(ProfileDto profileDto) {

        Optional<ProfileEntity> optional = profileRepository.findByEmail(profileDto.getEmail());
        if (optional.isPresent()) {
            throw new BadRequestException("User already exists");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setVisible(true);
        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setRole(profileDto.getRole());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setName(profileDto.getName());
        entity.setSurname(profileDto.getSurname());
        entity.setEmail(profileDto.getEmail());
        entity.setPassword(profileDto.getPassword());
        profileRepository.save(entity);

        profileDto.setId(entity.getId());

        return profileDto;
    }


    public ProfileDto update(ProfileDto profileDto) {

        ProfileEntity entity = isValid(profileDto);

        entity.setName(profileDto.getName());
        entity.setSurname(profileDto.getSurname());
        entity.setPassword(profileDto.getPassword());
        profileRepository.save(entity);

        profileDto.setId(entity.getId());

        return profileDto;

    }


    public List<ProfileDto> profileList() {
        List<ProfileDto> list = new LinkedList<>();
        for (ProfileEntity entity : profileRepository.findAll()) {
            ProfileDto dto = new ProfileDto();
            dto.setId(entity.getId());
            dto.setEmail(entity.getEmail());
            dto.setName(entity.getName());
            dto.setRole(entity.getRole());
            dto.setStatus(entity.getStatus());
            dto.setSurname(entity.getSurname());
            dto.setPassword(entity.getPassword());
            list.add(dto);
        }

        return list;
    }


    public ProfileDto changeStatus(ProfileDto profileDto) {

        ProfileEntity entity = isValid(profileDto);

        if (entity.getStatus().equals(ProfileStatus.ACTIVE) && !entity.getRole().equals(ProfileRole.ADMIN)) {
            entity.setStatus(ProfileStatus.BLOCK);
        } else {
            entity.setStatus(ProfileStatus.ACTIVE);
        }
        profileRepository.save(entity);
        profileDto.setId(entity.getId());
        return profileDto;

    }



    public ProfileDto delete(ProfileDto profileDto) {

        ProfileEntity entity = isValid(profileDto);

        if (entity.getVisible() && !entity.getRole().equals(ProfileRole.ADMIN)) {
            entity.setVisible(false);
        }


        profileRepository.save(entity);

        profileDto.setId(entity.getId());

        return profileDto;

    }


    public ProfileEntity isValid(ProfileDto dto){
        Optional<ProfileEntity> optional = profileRepository.findById(dto.getId());

        ProfileEntity entity = optional.get();

        if (!(optional.isPresent() && entity.getVisible())) {
            throw new ItemNotFoundException("we have not this user");
        }
        return entity;
    }

}

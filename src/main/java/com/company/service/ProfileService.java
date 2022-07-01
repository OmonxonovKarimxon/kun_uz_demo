package com.company.service;

import com.company.dto.profile.ProfileCreateDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.dto.profile.ProfileFilterDTO;
import com.company.entity.AttachEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileStatus;
import com.company.exps.NotPermissionException;
import com.company.exps.AlreadyExistPhone;
import com.company.exps.BadRequestException;
import com.company.exps.ItemNotFoundEseption;
import com.company.repository.ProfileFilterRepository;
import com.company.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AttachService attachService;
    @Autowired
    private ProfileFilterRepository profileFilterRepository;


    public ProfileDTO create(ProfileDTO profileDto) {

        Optional<ProfileEntity> entity = profileRepository.findByEmail(profileDto.getEmail());
        if (entity.isPresent()) {
            throw new AlreadyExistPhone("Already exist phone");
        }



        ProfileEntity profile = new ProfileEntity();
        profile.setName(profileDto.getName());
        profile.setSurname(profileDto.getSurname());
        profile.setEmail(profileDto.getEmail());
        profile.setRole(profileDto.getRole());
        profile.setPassword(profileDto.getPassword());
        profile.setStatus(ProfileStatus.ACTIVE);

        profile.setPhoto(new AttachEntity(profileDto.getPhotoId()));
        profileRepository.save(profile);
        profile.setPassword(null);

        return  "successfully";
    }

    public void update(Integer pId, ProfileDTO dto) {

        Optional<ProfileEntity> profile = profileRepository.findById(pId);

        if (profile.isEmpty()) {
            throw new ItemNotFoundEseption("not found profile");
        }

        isValidUpdate(dto);

        ProfileEntity entity = profile.get();

        if (entity.getPhoto() == null && dto.getPhotoId() != null) {
            entity.setPhoto(new AttachEntity(dto.getPhotoId()));
        } else if (entity.getPhoto() != null && dto.getPhotoId() == null) {
            attachService.delete(entity.getPhoto().getId());
            entity.setPhoto(null);
        } else if (entity.getPhoto() != null && dto.getPhotoId() != null &&
                !entity.getPhoto().getId().equals(dto.getPhotoId())) {
            attachService.delete(entity.getPhoto().getId());
            entity.setPhoto(new AttachEntity(dto.getPhotoId()));
        }


        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        profileRepository.save(entity);

    }


    public void delete(Integer id) {
        Optional<ProfileEntity> profile = profileRepository.findById(id);
        if (profile.isEmpty()) {
            throw new ItemNotFoundEseption("not found profile");
        }
        if (!profile.get().getVisible()) {
            throw new NotPermissionException("IsVisible False edi");
        }

        profile.get().setVisible(Boolean.FALSE);
        profileRepository.save(profile.get());
    }

    private void isValidUpdate(ProfileDTO dto) {

        if (dto.getName() == null || dto.getName().length() < 3) {
            throw new BadRequestException("wrong name");
        }

        if (dto.getSurname() == null || dto.getSurname().length() < 4) {
            throw new BadRequestException("surname required.");
        }

        if (dto.getEmail() == null || dto.getEmail().length() < 3) {
            throw new BadRequestException("email required.");
        }


    }



    public ProfileEntity getProfile(Integer profileId) {
        Optional<ProfileEntity> profile = profileRepository.findById(profileId);
        if (profile.isEmpty()) {
            throw new ItemNotFoundEseption(" mazgi bizda yoq");

        }
        return profile.get();
    }

    public PageImpl pagination(Integer page, Integer size) {

        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ProfileEntity> list = profileRepository.findByVisible(true, pageable);
        List<ProfileDTO> dtoList = new ArrayList<>();
        List<ProfileEntity> all = list.getContent();

        for (ProfileEntity entity : all) {
            ProfileDTO dto = entityToDto(entity);
            dtoList.add(dto);
        }


        return new PageImpl(dtoList, pageable, list.getTotalElements());
    }

    public List<ProfileDTO> filter(ProfileFilterDTO profileFilterDTO) {
        List<ProfileEntity> profileEntityList = profileFilterRepository.filter(profileFilterDTO);
        List<ProfileDTO> dtoList = new LinkedList<>();
        for (ProfileEntity entity : profileEntityList) {
            ProfileDTO dto = entityToDto(entity);
            dtoList.add(dto);
        }
        return  dtoList;
    }


    private ProfileDTO entityToDto(ProfileEntity entity){

        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole());
        return  dto;
    }


}


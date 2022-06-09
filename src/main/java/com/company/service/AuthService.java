package com.company.service;

import com.company.dto.AuthDto;
import com.company.dto.ProfileDto;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.exception.BadRequestException;
import com.company.repository.ProfileRepository;
import com.company.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private ProfileRepository profileRepository;


    public ProfileDto login(AuthDto authDTO) {

        Optional<ProfileEntity> optional = profileRepository.findByEmailAndPassword(authDTO.getEmail(), authDTO.getPassword());
        if (optional.isEmpty()) {
            throw new BadRequestException("User not found");
        }

        ProfileEntity entity = optional.get();
        if (entity.getStatus().equals(ProfileStatus.BLOCK)) {
            throw new BadRequestException("No ruhsat");
        }
        ProfileDto dto = new ProfileDto();

        dto.setJwtToken(JwtUtil.encode(entity.getId(), entity.getRole()));

        return dto;
    }

    public String registration(ProfileDto profileDto) {

        Optional<ProfileEntity> optional = profileRepository.findByEmail(profileDto.getEmail());
        if (optional.isPresent()) {
            throw new BadRequestException("User already exists");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setVisible(true);
        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setRole(ProfileRole.USER);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setName(profileDto.getName());
        entity.setSurname(profileDto.getSurname());
        entity.setEmail(profileDto.getEmail());
        entity.setPassword(profileDto.getPassword());
        profileRepository.save(entity);
        return "success";
    }
}

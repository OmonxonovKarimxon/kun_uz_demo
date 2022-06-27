package com.company.service;

import com.company.dto.integration.SmsRequestDTO;
import com.company.dto.integration.SmsResponseDTO;
import com.company.entity.SmsEntity;
import com.company.repository.SmsRepository;
import com.company.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SmsService {

    @Value("${sms.url}")
    private String smsUrl;

    @Value("${sms.key}")
    private String key;

    @Autowired
    private SmsRepository smsRepository;

    @Autowired
    private RestTemplate restTemplate;


    public void sendRegistrationSms(String phone) {
        String code = RandomUtil.getRandomSmsCode();
        String message = "Kun.uz Test partali uchun\n registratsiya kodi: " + code;

        SmsResponseDTO responseDTO = send(phone, message);

        SmsEntity entity = new SmsEntity();
        entity.setPhone(phone);
        entity.setCode(code);
        entity.setStatus(responseDTO.getSuccess());

        smsRepository.save(entity);
    }

    public boolean verifySms(String phone, String code) {
        Optional<SmsEntity> optional = smsRepository.findTopByPhoneOrderByCreatedDateDesc(phone);
        if (optional.isEmpty()) {
            return false;
        }
        SmsEntity sms = optional.get();
        LocalDateTime validDate = sms.getCreatedDate().plusMinutes(1);

        if (sms.getCode().equals(code) && validDate.isAfter(LocalDateTime.now())) {
            return true;
        }
        return false;
    }

    private SmsResponseDTO send(String phone, String message) {
        SmsRequestDTO requestDTO = new SmsRequestDTO();
        requestDTO.setKey(key);
        requestDTO.setPhone(phone);
        requestDTO.setMessage(message);
        System.out.println("Sms Request: message " + message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SmsRequestDTO> entity = new HttpEntity<>(requestDTO, headers);


        SmsResponseDTO response = restTemplate.postForObject(smsUrl, entity, SmsResponseDTO.class);
        System.out.println("Sms Response  " + response);
        return response;
    }

    public Long getSmsCount(String phone) {
        return null;
    }
}

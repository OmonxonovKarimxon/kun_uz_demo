package com.company.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.client.RestTemplate;

import javax.mail.internet.MimeMessage;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Configuration
public class SecuredFilterConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public FilterRegistrationBean<JwtFilter> filterRegistrationBeanRegion() {
        FilterRegistrationBean<JwtFilter> bean = new FilterRegistrationBean<JwtFilter>();
        bean.setFilter(jwtFilter);

        bean.addUrlPatterns("/profile/adm/*");
        bean.addUrlPatterns("/types/adm/*");
        bean.addUrlPatterns("/region/adm/*");
        bean.addUrlPatterns("/category/adm/*");
        bean.addUrlPatterns("/article/adm/*");
        bean.addUrlPatterns("/article_like/*");
        bean.addUrlPatterns("/comment_like/*");
        bean.addUrlPatterns("/comment/adm/*");
        bean.addUrlPatterns("/email/adm/*");
        bean.addUrlPatterns("/save/*");
        return bean;
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return  new RestTemplate();
    }




}

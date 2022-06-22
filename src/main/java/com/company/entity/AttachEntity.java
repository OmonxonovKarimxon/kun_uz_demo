package com.company.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "attach")
public class AttachEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "origin_name",nullable = false)
    private String originName;

    @Column( nullable = false)
    private String extention;

    @Column(nullable = false)
    private long size;
//
//    @Column(nullable = false)
//    @JoinColumn(name = "article_id")
//    @ManyToOne(fetch = FetchType.LAZY)
//    private  ArticleEntity article;

    @Column( nullable = false)
    private String path;
    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

}

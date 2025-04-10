package com.lawfirm.lawfirmserver.lawyer.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "lawyer")
public class Lawyer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 律师姓名
     */
    private String name;

    /**
     * 专业领域（刑事/民事）
     */
    private String field;

    /**
     * 地域
     */
    private String region;

    /**
     * 执业年限
     */
    private Integer experienceYears;

    /**
     * 评分
     */
    private Double rating;

    /**
     * 简介
     */
    @Column(length = 1000)
    private String introduction;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    @PrePersist
    public void prePersist() {
        createTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updateTime = LocalDateTime.now();
    }
} 
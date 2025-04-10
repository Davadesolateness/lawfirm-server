package com.lawfirm.lawfirmserver.lawyer.dto;

import lombok.Data;

@Data
public class LawyerQueryDTO {

    /**
     * 专业领域（刑事/民事）
     */
    private String field;

    /**
     * 地域
     */
    private String region;

    /**
     * 最小执业年限
     */
    private Integer minExperienceYears;

    /**
     * 最大执业年限
     */
    private Integer maxExperienceYears;

    /**
     * 最小评分
     */
    private Double minRating;

    /**
     * 最大评分
     */
    private Double maxRating;

    /**
     * 页码，从1开始
     */
    private Integer pageNum = 1;

    /**
     * 每页大小
     */
    private Integer pageSize = 10;
} 
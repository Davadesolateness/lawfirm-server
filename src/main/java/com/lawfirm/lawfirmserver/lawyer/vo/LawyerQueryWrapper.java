package com.lawfirm.lawfirmserver.lawyer.vo;

import lombok.Data;

import java.util.List;

/**
 * 律师查询条件构造器
 * 用于构建律师查询的SQL条件
 */
@Data
public class LawyerQueryWrapper {
    // 关键词（用于模糊查询律师姓名和简介）
    private String keyword;

    // 专业领域
    private String specialty;

    // 排除的专业领域列表
    private List<String> excludeSpecialties;

    // 省份代码
    private String provinceCode;

    // 城市代码
    private String cityCode;

    // 区域代码
    private String districtCode;

    // 是否推荐
    private Boolean recommend;

    // 最小从业年限
    private Integer experienceMin;

    // 最大从业年限
    private Integer experienceMax;

    // 排序条件
    private String orderBy = "id DESC";

    /**
     * 设置关键词模糊匹配条件
     */
    public LawyerQueryWrapper keywordLike(String keyword) {
        this.keyword = keyword;
        return this;
    }

    /**
     * 设置专业领域匹配条件
     */
    public LawyerQueryWrapper specialtyEquals(String specialty) {
        this.specialty = specialty;
        return this;
    }

    /**
     * 设置排除的专业领域列表
     */
    public LawyerQueryWrapper excludeSpecialties(List<String> excludeSpecialties) {
        this.excludeSpecialties = excludeSpecialties;
        return this;
    }

    /**
     * 设置省份代码匹配条件
     */
    public LawyerQueryWrapper provinceCodeEquals(String provinceCode) {
        this.provinceCode = provinceCode;
        return this;
    }

    /**
     * 设置城市代码匹配条件
     */
    public LawyerQueryWrapper cityCodeEquals(String cityCode) {
        this.cityCode = cityCode;
        return this;
    }

    /**
     * 设置区域代码匹配条件
     */
    public LawyerQueryWrapper districtCodeEquals(String districtCode) {
        this.districtCode = districtCode;
        return this;
    }

    /**
     * 设置是否推荐匹配条件
     */
    public LawyerQueryWrapper recommendEquals(Boolean recommend) {
        this.recommend = recommend;
        return this;
    }

    /**
     * 设置从业年限范围条件
     */
    public LawyerQueryWrapper experienceRange(Integer min, Integer max) {
        this.experienceMin = min;
        this.experienceMax = max;
        return this;
    }

    /**
     * 设置排序条件
     */
    public LawyerQueryWrapper orderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }
} 
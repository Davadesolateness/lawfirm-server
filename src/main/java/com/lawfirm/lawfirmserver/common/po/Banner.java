package com.lawfirm.lawfirmserver.common.po;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 轮播图实体类
 * 对应数据库表：banner
 * 用于存储首页轮播图信息
 */
public class Banner implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 轮播图标题
     */
    private String title;

    /**
     * 轮播图图片URL
     */
    private String imageUrl;

    /**
     * 轮播图链接URL
     */
    private String linkUrl;

    /**
     * 排序序号（数字越小越靠前）
     */
    private Integer sortOrder;

    /**
     * 是否启用（1-启用，0-禁用）
     */
    private Integer isEnabled;

    /**
     * 轮播图描述
     */
    private String description;

    /**
     * 数据插入时间
     */
    private Timestamp insertTimeForHis;

    /**
     * 数据更新时间
     */
    private Timestamp operateTimeForHis;

    public Banner() {
    }

    public Banner(Long id, String title, String imageUrl, String linkUrl, Integer sortOrder, Integer isEnabled, String description) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.linkUrl = linkUrl;
        this.sortOrder = sortOrder;
        this.isEnabled = isEnabled;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getInsertTimeForHis() {
        return insertTimeForHis;
    }

    public void setInsertTimeForHis(Timestamp insertTimeForHis) {
        this.insertTimeForHis = insertTimeForHis;
    }

    public Timestamp getOperateTimeForHis() {
        return operateTimeForHis;
    }

    public void setOperateTimeForHis(Timestamp operateTimeForHis) {
        this.operateTimeForHis = operateTimeForHis;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                ", sortOrder=" + sortOrder +
                ", isEnabled=" + isEnabled +
                ", description='" + description + '\'' +
                ", insertTimeForHis=" + insertTimeForHis +
                ", operateTimeForHis=" + operateTimeForHis +
                '}';
    }
} 
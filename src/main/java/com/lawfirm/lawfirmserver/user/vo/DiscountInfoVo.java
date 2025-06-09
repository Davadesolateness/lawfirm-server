package com.lawfirm.lawfirmserver.user.vo;

import java.util.Date;

public class DiscountInfoVo {
    private Long userId;
    private String username;
    private String nickName;
    private String userType;
    private Integer remainingServiceCount;
    private Integer remainingServiceMinutes;
    private Integer serviceLevel;
    private Date serviceStartTime;
    private Date serviceExpireTime;
    private Date updateTime;
    private Integer maxEmployeeCount;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getRemainingServiceCount() {
        return remainingServiceCount;
    }

    public void setRemainingServiceCount(Integer remainingServiceCount) {
        this.remainingServiceCount = remainingServiceCount;
    }

    public Integer getRemainingServiceMinutes() {
        return remainingServiceMinutes;
    }

    public void setRemainingServiceMinutes(Integer remainingServiceMinutes) {
        this.remainingServiceMinutes = remainingServiceMinutes;
    }

    public Integer getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(Integer serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public Date getServiceStartTime() {
        return serviceStartTime;
    }

    public void setServiceStartTime(Date serviceStartTime) {
        this.serviceStartTime = serviceStartTime;
    }

    public Date getServiceExpireTime() {
        return serviceExpireTime;
    }

    public void setServiceExpireTime(Date serviceExpireTime) {
        this.serviceExpireTime = serviceExpireTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getMaxEmployeeCount() {
        return maxEmployeeCount;
    }

    public void setMaxEmployeeCount(Integer maxEmployeeCount) {
        this.maxEmployeeCount = maxEmployeeCount;
    }
} 
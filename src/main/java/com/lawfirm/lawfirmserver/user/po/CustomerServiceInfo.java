package com.lawfirm.lawfirmserver.user.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户服务信息实体类
 * 用于存储客户的服务包相关信息，如剩余服务次数、服务时长等
 */
@Data
public class CustomerServiceInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 用户ID，关联user表
     */
    private Long userId;
    
    /**
     * 客户ID，可能关联individual_client或corporate_client表
     */
    private Long clientId;
    
    /**
     * 客户类型：individual - 个人客户，corporate - 法人客户
     */
    private String clientType;
    
    /**
     * 剩余服务次数
     */
    private Integer remainingServiceCount;
    
    /**
     * 剩余服务时长（分钟）
     */
    private Integer remainingServiceMinutes;
    
    /**
     * 服务级别：1-基础，2-标准，3-高级，4-VIP/企业VIP
     */
    private Integer serviceLevel;
    
    /**
     * 企业客户专用：员工数量上限
     */
    private Integer maxEmployeeCount;
    
    /**
     * 服务包开始时间
     */
    private Date serviceStartTime;
    
    /**
     * 服务包到期时间
     */
    private Date serviceExpireTime;
    
    /**
     * 上次更新时间
     */
    private Date updateTime;
    
    /**
     * 是否有效：1-有效，0-无效
     */
    private String isValidFlag;
    
    /**
     * 创建时间
     */
    private Date createTime;
} 
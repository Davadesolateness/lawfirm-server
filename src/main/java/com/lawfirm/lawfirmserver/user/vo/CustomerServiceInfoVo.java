package com.lawfirm.lawfirmserver.user.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 通过ins-framework-mybatis工具自动生成，表customerserviceinfo的VO对象<br/>
 * 对应表名：customerserviceinfo
 */
@Data
public class CustomerServiceInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 对应字段：id,备注：主键ID
     */
    private Long id;
    /**
     * 对应字段：userId,备注：用户ID，关联user表
     */
    private Long userId;
    /**
     * 对应字段：clientId,备注：客户ID，可能关联individual_client或corporate_client表
     */
    private Long clientId;
    /**
     * 对应字段：clientType,备注：客户类型：individual - 个人客户，corporate - 法人客户
     */
    private String clientType;
    /**
     * 对应字段：remainingServiceCount,备注：剩余服务次数
     */
    private Integer remainingServiceCount;
    /**
     * 对应字段：remainingServiceMinutes,备注：剩余服务时长（分钟）
     */
    private Integer remainingServiceMinutes;
    /**
     * 对应字段：serviceLevel,备注：服务级别：1-基础，2-标准，3-高级，4-VIP/企业VIP
     */
    private Integer serviceLevel;
    /**
     * 对应字段：maxEmployeeCount,备注：企业客户专用：员工数量上限
     */
    private Integer maxEmployeeCount;
    /**
     * 对应字段：serviceStartTime,备注：服务包开始时间
     */
    private Date serviceStartTime;
    /**
     * 对应字段：serviceExpireTime,备注：服务包到期时间
     */
    private Date serviceExpireTime;
    /**
     * 对应字段：updateTime,备注：上次更新时间
     */
    private Date updateTime;
    /**
     * 对应字段：isValidFlag,备注：是否有效：1-有效，0-无效
     */
    private String isValidFlag;
    /**
     * 对应字段：createTime,备注：创建时间
     */
    private Date createTime;
    /**
     * 对应字段：insertTimeForHis,备注：插入时间，默认为当前时间
     */
    private Date insertTimeForHis;
    /**
     * 对应字段：operateTimeForHis,备注：操作时间，自动更新
     */
    private Date operateTimeForHis;

}

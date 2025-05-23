package com.lawfirm.lawfirmserver.login.po;

import lombok.Data;

import java.util.Date;

/**
 * 登录日志实体类
 */
@Data
public class LoginLog {
    private static final long serialVersionUID = 1L;
    /**
     * 对应字段：id,备注：主键ID
     */
    private Long id;
    /** 对应字段：userId,备注：用户ID */
    private Long userId;
    /** 对应字段：phone,备注：手机号 */
    private String phone;
    /** 对应字段：loginType,备注：登录类型：1-密码登录，2-验证码登录，3-微信登录 */
    private Integer loginType;
    /** 对应字段：status,备注：登录状态：0-失败，1-成功 */
    private Integer status;
    /** 对应字段：ip,备注：IP地址 */
    private String ip;
    /** 对应字段：device,备注：设备信息 */
    private String device;
    /** 对应字段：remark,备注：备注 */
    private String remark;
    /** 对应字段：createTime,备注：创建时间 */
    private Date createTime;
    /**
     * 对应字段：insertTimeForHis,备注：插入时间
     */
    private Date insertTimeForHis;
    /**
     * 对应字段：operateTimeForHis,备注：更新时间
     */
    private Date operateTimeForHis;

} 
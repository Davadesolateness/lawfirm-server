package com.lawfirm.lawfirmserver.login.po;

import lombok.Data;

import java.util.Date;

/**
 * 登录日志实体类
 */
@Data
public class LoginLog {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 登录类型：1-密码登录，2-验证码登录，3-微信登录
     */
    private Integer loginType;

    /**
     * 登录状态：0-失败，1-成功
     */
    private Integer status;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 设备信息
     */
    private String device;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;
} 
package com.lawfirm.lawfirmserver.login.po;

import lombok.Data;

import java.util.Date;

/**
 * 短信验证码实体类
 */
@Data
public class SmsCode {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 验证码
     */
    private String code;

    /**
     * 验证码类型：1-登录验证码，2-注册验证码，3-重置密码验证码
     */
    private Integer type;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 创建时间
     */
    private Date createTime;
} 
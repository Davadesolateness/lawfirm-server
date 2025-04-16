package com.lawfirm.lawfirmserver.login.po;

import lombok.Data;

import java.util.Date;

/**
 * @description: 短信验证码登录po
 * @author dongzhibo
 * @date 2025/4/15 21:04
 * @version 1.0
 */
@Data
public class SmsVerification {
    // ID
    private Long id;
    // 手机号码
    private String phone;
    // 验证码
    private String code;
    // 类型(1-登录,2-注册,3-修改密码)
    private Byte type;
    // 过期时间
    private Date expireTime;
    // 创建时间
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

package com.lawfirm.lawfirmserver.login.dto;

import lombok.Data;

/**
 * 微信登录结果
 */
@Data
public class WechatLoginResult {
    
    /**
     * 用户唯一标识
     */
    private String openid;
    
    /**
     * 会话密钥
     */
    private String sessionKey;
    
    /**
     * 用户在开放平台的唯一标识符
     */
    private String unionid;
    
    /**
     * 错误码
     */
    private Integer errcode;
    
    /**
     * 错误信息
     */
    private String errmsg;
} 
package com.lawfirm.lawfirmserver.login.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

/**
 * @description: 登录返回信息
 * @author dongzhibo
 * @date 2025/4/15 21:10
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("登录返回信息")
public class LoginVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("用户类型")
    private String userType;

    @ApiModelProperty("头像URL")
    private String avatarUrl;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("token过期时间")
    private Long tokenExpireTime;

    @ApiModelProperty("刷新token")
    private String refreshToken;

    @ApiModelProperty("刷新token过期时间")
    private Long refreshTokenExpireTime;

    @ApiModelProperty("律师职称")
    private String title;

    @ApiModelProperty("律师经验")
    private String experience;

    @ApiModelProperty("律师专长")
    private String specialty;
    
    /**
     * 简化构造方法，用于登录返回
     */
    public LoginVo(String token, Object user) {
        this.token = token;
        // 这里实际应该转换User对象，但为简化示例，仅设置token
    }
}

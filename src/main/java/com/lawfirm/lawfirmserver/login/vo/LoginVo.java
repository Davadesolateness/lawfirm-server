package com.lawfirm.lawfirmserver.login.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @projectName: lawfirm-server
 * @package: com.lawfirm.lawfirmserver.login.vo
 * @className: LoginVo
 * @author: Eric
 * @description: TODO
 * @date: 2025/4/13 17:57
 * @version: 1.0
 */
@Data
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
}

package com.lawfirm.lawfirmserver.login.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Token刷新返回信息
 */
@Data
@ApiModel("Token刷新返回信息")
public class TokenRefreshVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("新token")
    private String token;

    @ApiModelProperty("token过期时间")
    private Long tokenExpireTime;

    @ApiModelProperty("新刷新token")
    private String refreshToken;

    @ApiModelProperty("刷新token过期时间")
    private Long refreshTokenExpireTime;
} 
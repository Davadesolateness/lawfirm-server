package com.lawfirm.lawfirmserver.login.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 验证码返回信息
 */
@Data
@ApiModel("验证码返回信息")
public class VerificationCodeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("验证码")
    private String code;

    @ApiModelProperty("验证码过期时间")
    private Long expireTime;
} 
package com.lawfirm.lawfirmserver.login.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 手机号验证返回信息
 */
@Data
@ApiModel("手机号验证返回信息")
public class PhoneVerifyVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("是否已注册")
    private boolean isRegistered;

    @ApiModelProperty("验证状态")
    private String status;

    @ApiModelProperty("验证时间")
    private Long verifyTime;
} 
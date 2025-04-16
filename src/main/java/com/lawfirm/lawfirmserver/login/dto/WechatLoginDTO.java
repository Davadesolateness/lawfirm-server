package com.lawfirm.lawfirmserver.login.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 微信登录DTO
 */
@Data
@ApiModel("微信登录请求")
public class WechatLoginDTO {

    @NotBlank(message = "code不能为空")
    @ApiModelProperty(value = "微信临时授权码", required = true, example = "0319QrGa1R0Pmj0rp3Ga1YrsxW49QrGP")
    private String code;

    @ApiModelProperty(value = "用户信息签名", example = "signature123")
    private String signature;

    @ApiModelProperty(value = "加密数据", example = "encryptedData123")
    private String encryptedData;

    @ApiModelProperty(value = "加密算法初始向量", example = "iv123")
    private String iv;

    @ApiModelProperty(value = "IP地址", example = "127.0.0.1")
    private String ip;
} 
package com.lawfirm.lawfirmserver.login.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 用户注册DTO
 */
@Data
@ApiModel("用户注册请求")
public class RegisterDTO {

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    @ApiModelProperty(value = "手机号", required = true, example = "13800138000")
    private String phone;

    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码", required = true, example = "123456")
    private String code;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", required = true, example = "password123")
    private String password;

    @ApiModelProperty(value = "昵称", example = "张三")
    private String nickname;
} 
package com.lawfirm.lawfirmserver.login.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 修改密码DTO
 */
@Data
@ApiModel(description = "修改密码请求参数")
public class ResetPasswordDTO {

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    @ApiModelProperty(value = "手机号", required = true, example = "13800138000")
    private String phone;

    @NotBlank(message = "验证码不能为空")
    @Pattern(regexp = "^\\d{6}$", message = "验证码必须是6位数字")
    @ApiModelProperty(value = "验证码", required = true, example = "123456")
    private String code;

    @NotBlank(message = "新密码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,20}$", message = "密码必须是6-20位字母或数字")
    @ApiModelProperty(value = "新密码", required = true, example = "123456")
    private String newPassword;
} 
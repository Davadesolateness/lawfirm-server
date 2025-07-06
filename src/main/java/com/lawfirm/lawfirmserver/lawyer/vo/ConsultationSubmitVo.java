package com.lawfirm.lawfirmserver.lawyer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @projectName: lawfirm-server
 * @package: com.lawfirm.lawfirmserver.lawyer.vo
 * @className: ConsultationSubmitVo
 * @author: Eric
 * @description: 用户提交咨询请求VO
 * @date: 2025/6/22 15:39
 * @version: 1.0
 */
@Data
@ApiModel("用户提交咨询请求")
public class ConsultationSubmitVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID", required = true, example = "1")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @ApiModelProperty(value = "咨询内容", required = true, example = "我需要咨询关于合同纠纷的问题...")
    @NotBlank(message = "咨询内容不能为空")
    private String consultContent;

    @ApiModelProperty(value = "问题类型", required = true, example = "1")
    @NotNull(message = "问题类型不能为空")
    private Long questionType;

    @ApiModelProperty(value = "是否同意协议", required = true, example = "1", notes = "1：已同意；0：未同意")
    @NotBlank(message = "必须同意咨询协议")
    private String agreeProtocol;
} 
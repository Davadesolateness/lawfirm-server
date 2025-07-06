package com.lawfirm.lawfirmserver.lawyer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @projectName: lawfirm-server
 * @package: com.lawfirm.lawfirmserver.lawyer.vo
 * @className: ConsultationAcceptVo
 * @author: Eric
 * @description: 律师接收咨询请求VO
 * @date: 2025/6/22 15:39
 * @version: 1.0
 */
@Data
@ApiModel("律师接收咨询请求")
public class ConsultationAcceptVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "咨询ID", required = true, example = "1")
    @NotNull(message = "咨询ID不能为空")
    private Long consultationId;

    @ApiModelProperty(value = "律师ID", required = true, example = "1")
    @NotNull(message = "律师ID不能为空")
    private Long lawyerId;
} 
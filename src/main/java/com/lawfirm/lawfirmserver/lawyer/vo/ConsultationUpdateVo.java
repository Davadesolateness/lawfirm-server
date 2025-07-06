package com.lawfirm.lawfirmserver.lawyer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @projectName: lawfirm-server
 * @package: com.lawfirm.lawfirmserver.lawyer.vo
 * @className: ConsultationUpdateVo
 * @author: Eric
 * @description: 更新咨询信息请求VO
 * @date: 2025/6/22 15:39
 * @version: 1.0
 */
@Data
@ApiModel("更新咨询信息请求")
public class ConsultationUpdateVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "咨询ID", required = true, example = "1")
    @NotNull(message = "咨询ID不能为空")
    private Long id;

    @ApiModelProperty(value = "用户ID", required = false, example = "1")
    private Long userId;

    @ApiModelProperty(value = "咨询内容", required = false, example = "更新后的咨询内容...")
    private String consultContent;

    @ApiModelProperty(value = "问题类型", required = false, example = "1")
    private Long questionType;

    @ApiModelProperty(value = "律师ID", required = false, example = "100")
    private Long lawyerId;

    @ApiModelProperty(value = "律师回复内容", required = false, example = "律师的回复内容...")
    private String lawyerReply;

    @ApiModelProperty(value = "创建时间", required = false)
    private Date createTime;

    @ApiModelProperty(value = "回复时间", required = false)
    private Date replyTime;

    @ApiModelProperty(value = "是否同意协议", required = false, example = "1")
    private String agreeProtocol;

    @ApiModelProperty(value = "咨询状态", required = false, example = "1", notes = "0：待回复；1：已回复；2：已关闭；3：已取消")
    private Integer status;
} 
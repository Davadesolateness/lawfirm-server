package com.lawfirm.lawfirmserver.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 个人用户详情信息VO对象，包含User表和IndividualClient表的关联数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("个人用户详情信息")
public class IndividualDetailsVo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Long userId;
    
    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;
    
    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    private String nickName;
    
    /**
     * 用户邮箱
     */
    @ApiModelProperty("用户邮箱")
    private String email;
    
    /**
     * 用户电话号码
     */
    @ApiModelProperty("用户电话号码")
    private String phoneNumber;
    
    /**
     * 用户创建时间
     */
    @ApiModelProperty("用户创建时间")
    private Date createTime;
    
    /**
     * 个人客户ID
     */
    @ApiModelProperty("个人客户ID")
    private Long individualId;
    
    /**
     * 个人客户全名
     */
    @ApiModelProperty("个人客户全名")
    private String fullName;
    
    /**
     * 性别
     */
    @ApiModelProperty("性别")
    private String gender;
    
    /**
     * 出生日期
     */
    @ApiModelProperty("出生日期")
    private Date birthDate;
    
    /**
     * 是否有效标志
     */
    @ApiModelProperty("是否有效标志：1-有效，0-无效")
    private String isValidFlag;
} 
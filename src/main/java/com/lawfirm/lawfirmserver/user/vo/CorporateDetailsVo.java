package com.lawfirm.lawfirmserver.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 法人用户详情信息VO对象，包含User表和CorporateClient表的关联数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("法人用户详情信息")
public class CorporateDetailsVo implements Serializable {
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
     * 法人客户ID
     */
    @ApiModelProperty("法人客户ID")
    private Long corporateId;
    
    /**
     * 公司名称
     */
    @ApiModelProperty("公司名称")
    private String companyName;
    
    /**
     * 证件类型
     */
    @ApiModelProperty("证件类型")
    private String certificateType;
    
    /**
     * 证件号码
     */
    @ApiModelProperty("证件号码")
    private String certificateNumber;
    
    /**
     * 联系人姓名
     */
    @ApiModelProperty("联系人姓名")
    private String contactPerson;
    
    /**
     * 是否有效标志
     */
    @ApiModelProperty("是否有效标志：1-有效，0-无效")
    private String isValidFlag;
} 
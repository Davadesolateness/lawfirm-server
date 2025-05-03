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
 * @author dong
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

    // 客户服务信息相关字段

    /**
     * 剩余服务次数
     */
    @ApiModelProperty("剩余服务次数")
    private Integer remainingServiceCount;

    /**
     * 剩余服务时长（分钟）
     */
    @ApiModelProperty("剩余服务时长（分钟）")
    private Integer remainingServiceMinutes;

    /**
     * 服务信息更新时间
     */
    @ApiModelProperty("服务信息更新时间")
    private Date serviceInfoUpdateTime;

    /**
     * 服务级别
     */
    @ApiModelProperty("服务级别：1-基础，2-标准，3-高级，4-VIP")
    private Integer serviceLevel;

    /**
     * 服务包开始时间
     */
    @ApiModelProperty("服务包开始时间")
    private Date serviceStartTime;

    /**
     * 服务包到期时间
     */
    @ApiModelProperty("服务包到期时间")
    private Date serviceExpireTime;

    /**
     * 头像Base64编码数据
     */
    @ApiModelProperty("头像Base64编码数据")
    private String imageData;

    /**
     * 头像文件类型
     */
    @ApiModelProperty("头像文件类型")
    private String type;
} 
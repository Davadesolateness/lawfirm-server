package com.lawfirm.lawfirmserver.login.vo;

import com.lawfirm.lawfirmserver.lawyer.po.Lawyer;
import com.lawfirm.lawfirmserver.user.po.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author dongzhibo
 * @version 1.0
 * @description: 登录返回信息
 * @date 2025/4/15 21:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("登录返回信息")
public class LoginVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("用户类型")
    private String userType;

    @ApiModelProperty("头像URL")
    private String avatarUrl;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("token过期时间")
    private Long tokenExpireTime;

    @ApiModelProperty("刷新token")
    private String refreshToken;

    @ApiModelProperty("刷新token过期时间")
    private Long refreshTokenExpireTime;

    @ApiModelProperty("律师职称")
    private String title;

    @ApiModelProperty("律师经验")
    private String experience;

    @ApiModelProperty("律师专长")
    private String specialty;

    /**
     * 简化构造方法，用于登录返回
     */
    public LoginVo(String token, Object user) {
        this.token = token;

        if (user instanceof User) {
            User userObj = (User) user;
            this.userId = String.valueOf(userObj.getId());
            this.userName = userObj.getUsername();
            this.userType = userObj.getUserType();
            this.phone = userObj.getPhoneNumber();

            // 设置token过期时间（例如：2小时后过期）
            this.tokenExpireTime = System.currentTimeMillis() + 2 * 60 * 60 * 1000;

            // 设置刷新token和过期时间（例如：7天后过期）
            this.refreshToken = token + "_refresh";
            this.refreshTokenExpireTime = System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000;
        }
    }

    /**
     * 设置律师相关信息
     */
    public void setLawyerInfo(Lawyer lawyer) {
        if (lawyer != null) {
            // 设置律师职称，可以根据workYears来确定
            if (lawyer.getWorkYears() != null) {
                if (lawyer.getWorkYears() >= 15) {
                    this.title = "资深律师";
                } else if (lawyer.getWorkYears() >= 10) {
                    this.title = "高级律师";
                } else if (lawyer.getWorkYears() >= 5) {
                    this.title = "中级律师";
                } else {
                    this.title = "初级律师";
                }
            }

            // 设置律师经验，直接使用工作年限
            this.experience = lawyer.getWorkYears() != null ? lawyer.getWorkYears() + "年" : "";

            // 设置律师专长
            this.specialty = lawyer.getLawyerIntroduction();
        }
    }
}

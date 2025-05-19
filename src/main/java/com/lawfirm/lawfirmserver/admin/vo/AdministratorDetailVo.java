package com.lawfirm.lawfirmserver.admin.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 管理员详细信息视图对象
 *
 * @author dong
 */
@Data
public class AdministratorDetailVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 对应字段：id,备注：管理员唯一标识，自增主键
     */
    private Long id;
    /**
     * 对应字段：admin_role,备注：管理员角色
     */
    private String adminRole;
    /**
     * 对应字段：admin_level,备注：管理员级别，数字越小级别越高
     */
    private Integer adminLevel;
    /**
     * 对应字段：parent_admin_id,备注：上级管理员 ID，顶级管理员该字段为 NULL
     */
    private Long parentAdminId;
    /**
     * 对应字段：is_valid_flag,备注：管理员是否有效，1 表示有效，0 表示无效，默认为有效
     */
    private String isValidFlag;

    /**
     * 用户名
     */
    private String username;
    
    /**
     * 昵称
     */
    private String nickName;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 手机号
     */
    private String phoneNumber;
    
    /**
     * 用户类型
     */
    private String userType;
    
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 头像图片数据
     */
    private  byte[] imageData;

    /**
     * 头像图片类型，如 jpeg, /png
     */
    private String imageType;
    /**
     * 文件扩展名（不含点号），如jpg, png, pdf等
     */
    private String fileExtension;

    /**
     * 头像图片类型，如 image/jpeg, image/png
     */
    private String mimeType;

    /**
     * 关联的用户ID
     */
    private Long userId;
}
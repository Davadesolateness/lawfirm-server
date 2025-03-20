package com.lawfirm.user.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 通过ins-framework-mybatis工具自动生成，请勿手工修改。表administrators的PO对象<br/>
 * 对应表名：administrators
 */
@Data
public class Administrators implements Serializable {
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
     * 对应字段：is_valid_flag,备注：管理员是否有效，默认为有效
     */
    private Boolean isValidFlag;
    /**
     * 对应字段：insert_time,备注：管理员信息插入时间，默认为当前时间
     */
    private Date insertTime;
    /**
     * 对应字段：operate_time,备注：管理员信息操作时间，自动更新
     */
    private Date operateTime;

}

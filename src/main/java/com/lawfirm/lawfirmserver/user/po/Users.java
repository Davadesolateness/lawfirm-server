package com.lawfirm.lawfirmserver.user.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 通过ins-framework-mybatis工具自动生成，请勿手工修改。表user的PO对象<br/>
 * 对应表名：user
 */
@Data
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 对应字段：id,备注：用户唯一标识，自增主键
     */
    private Long id;
    /**
     * 对应字段：openId,备注：开放平台的唯一标识
     */
    private String openId;
    /**
     * 对应字段：username,备注：用户名，唯一且不能为空
     */
    private String username;
    /**
     * 对应字段：password,备注：用户密码，需加密存储
     */
    private String password;
    /**
     * 对应字段：passwordUpdateTime,备注：用户密码修改时间，初始为当前时间
     */
    private Date passwordUpdateTime;
    /**
     * 对应字段：nickName,备注：用户昵称
     */
    private String nickName;
    /**
     * 对应字段：email,备注：用户邮箱
     */
    private String email;
    /**
     * 对应字段：phoneNumber,备注：用户电话号码，唯一
     */
    private String phoneNumber;
    /**
     * 对应字段：userType,备注：用户类型，取值可为：corporate（法人客户）、individual（个人客户）、lawyer（律师）、admin（管理员）
     */
    private String userType;
    /**
     * 对应字段：sourceType,备注：数据来源类型，取值可为：database_insert（数据库插入）、manual_register（人工系统注册），默认是人工系统注册
     */
    private String sourceType;
    /**
     * 对应字段：relatedEntityId,备注：关联的实体 ID，根据 userType 确定关联的表：corporate 对应 CorporateClient 表，individual 对应 IndividualClient 表，lawyer 对应 Lawyer 表，admin 对应 Administrator 表
     */
    private Long relatedEntityId;
    /**
     * 对应字段：createTime,备注：用户创建时间，默认为当前时间
     */
    private Date createTime;
    /**
     * 对应字段：isValidFlag,备注：用户是否有效，1 表示有效，0 表示无效，默认为有效
     */
    private String isValidFlag;
    /**
     * 对应字段：insertTimeForHis,备注：用户信息插入时间，默认为当前时间
     */
    private Date insertTimeForHis;
    /**
     * 对应字段：operateTimeForHis,备注：用户信息操作时间，自动更新
     */
    private Date operateTimeForHis;

}

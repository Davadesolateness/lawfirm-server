package com.lawfirm.lawfirmserver.user.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 通过ins-framework-mybatis工具自动生成，表users的VO对象<br/>
 * 对应表名：users
 */
@Data
public class UsersVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 对应字段：id,备注：用户唯一标识，自增主键
     */
    private Long id;
    /**
     * 对应字段：username,备注：用户名，唯一且不能为空
     */
    private String username;
    /**
     * 对应字段：password,备注：用户密码，需加密存储
     */
    private String password;
    /**
     * 对应字段：password_update_time,备注：用户密码修改时间，初始为当前时间
     */
    private Date passwordUpdateTime;
    /**
     * 对应字段：email,备注：用户邮箱，唯一
     */
    private String email;
    /**
     * 对应字段：phone_number,备注：用户电话号码
     */
    private String phoneNumber;
    /**
     * 对应字段：user_type,备注：用户类型，取值可为：corporate（法人客户）、individual（个人客户）、lawyer（律师）、admin（管理员）
     */
    private String userType;
    /**
     * 对应字段：source_type,备注：数据来源类型，取值可为：database_insert（数据库插入）、manual_register（人工系统注册），默认是人工系统注册
     */
    private String sourceType;
    /**
     * 对应字段：related_entity_id,备注：关联的实体 ID，根据 user_type 确定关联的表：corporate 对应 corporate_clients 表，individual 对应 individual_clients 表，lawyer 对应 lawyers 表，admin 对应 administrators 表
     */
    private Long relatedEntityId;
    /**
     * 对应字段：is_valid_flag,备注：用户是否有效，默认为有效
     */
    private Boolean isValidFlag;
    /**
     * 对应字段：insert_time,备注：用户信息插入时间，默认为当前时间
     */
    private Date insertTime;
    /**
     * 对应字段：operate_time,备注：用户信息操作时间，自动更新
     */
    private Date operateTime;

}

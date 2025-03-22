package com.lawfirm.lawfirmserver.user.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 通过ins-framework-mybatis工具自动生成，表individual_client的VO对象<br/>
 * 对应表名：individual_client
 */
@Data
public class IndividualClientVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 对应字段：id,备注：个人客户唯一标识，自增主键
     */
    private Long id;
    /**
     * 对应字段：full_name,备注：个人客户全名，不能为空
     */
    private String fullName;
    /**
     * 对应字段：gender,备注：性别，取值可为：male（男）、female（女）、other（其他）
     */
    private String gender;
    /**
     * 对应字段：birth_date,备注：出生日期
     */
    private Date birthDate;
    /**
     * 对应字段：is_valid_flag,备注：个人客户是否有效，1 表示有效，0 表示无效，默认为有效
     */
    private String isValidFlag;
    /**
     * 对应字段：insert_time_for_his,备注：个人客户信息插入时间，默认为当前时间
     */
    private Date insertTimeForHis;
    /**
     * 对应字段：operate_time_for_his,备注：个人客户信息操作时间，自动更新
     */
    private Date operateTimeForHis;

}

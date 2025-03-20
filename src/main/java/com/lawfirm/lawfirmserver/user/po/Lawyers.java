package com.lawfirm.lawfirmserver.user.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 通过ins-framework-mybatis工具自动生成，请勿手工修改。表lawyers的PO对象<br/>
 * 对应表名：lawyers
 */
@Data
public class Lawyers implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 对应字段：id,备注：律师唯一标识，自增主键
     */
    private Long id;
    /**
     * 对应字段：law_firm,备注：所在律师事务所名称
     */
    private String lawFirm;
    /**
     * 对应字段：lawyer_license_number,备注：律师执业证号，唯一
     */
    private String lawyerLicenseNumber;
    /**
     * 对应字段：specialization,备注：专业领域
     */
    private String specialization;
    /**
     * 对应字段：is_valid_flag,备注：律师是否有效，1 表示有效，0 表示无效，默认为有效
     */
    private String isValidFlag;
    /**
     * 对应字段：insert_time,备注：律师信息插入时间，默认为当前时间
     */
    private Date insertTime;
    /**
     * 对应字段：operate_time,备注：律师信息操作时间，自动更新
     */
    private Date operateTime;

}

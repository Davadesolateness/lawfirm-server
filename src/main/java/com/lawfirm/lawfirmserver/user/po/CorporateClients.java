package com.lawfirm.lawfirmserver.user.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 通过ins-framework-mybatis工具自动生成，请勿手工修改。表corporate_clients的PO对象<br/>
 * 对应表名：corporate_clients
 */
@Data
public class CorporateClients implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 对应字段：id,备注：法人客户唯一标识，自增主键
     */
    private Long id;
    /**
     * 对应字段：company_name,备注：公司名称，不能为空
     */
    private String companyName;
    /**
     * 对应字段：certificate_type,备注：证件类型，如营业执照、组织机构代码证等
     */
    private String certificateType;
    /**
     * 对应字段：certificate_number,备注：证件号码，唯一
     */
    private String certificateNumber;
    /**
     * 对应字段：contact_person,备注：联系人姓名
     */
    private String contactPerson;
    /**
     * 对应字段：is_valid_flag,备注：法人客户是否有效，1 表示有效，0 表示无效，默认为有效
     */
    private String isValidFlag;
    /**
     * 对应字段：insert_time_for_his,备注：法人客户信息插入时间，默认为当前时间
     */
    private Date insertTimeForHis;
    /**
     * 对应字段：operate_time_for_his,备注：法人客户信息操作时间，自动更新
     */
    private Date operateTimeForHis;

}

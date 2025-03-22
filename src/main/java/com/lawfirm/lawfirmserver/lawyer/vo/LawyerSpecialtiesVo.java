package com.lawfirm.lawfirmserver.lawyer.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 通过ins-framework-mybatis工具自动生成，表lawyer_specialties的VO对象<br/>
 * 对应表名：lawyer_specialties
 */
@Data
public class LawyerSpecialtiesVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 对应字段：id,备注：专长唯一标识，自增主键
     */
    private Long id;
    /**
     * 对应字段：specialty_name,备注：专长名称，唯一
     */
    private String specialtyName;
    /**
     * 对应字段：description,备注：专长描述
     */
    private String description;
    /**
     * 对应字段：insert_time_for_his,备注：专长信息插入时间，默认为当前时间
     */
    private Date insertTimeForHis;
    /**
     * 对应字段：operate_time_for_his,备注：专长信息操作时间，自动更新
     */
    private Date operateTimeForHis;

}

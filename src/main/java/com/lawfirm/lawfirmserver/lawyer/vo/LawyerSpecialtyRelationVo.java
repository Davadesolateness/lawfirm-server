package com.lawfirm.lawfirmserver.lawyer.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 通过ins-framework-mybatis工具自动生成，表lawyer_specialty_relation的VO对象<br/>
 * 对应表名：lawyer_specialty_relation
 */
@Data
public class LawyerSpecialtyRelationVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 对应字段：id,备注：关联记录唯一标识，自增主键
     */
    private Long id;
    /**
     * 对应字段：lawyer_id,备注：关联的律师 ID
     */
    private Long lawyerId;
    /**
     * 对应字段：specialty_id,备注：关联的专长 ID
     */
    private Long specialtyId;
    /**
     * 对应字段：insert_time_for_his,备注：关联信息插入时间，默认为当前时间
     */
    private Date insertTimeForHis;
    /**
     * 对应字段：operate_time_for_his,备注：关联信息操作时间，自动更新
     */
    private Date operateTimeForHis;

    private LawyerSpecialtieVo lawyerSpecialtieVo;
}

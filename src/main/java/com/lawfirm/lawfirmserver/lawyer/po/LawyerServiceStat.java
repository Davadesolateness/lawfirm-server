package com.lawfirm.lawfirmserver.lawyer.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 通过ins-framework-mybatis工具自动生成，请勿手工修改。表lawyerservicestat的PO对象<br/>
 * 对应表名：lawyerservicestat
 */
@Data
public class LawyerServiceStat implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 对应字段：lawyerId,备注：律师 ID
     */
    private Long lawyerId;
    /**
     * 对应字段：totalServiceCount,备注：律师总的服务次数
     */
    private Long totalServiceCount;
    /**
     * 对应字段：totalServiceDuration,备注：律师总的服务时长，单位为分钟
     */
    private Long totalServiceDuration;
    /**
     * 对应字段：insertTimeForHis,备注：统计信息插入时间
     */
    private Date insertTimeForHis;
    /**
     * 对应字段：operateTimeForHis,备注：统计信息更新时间
     */
    private Date operateTimeForHis;

}

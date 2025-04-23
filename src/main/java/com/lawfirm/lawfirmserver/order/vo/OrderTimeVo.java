package com.lawfirm.lawfirmserver.order.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 通过ins-framework-mybatis工具自动生成，表ordertime的VO对象<br/>
 * 对应表名：ordertime
 */
@Data
public class OrderTimeVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 对应字段：orderTimeId,备注：订单时间记录唯一标识，自增主键
     */
    private Long orderTimeId;
    /**
     * 对应字段：orderId,备注：关联的订单 ID
     */
    private Long orderId;
    /**
     * 对应字段：timeNode,备注：时间节点类型，如服务开始时间、服务结束时间等
     */
    private String timeNode;
    /**
     * 对应字段：timeValue,备注：对应时间节点的值
     */
    private Date timeValue;
    /**
     * 对应字段：insertTimeForHis,备注：时间记录插入时间
     */
    private Date insertTimeForHis;
    /**
     * 对应字段：operateTimeForHis,备注：时间记录更新时间
     */
    private Date operateTimeForHis;

}

package com.lawfirm.lawfirmserver.order.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单时间PO对象
 * 对应表名：order_times
 */
@Data
public class OrderTime implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * 对应字段：order_time_id,备注：订单时间记录唯一标识，自增主键
     */
    private Long orderTimeId;
    
    /**
     * 对应字段：order_id,备注：关联的订单 ID
     */
    private Long orderId;
    
    /**
     * 对应字段：time_node,备注：时间节点类型，如服务开始时间、服务结束时间等
     */
    private String timeNode;
    
    /**
     * 对应字段：time_value,备注：对应时间节点的值
     */
    private Date timeValue;
    
    /**
     * 对应字段：insert_time_for_his,备注：时间记录插入时间
     */
    private Date insertTimeForHis;
    
    /**
     * 对应字段：operate_time_for_his,备注：时间记录更新时间
     */
    private Date operateTimeForHis;
} 
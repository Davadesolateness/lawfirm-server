package com.lawfirm.lawfirmserver.order.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单PO对象
 * 对应表名：orders
 */
@Data
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 对应字段：order_id,备注：订单唯一标识，自增主键
     */
    private Long orderId;

    /**
     * 对应字段：user_id,备注：下单的用户 ID
     */
    private Long userId;

    /**
     * 对应字段：user_type,备注：用户类型，如 customer（客户）、other（其他）等
     */
    private String userType;

    /**
     * 对应字段：order_type,备注：订单类型，如 lawyer_service（律师服务订单）、buy_membership（购买会员订单）、welfare_distribution（福利发放订单）等
     */
    private String orderType;

    /**
     * 对应字段：service_duration,备注：服务时长，单位为分钟
     */
    private Long serviceDuration;

    /**
     * 对应字段：service_count,备注：服务次数，包含福利发放服务次数
     */
    private Long serviceCount;

    /**
     * 对应字段：purchase_amount,备注：购买金额，用于购买会员订单
     */
    private BigDecimal purchaseAmount;

    /**
     * 对应字段：order_status,备注：订单状态，如 pending（待处理）、completed（已完成）、cancelled（已取消）等
     */
    private String orderStatus;

    /**
     * 对应字段：input_time,备注：订单录入时间
     */
    private Date inputTime;

    /**
     * 对应字段：insert_time_for_his,备注：订单记录插入时间
     */
    private Date insertTimeForHis;

    /**
     * 对应字段：operate_time_for_his,备注：订单记录更新时间
     */
    private Date operateTimeForHis;
} 
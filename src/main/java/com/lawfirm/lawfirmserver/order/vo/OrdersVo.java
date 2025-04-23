package com.lawfirm.lawfirmserver.order.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 通过ins-framework-mybatis工具自动生成，表orders的VO对象<br/>
 * 对应表名：orders
 */
@Data
public class OrdersVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 对应字段：orderId,备注：订单唯一标识，自增主键
     */
    private Long orderId;
    /**
     * 对应字段：userId,备注：下单的用户 ID
     */
    private Long userId;
    /**
     * 对应字段：userType,备注：用户类型，如 customer（客户）、other（其他）等
     */
    private String userType;
    /**
     * 对应字段：orderType,备注：订单类型，如 lawyer_service（律师服务订单）、buy_membership（购买会员订单）、welfare_distribution（福利发放订单）等
     */
    private String orderType;
    /**
     * 对应字段：serviceDuration,备注：服务时长，单位为分钟
     */
    private Long serviceDuration;
    /**
     * 对应字段：serviceCount,备注：服务次数，包含福利发放服务次数
     */
    private Long serviceCount;
    /**
     * 对应字段：lawyerId,备注：律师 ID，用于律师服务订单下单用户与律师关联
     */
    private Long lawyerId;
    /**
     * 对应字段：amount,备注：购买金额，用于购买会员订单
     */
    private BigDecimal amount;
    /**
     * 对应字段：orderStatus,备注：订单状态，如 pending（待处理）、completed（已完成）、cancelled（已取消）等
     */
    private String orderStatus;
    /**
     * 对应字段：inputTime,备注：订单录入时间
     */
    private Date inputTime;
    /**
     * 对应字段：insertTimeForHis,备注：订单记录插入时间
     */
    private Date insertTimeForHis;
    /**
     * 对应字段：operateTimeForHis,备注：订单记录更新时间
     */
    private Date operateTimeForHis;
    /**
     * 订单时间信息
     */
    private List<OrderTimeVo> orderTimes;
}

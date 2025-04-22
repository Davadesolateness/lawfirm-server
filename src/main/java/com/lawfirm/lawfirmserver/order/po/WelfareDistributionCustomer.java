package com.lawfirm.lawfirmserver.order.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 福利发放客户PO对象
 * 对应表名：welfare_distribution_customers
 */
@Data
public class WelfareDistributionCustomer implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 对应字段：distribution_id,备注：福利发放记录唯一标识，自增主键
     */
    private Long distributionId;

    /**
     * 对应字段：order_id,备注：关联的福利发放订单 ID
     */
    private Long orderId;

    /**
     * 对应字段：user_id,备注：获得福利的用户 ID
     */
    private Long userId;

    /**
     * 对应字段：insert_time_for_his,备注：记录插入时间
     */
    private Date insertTimeForHis;

    /**
     * 对应字段：operate_time_for_his,备注：记录更新时间
     */
    private Date operateTimeForHis;
} 
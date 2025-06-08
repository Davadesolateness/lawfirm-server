package com.lawfirm.lawfirmserver.order.consts;

/**
 * @projectName: lawfirm-server
 * @package: com.lawfirm.lawfirmserver.order.consts
 * @className: OrderContant
 * @author: Eric
 * @description: 订单相关常量
 * @date: 2025/6/8 14:47
 * @version: 1.0
 */
public class OrderContant {

    /**
     * 订单状态常量
     */
    public static class OrderStatus {
        public static final String PENDING = "pending";
        public static final String COMPLETED = "completed";
        public static final String CANCELLED = "cancelled";
    }

    /**
     * 订单类型常量
     */
    public static class OrderType {
        public static final String LAWYER_SERVICE = "lawyer_service";
        public static final String BUY_MEMBERSHIP = "buy_membership";
        public static final String WELFARE_DISTRIBUTION = "welfare_distribution";
        public static final String PHONE_SERVICE = "phone_service";
    }
}

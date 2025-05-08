package com.lawfirm.lawfirmserver.order.vo;

import com.lawfirm.lawfirmserver.order.po.Orders;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单详情数据传输对象
 * 包含用户信息、律师信息和订单数据
 */
@Data
public class OrderDetailVO {

    // 订单ID
    private Long orderId;

    // 用户信息
    private Long userId;
    private String userName;
    // individual - 个人用户，corporate - 法人用户   lawyer - 律师
    private String userType;

    // 律师信息
    private Long lawyerId;
    private String lawyerName;
    private String lawyerTitle;
    private String lawyerAvatar;
    private byte[] imageData;
    private String fileExtension;

    // 订单信息
    // 订单类型
    private String orderType;
    // 订单金额
    private BigDecimal purchaseAmount;
    // 服务时长（分钟）
    private Long serviceDuration;
    // 服务次数
    private Long serviceCount;
    // 订单状态
    private String orderStatus;
    // 订单录入时间
    private Date inputTime;
    // 订单记录插入时间
    private Date insertTimeForHis;
    // 订单记录更新时间
    private Date operateTimeForHis;

    // 服务区域
    private String serviceArea;
    // 服务方式
    private String serviceMethod;

    /**
     * 从基础订单对象转换为详情对象
     *
     * @param order 基础订单
     * @return 详情对象
     */
    public static OrderDetailVO fromOrder(Orders order) {
        if (order == null) {
            return null;
        }

        OrderDetailVO vo = new OrderDetailVO();
        vo.setOrderId(order.getOrderId());
        vo.setUserId(order.getUserId());
        vo.setUserType(order.getUserType());
        vo.setOrderType(order.getOrderType());
        vo.setPurchaseAmount(order.getAmount());
        vo.setServiceDuration(order.getServiceDuration());
        vo.setServiceCount(order.getServiceCount());
        vo.setOrderStatus(order.getOrderStatus());
        vo.setInputTime(order.getInputTime());
        vo.setInsertTimeForHis(order.getInsertTimeForHis());
        vo.setOperateTimeForHis(order.getOperateTimeForHis());

        return vo;
    }
} 
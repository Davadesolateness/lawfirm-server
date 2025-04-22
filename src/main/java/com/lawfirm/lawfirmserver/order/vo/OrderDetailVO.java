package com.lawfirm.lawfirmserver.order.vo;

import com.lawfirm.lawfirmserver.order.po.Order;
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
    private String userType; // individual - 个人用户，corporate - 法人用户
    
    // 律师信息
    private Long lawyerId;
    private String lawyerName;
    private String lawyerTitle;
    private String lawyerAvatar;
    
    // 订单信息
    private String orderType; // 订单类型
    private BigDecimal purchaseAmount; // 订单金额
    private Long serviceDuration; // 服务时长（分钟）
    private Long serviceCount; // 服务次数
    private String orderStatus; // 订单状态
    private Date inputTime; // 订单录入时间
    private Date insertTimeForHis; // 订单记录插入时间
    private Date operateTimeForHis; // 订单记录更新时间
    
    // 其他扩展字段
    private String serviceArea; // 法律服务领域
    private String serviceMethod; // 服务方式（电话、在线、面谈等）
    
    /**
     * 从基础订单对象转换为详情对象
     * @param order 基础订单
     * @return 详情对象
     */
    public static OrderDetailVO fromOrder(Order order) {
        if (order == null) {
            return null;
        }
        
        OrderDetailVO vo = new OrderDetailVO();
        vo.setOrderId(order.getOrderId());
        vo.setUserId(order.getUserId());
        vo.setUserType(order.getUserType());
        vo.setOrderType(order.getOrderType());
        vo.setPurchaseAmount(order.getPurchaseAmount());
        vo.setServiceDuration(order.getServiceDuration());
        vo.setServiceCount(order.getServiceCount());
        vo.setOrderStatus(order.getOrderStatus());
        vo.setInputTime(order.getInputTime());
        vo.setInsertTimeForHis(order.getInsertTimeForHis());
        vo.setOperateTimeForHis(order.getOperateTimeForHis());
        
        return vo;
    }
} 
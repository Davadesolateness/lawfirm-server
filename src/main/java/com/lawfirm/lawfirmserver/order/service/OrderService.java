package com.lawfirm.lawfirmserver.order.service;

import com.lawfirm.lawfirmserver.order.dao.OrderDao;
import com.lawfirm.lawfirmserver.order.dao.OrderTimeDao;
import com.lawfirm.lawfirmserver.order.po.Order;
import com.lawfirm.lawfirmserver.order.po.OrderTime;
import com.lawfirm.lawfirmserver.order.vo.OrderTimeVo;
import com.lawfirm.lawfirmserver.order.vo.OrderVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 订单服务类
 * @author dongzhibo
 * @date 2025/4/21 21:31
 * @version 1.0
 */
@Service
public class OrderService {
    
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    
    @Autowired
    private OrderDao orderDao;
    
    @Autowired
    private OrderTimeDao orderTimeDao;
    
    /**
     * 根据用户ID获取订单列表
     *
     * @param userId 用户ID
     * @return 订单列表
     */
    public List<OrderVo> getOrdersByUserId(Long userId) {
        logger.info("根据用户ID获取订单列表, userId: {}", userId);
        
        // 查询用户的所有订单
        List<Order> orders = orderDao.selectByUserId(userId);
        if (orders == null || orders.isEmpty()) {
            return new ArrayList<>();
        }
        
        // 转换为VO对象
        List<OrderVo> orderVos = orders.stream().map(order -> {
            OrderVo orderVo = new OrderVo();
            BeanUtils.copyProperties(order, orderVo);
            
            // 查询订单关联的时间信息
            List<OrderTime> orderTimes = orderTimeDao.selectByOrderId(order.getOrderId());
            if (orderTimes != null && !orderTimes.isEmpty()) {
                List<OrderTimeVo> orderTimeVos = orderTimes.stream().map(orderTime -> {
                    OrderTimeVo orderTimeVo = new OrderTimeVo();
                    BeanUtils.copyProperties(orderTime, orderTimeVo);
                    return orderTimeVo;
                }).collect(Collectors.toList());
                orderVo.setOrderTimes(orderTimeVos);
            }
            
            return orderVo;
        }).collect(Collectors.toList());
        
        return orderVos;
    }
    
    /**
     * 根据订单ID获取订单详情
     *
     * @param orderId 订单ID
     * @return 订单详情
     */
    public OrderVo getOrderById(Long orderId) {
        logger.info("根据订单ID获取订单详情, orderId: {}", orderId);
        
        // 查询订单
        Order order = orderDao.selectByOrderId(orderId);
        if (order == null) {
            return null;
        }
        
        // 转换为VO对象
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order, orderVo);
        
        // 查询订单关联的时间信息
        List<OrderTime> orderTimes = orderTimeDao.selectByOrderId(orderId);
        if (orderTimes != null && !orderTimes.isEmpty()) {
            List<OrderTimeVo> orderTimeVos = orderTimes.stream().map(orderTime -> {
                OrderTimeVo orderTimeVo = new OrderTimeVo();
                BeanUtils.copyProperties(orderTime, orderTimeVo);
                return orderTimeVo;
            }).collect(Collectors.toList());
            orderVo.setOrderTimes(orderTimeVos);
        }
        
        return orderVo;
    }
}

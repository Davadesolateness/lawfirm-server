package com.lawfirm.lawfirmserver.order.service;

import com.lawfirm.lawfirmserver.order.dao.OrderTimeDao;
import com.lawfirm.lawfirmserver.order.dao.OrdersDao;
import com.lawfirm.lawfirmserver.order.po.OrderTime;
import com.lawfirm.lawfirmserver.order.vo.OrderDetailVO;
import com.lawfirm.lawfirmserver.order.po.Orders;
import com.lawfirm.lawfirmserver.order.vo.OrderTimeVo;
import com.lawfirm.lawfirmserver.order.vo.OrdersVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dongzhibo
 * @version 1.0
 * @description: 订单服务类
 * @date 2025/4/21 21:31
 */
@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrdersDao orderDao;

    @Autowired
    private OrderTimeDao orderTimeDao;

    /**
     * 根据用户ID获取订单列表
     *
     * @param userId 用户ID
     * @return 订单列表
     */
    public List<OrdersVo> getOrdersByUserId(String userId) {
        logger.info("根据用户ID获取订单列表, userId: {}", userId);

        // 查询用户的所有订单
        List<Orders> orders = orderDao.selectByUserId(Long.valueOf(userId));
        if (orders == null || orders.isEmpty()) {
            return new ArrayList<>();
        }

        // 转换为VO对象
        List<OrdersVo> orderVos = orders.stream().map(order -> {
            OrdersVo ordersVo = new OrdersVo();
            BeanUtils.copyProperties(order, ordersVo);

            // 查询订单关联的时间信息
            List<OrderTime> orderTimes = orderTimeDao.selectByOrderId(order.getOrderId());
            if (orderTimes != null && !orderTimes.isEmpty()) {
                List<OrderTimeVo> orderTimeVos = orderTimes.stream().map(orderTime -> {
                    OrderTimeVo orderTimeVo = new OrderTimeVo();
                    BeanUtils.copyProperties(orderTime, orderTimeVo);
                    return orderTimeVo;
                }).collect(Collectors.toList());
                ordersVo.setOrderTimes(orderTimeVos);
            }

            return ordersVo;
        }).collect(Collectors.toList());

        return orderVos;
    }

    /**
     * 根据订单ID获取订单详情
     *
     * @param orderId 订单ID
     * @return 订单详情
     */
    public OrdersVo getOrderById(Long orderId) {
        logger.info("根据订单ID获取订单详情, orderId: {}", orderId);

        // 查询订单
        Orders order = orderDao.selectByOrderId(orderId);
        if (order == null) {
            return null;
        }

        // 转换为VO对象
        OrdersVo orderVo = new OrdersVo();
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

    /**
     * 获取包含用户名和律师信息的订单详情
     *
     * @param orderId 订单ID
     * @return 详细的订单信息
     */
    public OrderDetailVO getOrderDetail(Long orderId) {
        logger.info("获取订单详情（包含用户和律师信息）, orderId: {}", orderId);

        // 直接调用Dao层的方法获取详细信息
        OrderDetailVO orderDetail = orderDao.getOrderDetail(orderId);
        if (orderDetail == null) {
            logger.warn("未找到订单详情, orderId: {}", orderId);
            return null;
        }

        logger.info("成功获取订单详情, orderId: {}, userName: {}, lawyerName: {}",
                    orderId, orderDetail.getUserName(), orderDetail.getLawyerName());
        return orderDetail;
    }

    /**
     * 获取用户的详细订单列表
     *
     * @param userId 用户ID
     * @return 详细的订单列表
     */
    public List<OrderDetailVO> getOrderDetailsByUserId(Long userId) {
        logger.info("获取用户的详细订单列表, userId: {}", userId);

        // 调用Dao层方法获取详细订单列表
        List<OrderDetailVO> orderDetails = orderDao.getOrderDetailsByUserId(userId);
        if (orderDetails == null || orderDetails.isEmpty()) {
            logger.info("用户没有订单记录, userId: {}", userId);
            return new ArrayList<>();
        }

        logger.info("成功获取用户的详细订单列表, userId: {}, 订单数量: {}", userId, orderDetails.size());
        return orderDetails;
    }
}

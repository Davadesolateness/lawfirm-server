package com.lawfirm.lawfirmserver.order.service;

import com.lawfirm.lawfirmserver.order.dao.OrderTimeDao;
import com.lawfirm.lawfirmserver.order.dao.OrdersDao;
import com.lawfirm.lawfirmserver.order.po.OrderTime;
import com.lawfirm.lawfirmserver.order.po.Orders;
import com.lawfirm.lawfirmserver.order.vo.OrderDetailVO;
import com.lawfirm.lawfirmserver.order.vo.OrderTimeVo;
import com.lawfirm.lawfirmserver.order.vo.OrdersVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    private OrdersDao ordersDao;

    @Autowired
    private OrderTimeDao orderTimeDao;

    /**
     * 根据用户ID获取订单列表，包含律师名称
     *
     * @param userId 用户ID
     * @return 包含律师名称的订单列表
     */
    public List<OrderDetailVO> getOrdersByUserId(String userId) {
        logger.info("根据用户ID获取订单概要信息（包含律师名称）, userId: {}", userId);

        // 直接调用Dao层方法获取包含律师名称的订单列表
        List<OrderDetailVO> orderDetails = ordersDao.getOrderDetailsByUserId(Long.valueOf(userId));
        if (orderDetails == null || orderDetails.isEmpty()) {
            logger.info("用户没有订单记录, userId: {}", userId);
            return new ArrayList<>();
        }

        logger.info("成功获取用户的订单概要信息, userId: {}, 订单数量: {}", userId, orderDetails.size());
        return orderDetails;
    }

    /**
     * 根据律师ID获取订单列表，包含用户名称
     *
     * @param lawyerId 律师ID
     * @return 包含用户名称的订单列表
     */
    public List<OrderDetailVO> getOrdersByLawyerId(String lawyerId) {
        logger.info("根据律师ID获取订单概要信息（包含用户名称）, lawyerId: {}", lawyerId);

        // 调用Dao层方法获取包含用户名称的订单列表
        List<OrderDetailVO> orderDetails = ordersDao.getOrderDetailsByLawyerId(Long.valueOf(lawyerId));
        if (orderDetails == null || orderDetails.isEmpty()) {
            logger.info("律师没有订单记录, lawyerId: {}", lawyerId);
            return new ArrayList<>();
        }

        logger.info("成功获取律师的订单概要信息, lawyerId: {}, 订单数量: {}", lawyerId, orderDetails.size());
        return orderDetails;
    }

    /**
     * 根据关键词（用户名或律师名）搜索订单
     *
     * @param keyword 搜索关键词
     * @return 匹配的订单列表
     */
    public List<OrderDetailVO> searchOrdersByKeyword(String keyword) {
        logger.info("根据关键词搜索订单, keyword: {}", keyword);
        
        if (!StringUtils.hasText(keyword)) {
            logger.info("搜索关键词为空，返回空列表");
            return new ArrayList<>();
        }
        
        // 调用DAO层方法进行搜索
        List<OrderDetailVO> orderDetails = ordersDao.searchOrdersByKeyword(keyword);
        
        if (orderDetails == null || orderDetails.isEmpty()) {
            logger.info("没有找到匹配关键词的订单, keyword: {}", keyword);
            return new ArrayList<>();
        }
        
        logger.info("成功搜索到匹配的订单, keyword: {}, 订单数量: {}", keyword, orderDetails.size());
        return orderDetails;
    }

    /**
     * 根据关键词（用户名或律师名）搜索订单，支持分页
     *
     * @param keyword 搜索关键词
     * @param pageNum 页码，从1开始
     * @param pageSize 每页记录数
     * @return 分页后的订单列表
     */
    public List<OrderDetailVO> searchOrdersByKeyword(String keyword, Integer pageNum, Integer pageSize) {
        logger.info("根据关键词搜索订单(分页), keyword: {}, pageNum: {}, pageSize: {}", keyword, pageNum, pageSize);
        
        if (!StringUtils.hasText(keyword)) {
            logger.info("搜索关键词为空，返回空列表");
            return new ArrayList<>();
        }
        
        // 计算分页参数
        int offset = (pageNum - 1) * pageSize;
        int limit = pageSize;
        
        // 调用DAO层方法进行分页搜索
        List<OrderDetailVO> orderDetails = ordersDao.searchOrdersByKeywordWithPagination(keyword, offset, limit);
        
        if (orderDetails == null || orderDetails.isEmpty()) {
            logger.info("没有找到匹配关键词的订单, keyword: {}", keyword);
            return new ArrayList<>();
        }
        
        logger.info("成功搜索到匹配的订单, keyword: {}, 订单数量: {}", keyword, orderDetails.size());
        return orderDetails;
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
        Orders order = ordersDao.selectByOrderId(orderId);
        if (order == null) {
            return null;
        }

        // 转换为VO对象
        OrdersVo ordersVo = new OrdersVo();
        BeanUtils.copyProperties(order, ordersVo);

        // 查询订单关联的时间信息
        List<OrderTime> orderTimes = orderTimeDao.selectByOrderId(orderId);
        if (orderTimes != null && !orderTimes.isEmpty()) {
            List<OrderTimeVo> orderTimeVos = orderTimes.stream().map(orderTime -> {
                OrderTimeVo orderTimeVo = new OrderTimeVo();
                BeanUtils.copyProperties(orderTime, orderTimeVo);
                return orderTimeVo;
            }).collect(Collectors.toList());
            ordersVo.setOrderTimes(orderTimeVos);
        }

        return ordersVo;
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
        OrderDetailVO orderDetail = ordersDao.getOrderDetail(orderId);
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
        List<OrderDetailVO> orderDetails = ordersDao.getOrderDetailsByUserId(userId);
        if (orderDetails == null || orderDetails.isEmpty()) {
            logger.info("用户没有订单记录, userId: {}", userId);
            return new ArrayList<>();
        }

        logger.info("成功获取用户的详细订单列表, userId: {}, 订单数量: {}", userId, orderDetails.size());
        return orderDetails;
    }

    /**
     * 根据用户ID获取订单列表，包含律师名称，支持分页
     *
     * @param userId 用户ID
     * @param pageNum 页码，从1开始
     * @param pageSize 每页记录数
     * @return 包含律师名称的订单列表
     */
    public List<OrderDetailVO> getOrdersByUserId(String userId, Integer pageNum, Integer pageSize) {
        logger.info("根据用户ID获取订单概要信息（分页）, userId: {}, pageNum: {}, pageSize: {}", userId, pageNum, pageSize);

        // 计算分页参数
        int offset = (pageNum - 1) * pageSize;
        int limit = pageSize;

        // 直接调用Dao层方法获取包含律师名称的订单列表
        List<OrderDetailVO> orderDetails = ordersDao.getOrderDetailsByUserIdWithPagination(Long.valueOf(userId), offset, limit);
        if (orderDetails == null || orderDetails.isEmpty()) {
            logger.info("用户没有订单记录, userId: {}", userId);
            return new ArrayList<>();
        }

        logger.info("成功获取用户的订单概要信息, userId: {}, 订单数量: {}", userId, orderDetails.size());
        return orderDetails;
    }

    /**
     * 根据律师ID获取订单列表，包含用户名称，支持分页
     *
     * @param lawyerId 律师ID
     * @param pageNum 页码，从1开始
     * @param pageSize 每页记录数
     * @return 包含用户名称的订单列表
     */
    public List<OrderDetailVO> getOrdersByLawyerId(String lawyerId, Integer pageNum, Integer pageSize) {
        logger.info("根据律师ID获取订单概要信息（分页）, lawyerId: {}, pageNum: {}, pageSize: {}", lawyerId, pageNum, pageSize);

        // 计算分页参数
        int offset = (pageNum - 1) * pageSize;
        int limit = pageSize;

        // 调用Dao层方法获取包含用户名称的订单列表
        List<OrderDetailVO> orderDetails = ordersDao.getOrderDetailsByLawyerIdWithPagination(Long.valueOf(lawyerId), offset, limit);
        if (orderDetails == null || orderDetails.isEmpty()) {
            logger.info("律师没有订单记录, lawyerId: {}", lawyerId);
            return new ArrayList<>();
        }

        logger.info("成功获取律师的订单概要信息, lawyerId: {}, 订单数量: {}", lawyerId, orderDetails.size());
        return orderDetails;
    }
}

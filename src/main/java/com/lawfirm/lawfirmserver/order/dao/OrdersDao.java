package com.lawfirm.lawfirmserver.order.dao;

import com.lawfirm.lawfirmserver.order.po.Orders;
import com.lawfirm.lawfirmserver.order.vo.OrderDetailVO;
import ins.framework.mybatis.MybatisBaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 表orders对应的基于MyBatis实现的Dao接口
 */
@Mapper
public interface OrdersDao extends MybatisBaseDao<Orders, Long> {

    /**
     * 根据用户ID查询订单列表
     *
     * @param userId 用户ID
     * @return 订单列表
     */
    List<Orders> selectByUserId(@Param("userId") Long userId);

    /**
     * 根据订单ID查询订单
     *
     * @param orderId 订单ID
     * @return 订单
     */
    Orders selectByOrderId(@Param("orderId") Long orderId);

    /**
     * 获取订单详情，包含用户名、律师信息和订单数据
     *
     * @param orderId 订单ID
     * @return 包含详细信息的订单详情对象
     */
    OrderDetailVO getOrderDetail(@Param("orderId") Long orderId);

    /**
     * 获取用户的订单详情列表
     *
     * @param userId 用户ID
     * @return 包含详细信息的订单详情列表
     */
    List<OrderDetailVO> getOrderDetailsByUserId(@Param("userId") Long userId);
}
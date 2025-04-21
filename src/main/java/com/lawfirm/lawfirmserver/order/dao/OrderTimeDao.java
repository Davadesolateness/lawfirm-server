package com.lawfirm.lawfirmserver.order.dao;

import com.lawfirm.lawfirmserver.order.po.OrderTime;
import ins.framework.mybatis.MybatisBaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 表order_times对应的基于MyBatis实现的Dao接口
 */
@Mapper
public interface OrderTimeDao extends MybatisBaseDao<OrderTime, Long> {
    
    /**
     * 根据订单ID查询订单时间列表
     * 
     * @param orderId 订单ID
     * @return 订单时间列表
     */
    List<OrderTime> selectByOrderId(@Param("orderId") Long orderId);
} 
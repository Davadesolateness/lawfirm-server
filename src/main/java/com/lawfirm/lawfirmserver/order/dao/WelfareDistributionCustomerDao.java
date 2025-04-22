package com.lawfirm.lawfirmserver.order.dao;

import com.lawfirm.lawfirmserver.order.po.WelfareDistributionCustomer;
import ins.framework.mybatis.MybatisBaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 表welfare_distribution_customers对应的基于MyBatis实现的Dao接口
 */
@Mapper
public interface WelfareDistributionCustomerDao extends MybatisBaseDao<WelfareDistributionCustomer, Long> {

    /**
     * 根据订单ID查询福利发放客户列表
     *
     * @param orderId 订单ID
     * @return 福利发放客户列表
     */
    List<WelfareDistributionCustomer> selectByOrderId(@Param("orderId") Long orderId);

    /**
     * 根据用户ID查询福利发放客户列表
     *
     * @param userId 用户ID
     * @return 福利发放客户列表
     */
    List<WelfareDistributionCustomer> selectByUserId(@Param("userId") Long userId);
} 
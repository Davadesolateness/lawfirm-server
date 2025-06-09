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

    /**
     * 获取律师的订单详情列表
     *
     * @param lawyerId 律师ID
     * @return 包含详细信息的订单详情列表
     */
    List<OrderDetailVO> getOrderDetailsByLawyerId(@Param("lawyerId") Long lawyerId);

    /**
     * 根据关键词搜索订单
     *
     * @param keyword 搜索关键词（用户名或律师名）
     * @return 包含详细信息的订单详情列表
     */
    List<OrderDetailVO> searchOrdersByKeyword(@Param("keyword") String keyword);

    /**
     * 根据关键词搜索订单，支持分页
     *
     * @param keyword 搜索关键词（用户名或律师名）
     * @param offset  起始位置
     * @param limit   查询条数
     * @return 分页后的订单详情列表
     */
    List<OrderDetailVO> searchOrdersByKeywordWithPagination(
            @Param("keyword") String keyword,
            @Param("offset") int offset,
            @Param("limit") int limit);

    /**
     * 获取用户的订单详情列表，支持分页
     *
     * @param userId 用户ID
     * @param offset 起始位置
     * @param limit  查询条数
     * @return 包含详细信息的订单详情列表
     */
    List<OrderDetailVO> getOrderDetailsByUserIdWithPagination(
            @Param("userId") Long userId,
            @Param("offset") int offset,
            @Param("limit") int limit);

    /**
     * 获取律师的订单详情列表，支持分页
     *
     * @param lawyerId 律师ID
     * @param offset   起始位置
     * @param limit    查询条数
     * @return 包含详细信息的订单详情列表
     */
    List<OrderDetailVO> getOrderDetailsByLawyerIdWithPagination(
            @Param("lawyerId") Long lawyerId,
            @Param("offset") int offset,
            @Param("limit") int limit);

    /**
     * 插入一条记录(为空的字段不操作)，并返回生成的主键
     * @param order 订单对象
     * @return 插入的订单ID
     */
    Long insertSelectiveAndBackId(Orders order);
}
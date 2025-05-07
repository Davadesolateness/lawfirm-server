package com.lawfirm.lawfirmserver.order.api;

import com.lawfirm.lawfirmserver.common.Result;
import com.lawfirm.lawfirmserver.order.service.OrderService;
import com.lawfirm.lawfirmserver.order.vo.OrderDetailVO;
import com.lawfirm.lawfirmserver.order.vo.OrdersVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author dongzhibo
 * @version 1.0
 * @description: 订单接口控制器
 * @date 2025/4/21 21:40
 */
@RestController
@RequestMapping("/order")
@Api(tags = "订单管理", description = "订单相关接口")
public class OrderApi {

    @Autowired
    private OrderService orderService;

    /**
     * 获取用户的所有订单，包含律师名称等概要信息，支持分页
     *
     * @param userId   用户ID
     * @param pageNum  页码，从1开始
     * @param pageSize 每页记录数
     * @return 包含律师名称的订单列表
     */
    @GetMapping("/getUserOrders")
    @ApiOperation("获取用户的所有订单，包含律师名称")
    public Result<List<OrderDetailVO>> getUserOrders(
            @ApiParam(value = "用户ID", required = true, example = "1")
            @RequestParam("userId") String userId,
            @ApiParam(value = "页码，从1开始", required = false, example = "1")
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页记录数", required = false, example = "10")
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        List<OrderDetailVO> orders = orderService.getOrdersByUserId(userId, pageNum, pageSize);
        return Result.success("获取用户订单成功", orders);
    }

    /**
     * 获取律师的所有订单，包含用户名称等概要信息，支持分页
     *
     * @param lawyerId 律师ID
     * @param pageNum  页码，从1开始
     * @param pageSize 每页记录数
     * @return 包含用户名称的订单列表
     */
    @GetMapping("/getLawyerOrders")
    @ApiOperation("获取律师的所有订单，包含用户名称")
    public Result<List<OrderDetailVO>> getLawyerOrders(
            @ApiParam(value = "律师ID", required = true, example = "1")
            @RequestParam("lawyerId") String lawyerId,
            @ApiParam(value = "页码，从1开始", required = false, example = "1")
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页记录数", required = false, example = "10")
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        List<OrderDetailVO> orders = orderService.getOrdersByLawyerId(lawyerId, pageNum, pageSize);
        return Result.success("获取律师订单成功", orders);
    }

    /**
     * 搜索订单接口，根据关键词查询匹配用户名或律师名的订单，支持分页
     *
     * @param keyword  搜索关键词（用户名或律师名）
     * @param pageNum  页码，从1开始
     * @param pageSize 每页记录数
     * @return 分页后的订单列表
     */
    @GetMapping("/searchOrders")
    @ApiOperation("根据关键词（用户名或律师名）搜索订单，支持分页")
    public Result<List<OrderDetailVO>> searchOrders(
            @ApiParam(value = "搜索关键词", required = true, example = "张三")
            @RequestParam("keyword") String keyword,
            @ApiParam(value = "页码，从1开始", required = false, example = "1")
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页记录数", required = false, example = "10")
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        List<OrderDetailVO> orders = orderService.searchOrdersByKeyword(keyword, pageNum, pageSize);
        return Result.success("搜索订单成功", orders);
    }

    /**
     * 获取订单详情
     *
     * @param orderId 订单ID
     * @return 订单详情
     */
    @GetMapping("/getOrderDetail")
    @ApiOperation("获取订单详情")
    public Result<OrdersVo> getOrderDetail(
            @ApiParam(value = "订单ID", required = true, example = "1")
            @RequestParam("orderId") Long orderId) {
        OrdersVo order = orderService.getOrderById(orderId);
        if (order != null) {
            return Result.success("获取订单详情成功", order);
        } else {
            return Result.fail("订单不存在");
        }
    }

    /**
     * 获取包含用户名和律师信息的完整订单详情
     *
     * @param orderId 订单ID
     * @return 包含详细信息的订单详情
     */
    @GetMapping("/getCompleteOrderDetail")
    @ApiOperation("获取包含用户和律师信息的完整订单详情")
    public Result<OrderDetailVO> getCompleteOrderDetail(
            @ApiParam(value = "订单ID", required = true, example = "1")
            @RequestParam("orderId") Long orderId) {
        OrderDetailVO orderDetail = orderService.getOrderDetail(orderId);
        if (orderDetail != null) {
            return Result.success("获取完整订单详情成功", orderDetail);
        } else {
            return Result.fail("订单不存在或无法获取完整详情");
        }
    }

    /**
     * 获取用户的所有详细订单
     *
     * @param userId 用户ID
     * @return 包含详细信息的订单列表
     */
    @GetMapping("/getUserOrderDetails")
    @ApiOperation("获取用户的所有详细订单")
    public Result<List<OrderDetailVO>> getUserOrderDetails(
            @ApiParam(value = "用户ID", required = true, example = "1")
            @RequestParam("userId") Long userId) {
        List<OrderDetailVO> orderDetails = orderService.getOrderDetailsByUserId(userId);
        return Result.success("获取用户详细订单成功", orderDetails);
    }
}
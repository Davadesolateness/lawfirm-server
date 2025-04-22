package com.lawfirm.lawfirmserver.order.api;

import com.lawfirm.lawfirmserver.common.Result;
import com.lawfirm.lawfirmserver.order.service.OrderService;
import com.lawfirm.lawfirmserver.order.vo.OrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 订单接口控制器
 * @author dongzhibo
 * @date 2025/4/21 21:40
 * @version 1.0
 */
@RestController
@RequestMapping("/order")
@Api(tags = "订单管理", description = "订单相关接口")
public class OrderApi {

    @Autowired
    private OrderService orderService;

    /**
     * 获取用户的所有订单
     * 
     * @param userId 用户ID
     * @return 订单列表
     */
    @GetMapping("/getUserOrders")
    @ApiOperation("获取用户的所有订单")
    public Result<List<OrderVo>> getUserOrders(
            @ApiParam(value = "用户ID", required = true, example = "1") 
            @RequestParam("userId") String userId) {
        List<OrderVo> orders = orderService.getOrdersByUserId(userId);
        return Result.success("获取用户订单成功", orders);
    }

    /**
     * 获取订单详情
     * 
     * @param orderId 订单ID
     * @return 订单详情
     */
    @GetMapping("/getOrderDetail")
    @ApiOperation("获取订单详情")
    public Result<OrderVo> getOrderDetail(
            @ApiParam(value = "订单ID", required = true, example = "1") 
            @RequestParam("orderId") Long orderId) {
        OrderVo order = orderService.getOrderById(orderId);
        if (order != null) {
            return Result.success("获取订单详情成功", order);
        } else {
            return Result.fail("订单不存在");
        }
    }
} 
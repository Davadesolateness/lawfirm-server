package com.lawfirm.lawfirmserver.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单视图对象
 */
@Data
@ApiModel("订单信息")
public class OrderVo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty("订单ID")
    private Long orderId;
    
    @ApiModelProperty("用户ID")
    private Long userId;
    
    @ApiModelProperty("用户类型")
    private String userType;
    
    @ApiModelProperty("订单类型")
    private String orderType;
    
    @ApiModelProperty("服务时长(分钟)")
    private Long serviceDuration;
    
    @ApiModelProperty("服务次数")
    private Long serviceCount;
    
    @ApiModelProperty("购买金额")
    private BigDecimal purchaseAmount;
    
    @ApiModelProperty("订单状态")
    private String orderStatus;
    
    @ApiModelProperty("订单录入时间")
    private Date inputTime;
    
    @ApiModelProperty("创建时间")
    private Date insertTimeForHis;
    
    @ApiModelProperty("订单时间信息")
    private List<OrderTimeVo> orderTimes;
} 
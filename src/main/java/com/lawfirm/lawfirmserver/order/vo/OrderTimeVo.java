package com.lawfirm.lawfirmserver.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单时间视图对象
 */
@Data
@ApiModel("订单时间信息")
public class OrderTimeVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单时间ID")
    private Long orderTimeId;

    @ApiModelProperty("订单ID")
    private Long orderId;

    @ApiModelProperty("时间节点类型")
    private String timeNode;

    @ApiModelProperty("时间值")
    private Date timeValue;
} 
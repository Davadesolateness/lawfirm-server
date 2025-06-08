package com.lawfirm.lawfirmserver.order.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author dong
 */
@Data
public class CreateOrderVo {
    // 用户ID
    private Long userId;

    // 律师ID
    private Long lawyerId;

    // 服务时长（分钟）
    private Long serviceDuration;

    // 服务次数
    private Long serviceCount;

    // 开始时间
    private Date startTime;

    // 结束时间
    private Date endTime;

    // 用户类型
    private String userType;
} 
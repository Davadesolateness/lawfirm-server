package com.lawfirm.lawfirmserver.user.vo;

import lombok.Data;

/**
 * 扣减用户优惠次数请求参数VO
 * @author dong
 */
@Data
public class DeductDiscountVo {
    // 用户ID
    private Long userId;

    // 订单ID
    private String orderId;
} 
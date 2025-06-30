package com.lawfirm.lawfirmserver.chat.dto;

import lombok.Data;

/**
 * 创建聊天会话请求参数
 */
@Data
public class CreateSessionRequest {
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 用户类型：individual（个人客户）、corporate（法人客户）
     */
    private String userType;
    
    /**
     * 客服ID
     */
    private Long serviceId;
    
    /**
     * 客服类型：customer_service（客服）
     */
    private String serviceType = "customer_service";
} 
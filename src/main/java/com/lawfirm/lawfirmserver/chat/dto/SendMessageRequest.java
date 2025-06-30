package com.lawfirm.lawfirmserver.chat.dto;

import lombok.Data;

/**
 * 发送消息请求参数
 */
@Data
public class SendMessageRequest {
    /**
     * 会话ID
     */
    private Long sessionId;
    
    /**
     * 发送者ID
     */
    private Long senderId;
    
    /**
     * 接收者ID
     */
    private Long receiverId;
    
    /**
     * 消息内容
     */
    private String messageContent;
} 
package com.lawfirm.lawfirmserver.chat.dto;

import lombok.Data;

/**
 * 自动回复配置请求参数
 */
@Data
public class AutoReplyConfigRequest {
    /**
     * 配置ID（更新时需要）
     */
    private Long configId;

    /**
     * 触发自动回复的关键词或问题模式
     */
    private String keyword;

    /**
     * 自动回复的内容
     */
    private String replyContent;

    /**
     * 匹配类型：exact（精确匹配）、contains（包含匹配）、regex（正则匹配）
     */
    private String matchType = "contains";

    /**
     * 优先级，数字越小优先级越高
     */
    private Integer priority = 100;

    /**
     * 状态：active（启用）、inactive（禁用）
     */
    private String status = "active";

    /**
     * 分类：greeting（问候）、faq（常见问题）、legal（法律咨询）
     */
    private String category;

    /**
     * 配置描述
     */
    private String description;

    /**
     * 创建用户ID
     */
    private Long createUserId;
} 
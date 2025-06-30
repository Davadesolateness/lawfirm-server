package com.lawfirm.lawfirmserver.chat.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 自动回复配置表的VO对象
 * 对应表名：auto_reply_config
 */
@Data
public class AutoReplyConfigVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 对应字段：configId，备注：配置唯一标识，自增主键
     */
    private Long configId;

    /**
     * 对应字段：keyword，备注：触发自动回复的关键词或问题模式
     */
    private String keyword;

    /**
     * 对应字段：replyContent，备注：自动回复的内容
     */
    private String replyContent;

    /**
     * 对应字段：matchType，备注：匹配类型，如 exact（精确匹配）、contains（包含匹配）、regex（正则匹配）
     */
    private String matchType;

    /**
     * 对应字段：priority，备注：优先级，数字越小优先级越高
     */
    private Integer priority;

    /**
     * 对应字段：status，备注：状态，如 active（启用）、inactive（禁用）
     */
    private String status;

    /**
     * 对应字段：category，备注：分类，如 greeting（问候）、faq（常见问题）、legal（法律咨询）
     */
    private String category;

    /**
     * 对应字段：description，备注：配置描述
     */
    private String description;

    /**
     * 对应字段：createUserId，备注：创建用户ID
     */
    private Long createUserId;

    /**
     * 对应字段：insertTimeForHis，备注：记录插入时间
     */
    private Date insertTimeForHis;

    /**
     * 对应字段：operateTimeForHis，备注：记录更新时间
     */
    private Date operateTimeForHis;
} 
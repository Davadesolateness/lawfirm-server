package com.lawfirm.lawfirmserver.chat.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 通过ins-framework-mybatis工具自动生成，表chatmessage的VO对象<br/>
 * 对应表名：chatmessage
 */
@Data
public class ChatMessageVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 对应字段：messageId,备注：消息唯一标识，自增主键
     */
    private Long messageId;
    /**
     * 对应字段：sessionId,备注：消息所属的会话 ID
     */
    private Long sessionId;
    /**
     * 对应字段：senderId,备注：消息发送者 ID，可能是法人客户、个人客户、律师或客服
     */
    private Long senderId;
    /**
     * 对应字段：receiverId,备注：消息接收者 ID，可能是法人客户、个人客户、律师或客服
     */
    private Long receiverId;
    /**
     * 对应字段：messageContent,备注：消息具体内容
     */
    private String messageContent;
    /**
     * 对应字段：sendTime,备注：消息发送时间
     */
    private Date sendTime;
    /**
     * 对应字段：isRead,备注：消息是否已读，0 表示未读，1 表示已读
     */
    private Boolean isRead;
    /**
     * 对应字段：insertTimeForHis,备注：消息记录插入时间
     */
    private Date insertTimeForHis;
    /**
     * 对应字段：operateTimeForHis,备注：消息记录更新时间
     */
    private Date operateTimeForHis;

}

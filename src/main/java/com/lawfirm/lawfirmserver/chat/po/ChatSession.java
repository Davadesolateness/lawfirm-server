package com.lawfirm.lawfirmserver.chat.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 通过ins-framework-mybatis工具自动生成，请勿手工修改。表chatsession的PO对象<br/>
 * 对应表名：chatsession
 */
@Data
public class ChatSession implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 对应字段：sessionId,备注：会话唯一标识，自增主键
     */
    private Long sessionId;
    /**
     * 对应字段：creatorId,备注：聊天会话创建者的 ID，可能是法人客户、个人客户、律师或客服
     */
    private Long creatorId;
    /**
     * 对应字段：creatorType,备注：聊天会话创建者的类型，取值可为 corporate（法人客户）、individual（个人客户）、lawyer（律师）、customer_service（客服）
     */
    private String creatorType;
    /**
     * 对应字段：participantId,备注：聊天会话参与者的 ID，可能是法人客户、个人客户、律师或客服
     */
    private Long participantId;
    /**
     * 对应字段：participantType,备注：聊天会话参与者的类型，取值可为 corporate（法人客户）、individual（个人客户）、lawyer（律师）、customer_service（客服）
     */
    private String participantType;
    /**
     * 对应字段：chatType,备注：聊天类型，如 user-lawyer（用户和律师的会话）、user-customer_service（用户和客服的会话）等
     */
    private String chatType;
    /**
     * 对应字段：startTime,备注：会话开始时间
     */
    private Date startTime;
    /**
     * 对应字段：endTime,备注：会话结束时间，可为 NULL
     */
    private Date endTime;
    /**
     * 对应字段：status,备注：会话状态，如 active（活跃）、closed（已关闭）等
     */
    private String status;
    /**
     * 对应字段：insertTimeForHis,备注：会话记录插入时间
     */
    private Date insertTimeForHis;
    /**
     * 对应字段：operateTimeForHis,备注：会话记录更新时间
     */
    private Date operateTimeForHis;

}

-- 创建聊天会话表，用于记录聊天会话的基本信息和参与者信息
CREATE TABLE chatSession
(
    sessionId         BIGINT AUTO_INCREMENT COMMENT '会话唯一标识，自增主键',
    creatorId         BIGINT COMMENT '聊天会话创建者的 ID，可能是法人客户、个人客户、律师或客服',
    creatorType       VARCHAR(20) COMMENT '聊天会话创建者的类型，取值可为 corporate（法人客户）、individual（个人客户）、lawyer（律师）、customer_service（客服）',
    participantId     BIGINT COMMENT '聊天会话参与者的 ID，可能是法人客户、个人客户、律师或客服',
    participantType   VARCHAR(20) COMMENT '聊天会话参与者的类型，取值可为 corporate（法人客户）、individual（个人客户）、lawyer（律师）、customer_service（客服）',
    chatType          VARCHAR(20) COMMENT '聊天类型，如 user-lawyer（用户和律师的会话）、user-customer_service（用户和客服的会话）等',
    startTime         DATETIME COMMENT '会话开始时间',
    endTime           DATETIME COMMENT '会话结束时间，可为 NULL',
    status            VARCHAR(20) COMMENT '会话状态，如 active（活跃）、closed（已关闭）等',
    insertTimeForHis  DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '会话记录插入时间',
    operateTimeForHis DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '会话记录更新时间',
    PRIMARY KEY (sessionId)
);
-- 为 chatSession 表的 creatorId 字段添加独立索引，加快根据创建者 ID 查询会话的速度
CREATE INDEX idx_chatSession_creatorId ON chatSession (creatorId);
-- 为 chatSession 表的 creatorType 字段添加独立索引，加快根据创建者类型查询会话的速度
CREATE INDEX idx_chatSession_creatorType ON chatSession (creatorType);
-- 为 chatSession 表的 participantId 字段添加独立索引，加快根据参与者 ID 查询会话的速度
CREATE INDEX idx_chatSession_participantId ON chatSession (participantId);
-- 为 chatSession 表的 participantType 字段添加独立索引，加快根据参与者类型查询会话的速度
CREATE INDEX idx_chatSession_participantType ON chatSession (participantType);
-- 为 chatSession 表的 chatType 字段添加独立索引，加快根据聊天类型查询会话的速度
CREATE INDEX idx_chatSession_chatType ON chatSession (chatType);

-- 创建聊天消息表，用于存储聊天过程中的具体消息
CREATE TABLE chatMessage
(
    messageId         BIGINT AUTO_INCREMENT COMMENT '消息唯一标识，自增主键',
    sessionId         BIGINT COMMENT '消息所属的会话 ID',
    senderId          BIGINT COMMENT '消息发送者 ID，可能是法人客户、个人客户、律师或客服',
    receiverId        BIGINT COMMENT '消息接收者 ID，可能是法人客户、个人客户、律师或客服',
    messageContent    TEXT COMMENT '消息具体内容',
    sendTime          DATETIME COMMENT '消息发送时间',
    isRead            TINYINT(1) DEFAULT 0 COMMENT '消息是否已读，0 表示未读，1 表示已读',
    insertTimeForHis  DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '消息记录插入时间',
    operateTimeForHis DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '消息记录更新时间',
    PRIMARY KEY (messageId)
);
-- 为 chatMessage 表的 sessionId 字段添加独立索引，加快根据会话 ID 查询消息的速度
CREATE INDEX idx_chatMessage_sessionId ON chatMessage (sessionId);
-- 为 chatMessage 表的 senderId 字段添加独立索引，加快根据发送者 ID 查询消息的速度
CREATE INDEX idx_chatMessage_senderId ON chatMessage (senderId);
-- 为 chatMessage 表的 receiverId 字段添加独立索引，加快根据接收者 ID 查询消息的速度
CREATE INDEX idx_chatMessage_receiverId ON chatMessage (receiverId);
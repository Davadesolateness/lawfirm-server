-- 创建聊天会话表，用于记录聊天会话的基本信息和参与者信息
CREATE TABLE chat_session
(
    session_id           BIGINT AUTO_INCREMENT COMMENT '会话唯一标识，自增主键',
    creator_id           BIGINT COMMENT '聊天会话创建者的 ID，可能是法人客户、个人客户、律师或客服',
    creator_type         VARCHAR(20) COMMENT '聊天会话创建者的类型，取值可为 corporate（法人客户）、individual（个人客户）、lawyer（律师）、customer_service（客服）',
    participant_id       BIGINT COMMENT '聊天会话参与者的 ID，可能是法人客户、个人客户、律师或客服',
    participant_type     VARCHAR(20) COMMENT '聊天会话参与者的类型，取值可为 corporate（法人客户）、individual（个人客户）、lawyer（律师）、customer_service（客服）',
    chat_type            VARCHAR(20) COMMENT '聊天类型，如 user-lawyer（用户和律师的会话）、user-customer_service（用户和客服的会话）等',
    start_time           DATETIME COMMENT '会话开始时间',
    end_time             DATETIME COMMENT '会话结束时间，可为 NULL',
    status               VARCHAR(20) COMMENT '会话状态，如 active（活跃）、closed（已关闭）等',
    insert_time_for_his  DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '会话记录插入时间',
    operate_time_for_his DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '会话记录更新时间',
    PRIMARY KEY (session_id)
);
-- 为 chat_session 表的 creator_id 字段添加独立索引，加快根据创建者 ID 查询会话的速度
CREATE INDEX idx_chat_session_creator_id ON chat_session (creator_id);
-- 为 chat_session 表的 creator_type 字段添加独立索引，加快根据创建者类型查询会话的速度
CREATE INDEX idx_chat_session_creator_type ON chat_session (creator_type);
-- 为 chat_session 表的 participant_id 字段添加独立索引，加快根据参与者 ID 查询会话的速度
CREATE INDEX idx_chat_session_participant_id ON chat_session (participant_id);
-- 为 chat_session 表的 participant_type 字段添加独立索引，加快根据参与者类型查询会话的速度
CREATE INDEX idx_chat_session_participant_type ON chat_session (participant_type);
-- 为 chat_session 表的 chat_type 字段添加独立索引，加快根据聊天类型查询会话的速度
CREATE INDEX idx_chat_session_chat_type ON chat_session (chat_type);

-- 创建聊天消息表，用于存储聊天过程中的具体消息
CREATE TABLE chat_message
(
    message_id           BIGINT AUTO_INCREMENT COMMENT '消息唯一标识，自增主键',
    session_id           BIGINT COMMENT '消息所属的会话 ID',
    sender_id            BIGINT COMMENT '消息发送者 ID，可能是法人客户、个人客户、律师或客服',
    receiver_id          BIGINT COMMENT '消息接收者 ID，可能是法人客户、个人客户、律师或客服',
    message_content      TEXT COMMENT '消息具体内容',
    send_time            DATETIME COMMENT '消息发送时间',
    is_read              TINYINT(1) DEFAULT 0 COMMENT '消息是否已读，0 表示未读，1 表示已读',
    insert_time_for_his  DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '消息记录插入时间',
    operate_time_for_his DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '消息记录更新时间',
    PRIMARY KEY (message_id)
);
-- 为 chat_message 表的 session_id 字段添加独立索引，加快根据会话 ID 查询消息的速度
CREATE INDEX idx_chat_message_session_id ON chat_message (session_id);
-- 为 chat_message 表的 sender_id 字段添加独立索引，加快根据发送者 ID 查询消息的速度
CREATE INDEX idx_chat_message_sender_id ON chat_message (sender_id);
-- 为 chat_message 表的 receiver_id 字段添加独立索引，加快根据接收者 ID 查询消息的速度
CREATE INDEX idx_chat_message_receiver_id ON chat_message (receiver_id);
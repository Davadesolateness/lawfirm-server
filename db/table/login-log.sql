-- 若需删除 loginLog 表，可取消此注释
-- DROP TABLE IF EXISTS loginLog;
CREATE TABLE loginLog
(
    id                BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    userId            BIGINT COMMENT '用户ID',
    phone             VARCHAR(20) COMMENT '手机号',
    loginType         INT COMMENT '登录类型：1-密码登录，2-验证码登录，3-微信登录',
    status            INT COMMENT '登录状态：0-失败，1-成功',
    ip                VARCHAR(50) COMMENT 'IP地址',
    device            VARCHAR(255) COMMENT '设备信息',
    remark            VARCHAR(255) COMMENT '备注',
    createTime        DATETIME COMMENT '创建时间',
    insertTimeForHis  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
    operateTimeForHis TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT '登录日志表';

-- 为 loginLog 表添加索引
CREATE INDEX idx_loginLog_insertTimeForHis ON loginLog (insertTimeForHis);
CREATE INDEX idx_loginLog_operateTimeForHis ON loginLog (operateTimeForHis);
CREATE TABLE login_log
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id     BIGINT COMMENT '用户ID',
    phone       VARCHAR(20) COMMENT '手机号',
    login_type  INT COMMENT '登录类型：1-密码登录，2-验证码登录，3-微信登录',
    status      INT COMMENT '登录状态：0-失败，1-成功',
    ip          VARCHAR(50) COMMENT 'IP地址',
    device      VARCHAR(255) COMMENT '设备信息',
    remark      VARCHAR(255) COMMENT '备注',
    create_time DATETIME COMMENT '创建时间'
) COMMENT '登录日志表';
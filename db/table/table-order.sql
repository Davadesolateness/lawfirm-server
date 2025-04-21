-- 记录订单的详细信息
CREATE TABLE orders
(
    order_id             BIGINT AUTO_INCREMENT COMMENT '订单唯一标识，自增主键',
    user_id              BIGINT COMMENT '下单的用户 ID',
    user_type            VARCHAR(20) COMMENT '用户类型，如 customer（客户）、other（其他）等',
    order_type           VARCHAR(50) COMMENT '订单类型，如 lawyer_service（律师服务订单）、buy_membership（购买会员订单）、welfare_distribution（福利发放订单）等',
    service_duration     BIGINT COMMENT '服务时长，单位为分钟',
    service_count        BIGINT COMMENT '服务次数，包含福利发放服务次数',
    purchase_amount      DECIMAL(10, 2) COMMENT '购买金额，用于购买会员订单',
    order_status         VARCHAR(20) COMMENT '订单状态，如 pending（待处理）、completed（已完成）、cancelled（已取消）等',
    input_time           DATETIME COMMENT '订单录入时间',
    insert_time_for_his  DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '订单记录插入时间',
    operate_time_for_his DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '订单记录更新时间',
    PRIMARY KEY (order_id)
);
-- 为 orders 表添加索引
CREATE INDEX idx_orders_user_id ON orders (user_id);
CREATE INDEX idx_orders_user_type ON orders (user_type);
CREATE INDEX idx_orders_order_type ON orders (order_type);
CREATE INDEX idx_orders_order_status ON orders (order_status);

-- 记录订单的时间相关信息，可能用于跟踪订单的时间节点
CREATE TABLE order_times
(
    order_time_id        BIGINT AUTO_INCREMENT COMMENT '订单时间记录唯一标识，自增主键',
    order_id             BIGINT COMMENT '关联的订单 ID',
    time_node            VARCHAR(20) COMMENT '时间节点类型，如服务开始时间、服务结束时间等',
    time_value           DATETIME COMMENT '对应时间节点的值',
    insert_time_for_his  DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '时间记录插入时间',
    operate_time_for_his DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间记录更新时间',
    PRIMARY KEY (order_time_id)
);
-- 为 order_times 表添加索引
CREATE INDEX idx_order_times_order_id ON order_times (order_id);

-- 记录律师的服务次数和时长统计信息
CREATE TABLE lawyer_service_stats
(
    lawyer_id              BIGINT COMMENT '律师 ID',
    total_service_count    BIGINT COMMENT '律师总的服务次数',
    total_service_duration BIGINT COMMENT '律师总的服务时长，单位为分钟',
    insert_time_for_his    DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '统计信息插入时间',
    operate_time_for_his   DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '统计信息更新时间',
    PRIMARY KEY (lawyer_id)
);


-- 创建客户服务信息表
DROP TABLE IF EXISTS customer_service_info;
CREATE TABLE IF NOT EXISTS customer_service_info (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID，关联user表',
    client_id BIGINT COMMENT '客户ID，可能关联individual_client或corporate_client表',
    client_type VARCHAR(20) COMMENT '客户类型：individual - 个人客户，corporate - 法人客户',
    remaining_service_count INT DEFAULT 0 COMMENT '剩余服务次数',
    remaining_service_minutes INT DEFAULT 0 COMMENT '剩余服务时长（分钟）',
    service_level INT DEFAULT 1 COMMENT '服务级别：1-基础，2-标准，3-高级，4-VIP/企业VIP',
    max_employee_count INT DEFAULT 5 COMMENT '企业客户专用：员工数量上限',
    service_start_time DATETIME COMMENT '服务包开始时间',
    service_expire_time DATETIME COMMENT '服务包到期时间',
    update_time DATETIME COMMENT '上次更新时间',
    is_valid_flag CHAR(1) DEFAULT '1' COMMENT '是否有效：1-有效，0-无效',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user_id (user_id),
    INDEX idx_client_info (client_id, client_type)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户服务信息表';

-- 初始化一些测试数据
INSERT INTO customer_service_info (user_id, client_id, client_type, remaining_service_count, remaining_service_minutes, service_level, service_start_time, service_expire_time, update_time)
VALUES
    (1, 1, 'individual', 5, 300, 2, DATE_SUB(NOW(), INTERVAL 1 MONTH), DATE_ADD(NOW(), INTERVAL 11 MONTH), NOW()),
    (2, 1, 'corporate', 10, 600, 3, DATE_SUB(NOW(), INTERVAL 2 MONTH), DATE_ADD(NOW(), INTERVAL 10 MONTH), NOW()),
    (3, 2, 'individual', 3, 180, 1, DATE_SUB(NOW(), INTERVAL 3 MONTH), DATE_ADD(NOW(), INTERVAL 9 MONTH), NOW());


-- 记录福利发放到哪些客户
CREATE TABLE welfare_distribution_customers
(
    distribution_id      BIGINT AUTO_INCREMENT COMMENT '福利发放记录唯一标识，自增主键',
    order_id             BIGINT COMMENT '关联的福利发放订单 ID',
    user_id              BIGINT COMMENT '获得福利的用户 ID',
    insert_time_for_his  DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '记录插入时间',
    operate_time_for_his DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
    PRIMARY KEY (distribution_id)
);
-- 为 welfare_distribution_customers 表添加索引
CREATE INDEX idx_welfare_distribution_customers_order_id ON welfare_distribution_customers (order_id);
CREATE INDEX idx_welfare_distribution_customers_user_id ON welfare_distribution_customers (user_id);



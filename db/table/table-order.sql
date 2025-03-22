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

-- 记录客户服务基本信息
CREATE TABLE customer_service_info
(
    id                         BIGINT AUTO_INCREMENT COMMENT '自增主键',
    customer_id                BIGINT COMMENT '客户 ID',
    customer_type              VARCHAR(20) COMMENT '客户类型',
    remaining_service_count    BIGINT COMMENT '客户剩余服务次数',
    remaining_service_duration BIGINT COMMENT '客户剩余服务时长，单位为分钟',
    insert_time_for_his        DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '记录插入时间',
    operate_time_for_his       DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
    PRIMARY KEY (id)
);
-- 为 customer_service_info 表添加索引
CREATE INDEX idx_customer_service_info_id ON customer_service_info (id);
CREATE INDEX idx_customer_service_info_customer_id ON customer_service_info (customer_id);
CREATE INDEX idx_customer_service_info_customer_type ON customer_service_info (customer_type);

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
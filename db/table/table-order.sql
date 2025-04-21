-- 记录订单的详细信息
CREATE TABLE orders
(
    orderId           BIGINT AUTO_INCREMENT COMMENT '订单唯一标识，自增主键',
    userId            BIGINT COMMENT '下单的用户 ID',
    userType          VARCHAR(20) COMMENT '用户类型，如 customer（客户）、other（其他）等',
    orderType         VARCHAR(50) COMMENT '订单类型，如 lawyer_service（律师服务订单）、buy_membership（购买会员订单）、welfare_distribution（福利发放订单）等',
    serviceDuration   BIGINT COMMENT '服务时长，单位为分钟',
    serviceCount      BIGINT COMMENT '服务次数，包含福利发放服务次数',
    purchaseAmount    DECIMAL(10, 2) COMMENT '购买金额，用于购买会员订单',
    orderStatus       VARCHAR(20) COMMENT '订单状态，如 pending（待处理）、completed（已完成）、cancelled（已取消）等',
    inputTime         DATETIME COMMENT '订单录入时间',
    insertTimeForHis  DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '订单记录插入时间',
    operateTimeForHis DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '订单记录更新时间',
    PRIMARY KEY (orderId)
);
-- 为 orders 表添加索引
CREATE INDEX idx_orders_userId ON orders (userId);
CREATE INDEX idx_orders_userType ON orders (userType);
CREATE INDEX idx_orders_orderType ON orders (orderType);
CREATE INDEX idx_orders_orderStatus ON orders (orderStatus);

-- 记录订单的时间相关信息，可能用于跟踪订单的时间节点
CREATE TABLE orderTimes
(
    orderTimeId       BIGINT AUTO_INCREMENT COMMENT '订单时间记录唯一标识，自增主键',
    orderId           BIGINT COMMENT '关联的订单 ID',
    timeNode          VARCHAR(20) COMMENT '时间节点类型，如服务开始时间、服务结束时间等',
    timeValue         DATETIME COMMENT '对应时间节点的值',
    insertTimeForHis  DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '时间记录插入时间',
    operateTimeForHis DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间记录更新时间',
    PRIMARY KEY (orderTimeId)
);
-- 为 orderTimes 表添加索引
CREATE INDEX idx_orderTimes_orderId ON orderTimes (orderId);

-- 记录律师的服务次数和时长统计信息
CREATE TABLE lawyerServiceStats
(
    lawyerId             BIGINT COMMENT '律师 ID',
    totalServiceCount    BIGINT COMMENT '律师总的服务次数',
    totalServiceDuration BIGINT COMMENT '律师总的服务时长，单位为分钟',
    insertTimeForHis     DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '统计信息插入时间',
    operateTimeForHis    DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '统计信息更新时间',
    PRIMARY KEY (lawyerId)
);

-- 记录客户服务基本信息
CREATE TABLE customerServiceInfo
(
    id                       BIGINT AUTO_INCREMENT COMMENT '自增主键',
    customerId               BIGINT COMMENT '客户 ID',
    customerType             VARCHAR(20) COMMENT '客户类型',
    remainingServiceCount    BIGINT COMMENT '客户剩余服务次数',
    remainingServiceDuration BIGINT COMMENT '客户剩余服务时长，单位为分钟',
    insertTimeForHis         DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '记录插入时间',
    operateTimeForHis        DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
    PRIMARY KEY (id)
);
-- 为 customerServiceInfo 表添加索引
CREATE INDEX idx_customerServiceInfo_id ON customerServiceInfo (id);
CREATE INDEX idx_customerServiceInfo_customerId ON customerServiceInfo (customerId);
CREATE INDEX idx_customerServiceInfo_customerType ON customerServiceInfo (customerType);

-- 记录福利发放到哪些客户
CREATE TABLE welfareDistributionCustomers
(
    distributionId    BIGINT AUTO_INCREMENT COMMENT '福利发放记录唯一标识，自增主键',
    orderId           BIGINT COMMENT '关联的福利发放订单 ID',
    userId            BIGINT COMMENT '获得福利的用户 ID',
    insertTimeForHis  DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '记录插入时间',
    operateTimeForHis DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
    PRIMARY KEY (distributionId)
);
-- 为 welfareDistributionCustomers 表添加索引
CREATE INDEX idx_welfareDistributionCustomers_orderId ON welfareDistributionCustomers (orderId);
CREATE INDEX idx_welfareDistributionCustomers_userId ON welfareDistributionCustomers (userId);
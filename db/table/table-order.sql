-- 若需删除 orders 表，可取消此注释
-- DROP TABLE IF EXISTS orders;
-- 记录订单的详细信息
CREATE TABLE orders
(
    orderId           BIGINT AUTO_INCREMENT COMMENT '订单唯一标识，自增主键',
    userId            BIGINT COMMENT '下单的用户 ID',
    userType          VARCHAR(20) COMMENT '用户类型，如 customer（客户）、other（其他）等',
    orderType         VARCHAR(50) COMMENT '订单类型，如 lawyer_service（律师服务订单）、buy_membership（购买会员订单）、welfare_distribution（福利发放订单）等',
    serviceDuration   BIGINT COMMENT '服务时长，单位为分钟',
    serviceCount      BIGINT COMMENT '服务次数，包含福利发放服务次数',
    lawyerId          BIGINT COMMENT '律师 ID，用于律师服务订单下单用户与律师关联',
    amount            DECIMAL(10, 2) COMMENT '购买金额，用于购买会员订单',
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
CREATE INDEX idx_orders_lawyerId ON orders (lawyerId);
CREATE INDEX idx_orders_orderStatus ON orders (orderStatus);

-- 若需删除 orderTime 表，可取消此注释
-- DROP TABLE IF EXISTS orderTime;
-- 记录订单的时间相关信息，可能用于跟踪订单的时间节点
CREATE TABLE orderTime
(
    orderTimeId       BIGINT AUTO_INCREMENT COMMENT '订单时间记录唯一标识，自增主键',
    orderId           BIGINT COMMENT '关联的订单 ID',
    timeNode          VARCHAR(20) COMMENT '时间节点类型，如服务开始时间、服务结束时间等',
    timeValue         DATETIME COMMENT '对应时间节点的值',
    insertTimeForHis  DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '时间记录插入时间',
    operateTimeForHis DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间记录更新时间',
    PRIMARY KEY (orderTimeId)
);
-- 为 orderTime 表添加索引
CREATE INDEX idx_orderTime_orderId ON orderTime (orderId);

-- 若需删除 lawyerServiceStat 表，可取消此注释
-- DROP TABLE IF EXISTS lawyerServiceStat;
-- 记录律师的服务次数和时长统计信息
CREATE TABLE lawyerServiceStat
(
    lawyerId             BIGINT COMMENT '律师 ID',
    totalServiceCount    BIGINT COMMENT '律师总的服务次数',
    totalServiceDuration BIGINT COMMENT '律师总的服务时长，单位为分钟',
    insertTimeForHis     DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '统计信息插入时间',
    operateTimeForHis    DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '统计信息更新时间',
    PRIMARY KEY (lawyerId)
);

-- 若需删除 welfareDistributionCustomer 表，可取消此注释
-- DROP TABLE IF EXISTS welfareDistributionCustomer;
-- 记录福利发放到哪些客户
CREATE TABLE welfareDistributionCustomer
(
    distributionId    BIGINT AUTO_INCREMENT COMMENT '福利发放记录唯一标识，自增主键',
    orderId           BIGINT COMMENT '关联的福利发放订单 ID',
    userId            BIGINT COMMENT '获得福利的用户 ID',
    insertTimeForHis  DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '记录插入时间',
    operateTimeForHis DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
    PRIMARY KEY (distributionId)
);
-- 为 welfareDistributionCustomer 表添加索引
CREATE INDEX idx_welfareDistributionCustomer_orderId ON welfareDistributionCustomer (orderId);
CREATE INDEX idx_welfareDistributionCustomer_userId ON welfareDistributionCustomer (userId);
INSERT INTO orders (userId, userType, orderType, serviceDuration, serviceCount, lawyerId, amount, orderStatus,
                    inputTime)
VALUES
-- 律师服务订单（需要关联 lawyerId）
(5, 'customer', 'lawyer_service', 30, NULL, 3, NULL, 'completed', '2023-05-12 14:30:00'),
(12, 'customer', 'lawyer_service', 45, NULL, 7, NULL, 'completed', '2023-06-18 09:15:00'),
(8, 'other', 'lawyer_service', 60, NULL, 2, NULL, 'pending', '2023-07-01 16:45:00'),
(22, 'customer', 'lawyer_service', 30, NULL, 5, NULL, 'cancelled', '2023-08-22 11:20:00'),
(17, 'customer', 'lawyer_service', 90, NULL, 9, NULL, 'completed', '2023-09-05 13:10:00'),

-- 购买会员订单（需要金额 amount）
(33, 'customer', 'buy_membership', NULL, NULL, NULL, 299.00, 'completed', '2023-04-10 10:00:00'),
(45, 'other', 'buy_membership', NULL, NULL, NULL, 599.00, 'pending', '2023-05-25 15:30:00'),
(27, 'customer', 'buy_membership', NULL, NULL, NULL, 199.00, 'cancelled', '2023-07-14 08:45:00'),

-- 福利发放订单（需要服务次数 serviceCount）
(6, 'customer', 'welfare_distribution', NULL, 5, NULL, NULL, 'completed', '2023-03-08 12:00:00'),
(19, 'other', 'welfare_distribution', NULL, 10, NULL, NULL, 'completed', '2023-06-30 17:20:00'),
(41, 'customer', 'welfare_distribution', NULL, 3, NULL, NULL, 'pending', '2023-08-15 14:55:00'),

-- 混合数据
(9, 'customer', 'lawyer_service', 60, NULL, 4, NULL, 'completed', '2023-02-28 10:30:00'),
(15, 'other', 'buy_membership', NULL, NULL, NULL, 99.00, 'completed', '2023-10-01 09:00:00'),
(28, 'customer', 'welfare_distribution', NULL, 8, NULL, NULL, 'cancelled', '2023-09-18 16:40:00'),
(3, 'customer', 'lawyer_service', 45, NULL, 6, NULL, 'pending', '2023-11-05 18:15:00'),
(50, 'other', 'buy_membership', NULL, NULL, NULL, 399.00, 'completed', '2023-12-12 12:30:00'),
(14, 'customer', 'lawyer_service', 30, NULL, 1, NULL, 'completed', '2023-01-15 08:20:00'),
(36, 'customer', 'welfare_distribution', NULL, 2, NULL, NULL, 'pending', '2023-07-30 14:00:00'),
(23, 'other', 'buy_membership', NULL, NULL, NULL, 199.00, 'cancelled', '2023-04-22 17:45:00'),
(7, 'customer', 'lawyer_service', 90, NULL, 8, NULL, 'completed', '2023-06-10 11:10:00');

INSERT INTO orderTime (orderId, timeNode, timeValue)
VALUES
-- 示例数据：每个订单关联 1-2 个时间节点
(1, 'start', '2023-05-12 15:00:00'),
(1, 'end', '2023-05-12 15:30:00'),
(2, 'start', '2023-06-18 10:00:00'),
(3, 'start', '2023-07-01 17:00:00'),
(5, 'start', '2023-09-05 14:00:00'),
(5, 'end', '2023-09-05 15:30:00'),
(7, 'payment_time', '2023-07-14 09:00:00'),
(9, 'distribution_time', '2023-03-08 13:00:00'),
(10, 'distribution_time', '2023-06-30 18:00:00'),
(12, 'start', '2023-02-28 11:00:00'),
(12, 'end', '2023-02-28 12:00:00'),
(15, 'payment_time', '2023-11-05 19:00:00'),
(16, 'start', '2023-01-15 09:00:00'),
(16, 'end', '2023-01-15 09:30:00'),
(19, 'payment_time', '2023-04-22 18:00:00'),
(20, 'start', '2023-06-10 12:00:00'),
(20, 'end', '2023-06-10 13:30:00');
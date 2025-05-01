-- 用户数据（包含法人、个人、律师、管理员）
INSERT INTO users (openId, username, password, nickName, email, phoneNumber, userType, relatedEntityId, sourceType)
VALUES
-- 法人用户（关联 corporateClient 表）
(NULL, 'corp_user1', 'encrypted_pwd1', '企业一号', 'corp1@example.com', '13800010001', 'corporate', 1,
 'database_insert'),
(NULL, 'corp_user2', 'encrypted_pwd2', '企业二号', 'corp2@example.com', '13800010002', 'corporate', 2,
 'database_insert'),

-- 个人用户（关联 individualClient 表）
(NULL, 'indi_user1', 'encrypted_pwd3', '张三', 'zhangsan@example.com', '13800020001', 'individual', 1,
 'manual_register'),
(NULL, 'indi_user2', 'encrypted_pwd4', '李四', 'lisi@example.com', '13800020002', 'individual', 2, 'manual_register'),

-- 律师用户（关联 lawyer 表）
(NULL, 'lawyer_user1', 'encrypted_pwd5', '王律师', 'lawyer1@example.com', '13800030001', 'lawyer', 1,
 'database_insert'),
(NULL, 'lawyer_user2', 'encrypted_pwd6', '陈律师', 'lawyer2@example.com', '13800030002', 'lawyer', 2,
 'database_insert'),

-- 管理员用户（关联 administrator 表）
(NULL, 'admin_user1', 'encrypted_pwd7', '超级管理员', 'admin1@example.com', '13800040001', 'admin', 1,
 'database_insert'),
(NULL, 'admin_user2', 'encrypted_pwd8', '运营管理员', 'admin2@example.com', '13800040002', 'admin', 2,
 'database_insert');

-- 法人客户数据（与 users 表关联）
INSERT INTO corporateClient (companyName, certificateType, certificateNumber, contactPerson)
VALUES ('北京正义科技有限公司', '营业执照', '91310101MA1F123456', '张经理'),
       ('上海海华贸易有限公司', '组织机构代码证', '91310230MA1F654321', '李主管'),
       ('广州白云实业集团', '统一社会信用代码', '91440101MA5ACDE12', '王总');


-- 个人客户数据（与 users 表关联）
INSERT INTO individualClient (fullName, gender, birthDate)
VALUES ('张三', 'male', '1990-05-15'),
       ('李四', 'female', '1985-08-22'),
       ('王五', 'other', '2000-01-10'),
       ('赵六', 'male', '1995-12-30');

-- 管理员数据（与 users 表关联）
INSERT INTO administrator (adminRole, adminLevel, parentAdminId)
VALUES ('超级管理员', 1, NULL),
       ('运营管理员', 2, 1),
       ('客服主管', 3, 2),
       ('审核专员', 4, 2);


-- 客户服务信息（关联用户和客户）
INSERT INTO customerServiceInfo (userId, clientId, clientType, remainingServiceCount, serviceStartTime,
                                 serviceExpireTime)
VALUES
-- 法人客户服务
(1, 1, 'corporate', 50, '2023-01-01 00:00:00', '2024-01-01 00:00:00'),
(2, 2, 'corporate', 100, '2023-06-01 00:00:00', '2024-06-01 00:00:00'),

-- 个人客户服务
(3, 1, 'individual', 10, '2023-03-15 00:00:00', '2023-12-31 00:00:00'),
(4, 2, 'individual', 20, '2023-07-01 00:00:00', '2024-07-01 00:00:00'),

-- 律师服务
(5, 1, 'lawyer', 200, '2023-01-01 00:00:00', '2025-01-01 00:00:00'),
(6, 2, 'lawyer', 300, '2023-09-01 00:00:00', '2024-09-01 00:00:00');

















-- 自动回复配置表
CREATE TABLE auto_reply_config (
    configId BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '配置唯一标识，自增主键',
    keyword VARCHAR(500) NOT NULL COMMENT '触发自动回复的关键词或问题模式',
    replyContent TEXT NOT NULL COMMENT '自动回复的内容',
    matchType VARCHAR(20) NOT NULL DEFAULT 'contains' COMMENT '匹配类型：exact（精确匹配）、contains（包含匹配）、regex（正则匹配）',
    priority INT NOT NULL DEFAULT 100 COMMENT '优先级，数字越小优先级越高',
    status VARCHAR(20) NOT NULL DEFAULT 'active' COMMENT '状态：active（启用）、inactive（禁用）',
    category VARCHAR(50) COMMENT '分类：greeting（问候）、faq（常见问题）、legal（法律咨询）',
    description VARCHAR(500) COMMENT '配置描述',
    createUserId BIGINT COMMENT '创建用户ID',
    insertTimeForHis DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '记录插入时间',
    operateTimeForHis DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
    
    INDEX idx_status (status),
    INDEX idx_category (category),
    INDEX idx_priority (priority),
    INDEX idx_keyword (keyword(100))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='自动回复配置表';

-- 插入一些示例数据
INSERT INTO auto_reply_config (keyword, replyContent, matchType, priority, status, category, description, createUserId) VALUES
('你好', '您好！欢迎咨询我们的法律服务，我是智能客服助手。请问有什么可以帮助您的吗？', 'exact', 1, 'active', 'greeting', '问候语自动回复', 1),
('您好', '您好！欢迎咨询我们的法律服务，我是智能客服助手。请问有什么可以帮助您的吗？', 'exact', 1, 'active', 'greeting', '问候语自动回复', 1),
('在吗', '我在的！有什么法律问题需要咨询吗？', 'exact', 2, 'active', 'greeting', '询问在线状态回复', 1),
('收费', '我们的法律咨询服务收费标准如下：\n- 普通咨询：100元/小时\n- 专业咨询：200元/小时\n- 案件代理：根据案件复杂程度确定\n具体费用可以联系我们的客服人员详细了解。', 'contains', 10, 'active', 'faq', '收费标准自动回复', 1),
('价格', '我们的法律咨询服务收费标准如下：\n- 普通咨询：100元/小时\n- 专业咨询：200元/小时\n- 案件代理：根据案件复杂程度确定\n具体费用可以联系我们的客服人员详细了解。', 'contains', 10, 'active', 'faq', '价格询问自动回复', 1),
('工作时间', '我们的工作时间：\n周一至周五：9:00-18:00\n周六：9:00-17:00\n周日：休息\n如有紧急法律问题，可以留言，我们会在工作时间内及时回复。', 'contains', 15, 'active', 'faq', '工作时间询问回复', 1),
('离婚', '关于离婚法律问题，涉及财产分割、子女抚养等复杂事项。建议您详细描述具体情况，我们的专业律师会为您提供针对性的法律建议。', 'contains', 20, 'active', 'legal', '离婚咨询自动回复', 1),
('合同', '合同相关法律问题需要根据具体合同条款和争议情况进行分析。请您提供合同的关键条款或争议焦点，我们的律师会为您提供专业建议。', 'contains', 20, 'active', 'legal', '合同咨询自动回复', 1),
('谢谢', '不客气！如果还有其他法律问题需要咨询，随时联系我们。祝您生活愉快！', 'contains', 30, 'active', 'greeting', '感谢回复', 1),
('再见', '再见！如果以后有法律问题需要咨询，欢迎随时联系我们。祝您一切顺利！', 'contains', 30, 'active', 'greeting', '告别回复', 1); 
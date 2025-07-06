-- 轮播图表创建脚本
-- 表名：banner
-- 描述：用于存储首页轮播图信息

CREATE TABLE IF NOT EXISTS `banner` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(100) NOT NULL COMMENT '轮播图标题',
  `imageUrl` varchar(500) NOT NULL COMMENT '轮播图图片URL',
  `linkUrl` varchar(500) DEFAULT NULL COMMENT '轮播图链接URL',
  `sortOrder` int(11) DEFAULT 0 COMMENT '排序序号（数字越小越靠前）',
  `isEnabled` tinyint(1) DEFAULT 1 COMMENT '是否启用（1-启用，0-禁用）',
  `description` varchar(500) DEFAULT NULL COMMENT '轮播图描述',
  `insertTimeForHis` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据插入时间',
  `operateTimeForHis` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_enabled_sort` (`isEnabled`, `sortOrder`) COMMENT '启用状态和排序索引',
  KEY `idx_sort_order` (`sortOrder`) COMMENT '排序索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='轮播图表';

-- 插入示例数据
INSERT INTO `banner` (`title`, `imageUrl`, `linkUrl`, `sortOrder`, `isEnabled`, `description`) VALUES
('专业法律服务', '/images/banner1.jpg', '/services', 1, 1, '提供专业的法律咨询服务'),
('律师团队', '/images/banner2.jpg', '/lawyers', 2, 1, '经验丰富的律师团队'),
('在线咨询', '/images/banner3.jpg', '/consultation', 3, 1, '便捷的在线法律咨询'); 
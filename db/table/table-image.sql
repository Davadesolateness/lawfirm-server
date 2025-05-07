-- DROP TABLE IF EXISTS imageStorage;
-- 影像存储表
CREATE TABLE imageStorage
(
    imageId           BIGINT AUTO_INCREMENT COMMENT '影像唯一标识，自增主键',
    userId            BIGINT COMMENT '关联的用户 ID',
    imageType         VARCHAR(20) COMMENT '影像类型，如合同、证明等',
    fileExtension     VARCHAR(10) COMMENT '文件扩展名（不含点号），如jpg, png, pdf等',
    imageData         LONGBLOB COMMENT '影像的二进制数据',
    insertTimeForHis  DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '影像记录插入时间',
    operateTimeForHis DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '影像记录更新时间',
    PRIMARY KEY (imageId)
);
-- 为影像存储表添加索引
CREATE INDEX idx_imageStorage_userId ON imageStorage (userId);
CREATE INDEX idx_imageStorage_imageType ON imageStorage (imageType);


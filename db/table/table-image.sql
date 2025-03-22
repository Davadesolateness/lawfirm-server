-- 影像存储表
CREATE TABLE image_storage
(
    image_id             BIGINT AUTO_INCREMENT COMMENT '影像唯一标识，自增主键',
    user_id              BIGINT COMMENT '关联的用户 ID',
    image_type           VARCHAR(20) COMMENT '影像类型，如合同、证明等',
    image_data           LONGBLOB COMMENT '影像的二进制数据',
    insert_time_for_his  DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '影像记录插入时间',
    operate_time_for_his DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '影像记录更新时间',
    PRIMARY KEY (image_id)
);
-- 为影像存储表添加索引
CREATE INDEX idx_image_storage_user_id ON image_storage (user_id);
CREATE INDEX idx_image_storage_image_type ON image_storage (image_type);
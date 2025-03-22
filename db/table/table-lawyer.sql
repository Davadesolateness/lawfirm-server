-- 若需删除 lawyer 表，可取消此注释
-- DROP TABLE IF EXISTS lawyer;
-- 律师表，存储律师的额外信息
CREATE TABLE lawyer
(
    id                    BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '律师唯一标识，自增主键',
    law_firm              VARCHAR(200) COMMENT '所在律师事务所名称',
    lawyer_license_number VARCHAR(50) COMMENT '律师执业证号',
    specialization        VARCHAR(255) COMMENT '专业领域',
    is_valid_flag         CHAR(1)   DEFAULT '1' COMMENT '律师是否有效，1 表示有效，0 表示无效，默认为有效',
    insert_time_for_his   TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '律师信息插入时间，默认为当前时间',
    operate_time_for_his  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '律师信息操作时间，自动更新'
);
-- 创建律师表插入时间的索引，提高按插入时间查询的效率
CREATE INDEX idx_lawyer_insert_time_for_his ON lawyer (insert_time_for_his);
-- 创建律师表操作时间的索引，提高按操作时间查询的效率
CREATE INDEX idx_lawyer_operate_time_for_his ON lawyer (operate_time_for_his);

-- 律师专长表，用于存储律师的各类专长信息
-- 若需删除 lawyer_specialtie 表，可取消此注释
-- DROP TABLE IF EXISTS lawyer_specialtie;
CREATE TABLE lawyer_specialtie
(
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '专长唯一标识，自增主键',
    specialty_name       VARCHAR(100) NOT NULL UNIQUE COMMENT '专长名称，唯一',
    description          TEXT COMMENT '专长描述',
    insert_time_for_his  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '专长信息插入时间，默认为当前时间',
    operate_time_for_his TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '专长信息操作时间，自动更新'
);
-- 创建律师专长表插入时间的索引，提高按插入时间查询的效率
CREATE INDEX idx_lawyer_specialtie_insert_time_for_his ON lawyer_specialtie (insert_time_for_his);
-- 创建律师专长表操作时间的索引，提高按操作时间查询的效率
CREATE INDEX idx_lawyer_specialtie_operate_time_for_his ON lawyer_specialtie (operate_time_for_his);

-- 律师与专长的关联表，用于建立律师和专长之间的多对多关系
-- 若需删除 lawyer_specialty_relation 表，可取消此注释
-- DROP TABLE IF EXISTS lawyer_specialty_relation;
CREATE TABLE lawyer_specialty_relation
(
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '关联记录唯一标识，自增主键',
    lawyer_id            BIGINT NOT NULL COMMENT '关联的律师 ID',
    specialty_id         BIGINT NOT NULL COMMENT '关联的专长 ID',
    insert_time_for_his  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '关联信息插入时间，默认为当前时间',
    operate_time_for_his TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '关联信息操作时间，自动更新',
    -- 外键约束，当删除 lawyer 表中的记录时，此表中关联的记录会自动删除
    FOREIGN KEY (lawyer_id) REFERENCES lawyer (id) ON DELETE CASCADE,
    -- 外键约束，当删除 lawyer_specialtie 表中的记录时，此表中关联的记录会自动删除
    FOREIGN KEY (specialty_id) REFERENCES lawyer_specialtie (id) ON DELETE CASCADE
);
-- 创建律师与专长关联表律师 ID 的索引，提高按律师 ID 查询的效率
CREATE INDEX idx_lawyer_specialty_relation_lawyer_id ON lawyer_specialty_relation (lawyer_id);
-- 创建律师与专长关联表专长 ID 的索引，提高按专长 ID 查询的效率
CREATE INDEX idx_lawyer_specialty_relation_specialty_id ON lawyer_specialty_relation (specialty_id);
-- 创建律师与专长关联表插入时间的索引，提高按插入时间查询的效率
CREATE INDEX idx_lawyer_specialty_relation_insert_time_for_his ON lawyer_specialty_relation (insert_time_for_his);
-- 创建律师与专长关联表操作时间的索引，提高按操作时间查询的效率
CREATE INDEX idx_lawyer_specialty_relation_operate_time_for_his ON lawyer_specialty_relation (operate_time_for_his);
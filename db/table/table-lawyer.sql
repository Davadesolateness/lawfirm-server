-- 若需删除 lawyers 表，可取消此注释
-- DROP TABLE IF EXISTS lawyers;
-- 律师表，存储律师的额外信息
CREATE TABLE lawyers
(
    id                    BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '律师唯一标识，自增主键',
    law_firm              VARCHAR(200) COMMENT '所在律师事务所名称',
    lawyer_license_number VARCHAR(50) COMMENT '律师执业证号',
    specialization        VARCHAR(255) COMMENT '专业领域',
    is_valid_flag         CHAR(1)   DEFAULT '1' COMMENT '律师是否有效，1 表示有效，0 表示无效，默认为有效',
    insert_time_for_his   TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '律师信息插入时间，默认为当前时间',
    operate_time_for_his  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '律师信息操作时间，自动更新'
);
CREATE INDEX idx_lawyers_insert_time_for_his ON lawyers (insert_time_for_his);
CREATE INDEX idx_lawyers_operate_time_for_his ON lawyers (operate_time_for_his);

-- 律师专长表，用于存储律师的各类专长信息
-- 若需删除 lawyer_specialties 表，可取消此注释
-- DROP TABLE IF EXISTS lawyer_specialties;
CREATE TABLE lawyer_specialties
(
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '专长唯一标识，自增主键',
    specialty_name       VARCHAR(100) NOT NULL UNIQUE COMMENT '专长名称，唯一',
    description          TEXT COMMENT '专长描述',
    insert_time_for_his  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '专长信息插入时间，默认为当前时间',
    operate_time_for_his TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '专长信息操作时间，自动更新'
);
CREATE INDEX idx_lawyer_specialties_insert_time_for_his ON lawyer_specialties (insert_time_for_his);
CREATE INDEX idx_lawyer_specialties_operate_time_for_his ON lawyer_specialties (operate_time_for_his);

-- 律师与专长的关联表，用于建立律师和专长之间的多对多关系
-- 若需删除 lawyer_specialty_relations 表，可取消此注释
-- DROP TABLE IF EXISTS lawyer_specialty_relations;
CREATE TABLE lawyer_specialty_relations
(
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '关联记录唯一标识，自增主键',
    lawyer_id            BIGINT NOT NULL COMMENT '关联的律师 ID',
    specialty_id         BIGINT NOT NULL COMMENT '关联的专长 ID',
    insert_time_for_his  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '关联信息插入时间，默认为当前时间',
    operate_time_for_his TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '关联信息操作时间，自动更新',
    FOREIGN KEY (lawyer_id) REFERENCES lawyers (id) ON DELETE CASCADE,
    FOREIGN KEY (specialty_id) REFERENCES lawyer_specialties (id) ON DELETE CASCADE
);
CREATE INDEX idx_lawyer_specialty_relations_lawyer_id ON lawyer_specialty_relations (lawyer_id);
CREATE INDEX idx_lawyer_specialty_relations_specialty_id ON lawyer_specialty_relations (specialty_id);
CREATE INDEX idx_lawyer_specialty_relations_insert_time_for_his ON lawyer_specialty_relations (insert_time_for_his);
CREATE INDEX idx_lawyer_specialty_relations_operate_time_for_his ON lawyer_specialty_relations (operate_time_for_his);

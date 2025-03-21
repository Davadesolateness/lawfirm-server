-- 若需删除 user 表，可取消此注释
-- DROP TABLE IF EXISTS user;
-- 用户表，存储所有类型用户的通用信息
CREATE TABLE user
(
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户唯一标识，自增主键',
    username             VARCHAR(50)  NOT NULL UNIQUE COMMENT '用户名，唯一且不能为空',
    password             VARCHAR(255) NOT NULL COMMENT '用户密码，需加密存储',
    password_update_time TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '用户密码修改时间，初始为当前时间',
    email                VARCHAR(100) UNIQUE COMMENT '用户邮箱，唯一',
    phone_number         VARCHAR(20) COMMENT '用户电话号码',
    user_type            VARCHAR(50)  NOT NULL COMMENT '用户类型，取值可为：corporate（法人客户）、individual（个人客户）、lawyer（律师）、admin（管理员）',
    source_type          VARCHAR(50) DEFAULT 'manual_register' COMMENT '数据来源类型，取值可为：database_insert（数据库插入）、manual_register（人工系统注册），默认是人工系统注册',
    related_entity_id    BIGINT COMMENT '关联的实体 ID，根据 user_type 确定关联的表：corporate 对应 corporate_clients 表，individual 对应 individual_clients 表，lawyer 对应 lawyers 表，admin 对应 administrators 表',
    is_valid_flag        CHAR(1)     DEFAULT '1' COMMENT '用户是否有效，1 表示有效，0 表示无效，默认为有效',
    insert_time_for_his  TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '用户信息插入时间，默认为当前时间',
    operate_time_for_his TIMESTAMP   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '用户信息操作时间，自动更新'
);
CREATE INDEX idx_user_related_entity_id ON user (related_entity_id);
CREATE INDEX idx_user_insert_time_for_his ON user (insert_time_for_his);
CREATE INDEX idx_user_operate_time_for_his ON user (operate_time_for_his);

-- 若需删除 corporate_clients 表，可取消此注释
-- DROP TABLE IF EXISTS corporate_clients;
-- 法人客户表，存储法人客户的额外信息
CREATE TABLE corporate_clients
(
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '法人客户唯一标识，自增主键',
    company_name         VARCHAR(200) NOT NULL COMMENT '公司名称，不能为空',
    certificate_type     VARCHAR(50) COMMENT '证件类型，如营业执照、组织机构代码证等',
    certificate_number   VARCHAR(50) UNIQUE COMMENT '证件号码，唯一',
    contact_person       VARCHAR(50) COMMENT '联系人姓名',
    is_valid_flag        CHAR(1)   DEFAULT '1' COMMENT '法人客户是否有效，1 表示有效，0 表示无效，默认为有效',
    insert_time_for_his  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '法人客户信息插入时间，默认为当前时间',
    operate_time_for_his TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '法人客户信息操作时间，自动更新'
);
CREATE INDEX idx_corporate_clients_insert_time_for_his ON corporate_clients (insert_time_for_his);
CREATE INDEX idx_corporate_clients_operate_time_for_his ON corporate_clients (operate_time_for_his);

-- 若需删除 individual_clients 表，可取消此注释
-- DROP TABLE IF EXISTS individual_clients;
-- 个人客户表，存储个人客户的额外信息
CREATE TABLE individual_clients
(
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '个人客户唯一标识，自增主键',
    full_name            VARCHAR(100) NOT NULL COMMENT '个人客户全名，不能为空',
    gender               VARCHAR(50) COMMENT '性别，取值可为：male（男）、female（女）、other（其他）',
    birth_date           DATE COMMENT '出生日期',
    is_valid_flag        CHAR(1)   DEFAULT '1' COMMENT '个人客户是否有效，1 表示有效，0 表示无效，默认为有效',
    insert_time_for_his  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '个人客户信息插入时间，默认为当前时间',
    operate_time_for_his TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '个人客户信息操作时间，自动更新'
);
CREATE INDEX idx_individual_clients_insert_time_for_his ON individual_clients (insert_time_for_his);
CREATE INDEX idx_individual_clients_operate_time_for_his ON individual_clients (operate_time_for_his);

-- 若需删除 lawyers 表，可取消此注释
-- DROP TABLE IF EXISTS lawyers;
-- 律师表，存储律师的额外信息
CREATE TABLE lawyers
(
    id                    BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '律师唯一标识，自增主键',
    law_firm              VARCHAR(200) COMMENT '所在律师事务所名称',
    lawyer_license_number VARCHAR(50) UNIQUE COMMENT '律师执业证号，唯一',
    specialization        VARCHAR(255) COMMENT '专业领域',
    is_valid_flag         CHAR(1)   DEFAULT '1' COMMENT '律师是否有效，1 表示有效，0 表示无效，默认为有效',
    insert_time_for_his   TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '律师信息插入时间，默认为当前时间',
    operate_time_for_his  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '律师信息操作时间，自动更新'
);
CREATE INDEX idx_lawyers_insert_time_for_his ON lawyers (insert_time_for_his);
CREATE INDEX idx_lawyers_operate_time_for_his ON lawyers (operate_time_for_his);

-- 若需删除 administrators 表，可取消此注释
-- DROP TABLE IF EXISTS administrators;
-- 管理员表，存储管理员的额外信息
CREATE TABLE administrators
(
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '管理员唯一标识，自增主键',
    admin_role           VARCHAR(50) COMMENT '管理员角色',
    admin_level          INT NOT NULL DEFAULT 1 COMMENT '管理员级别，数字越小级别越高',
    parent_admin_id      BIGINT COMMENT '上级管理员 ID，顶级管理员该字段为 NULL',
    is_valid_flag        CHAR(1)      DEFAULT '1' COMMENT '管理员是否有效，1 表示有效，0 表示无效，默认为有效',
    insert_time_for_his  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP COMMENT '管理员信息插入时间，默认为当前时间',
    operate_time_for_his TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '管理员信息操作时间，自动更新'
);
CREATE INDEX idx_administrators_user_id ON administrators (id);
CREATE INDEX idx_administrators_parent_admin_id ON administrators (parent_admin_id);
CREATE INDEX idx_administrators_insert_time_for_his ON administrators (insert_time_for_his);
CREATE INDEX idx_administrators_operate_time_for_his ON administrators (operate_time_for_his);

-- 若需删除 user 表，可取消此注释
-- DROP TABLE IF EXISTS user;
-- 用户表，存储所有类型用户的通用信息
CREATE TABLE user
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户唯一标识，自增主键',
    openId             VARCHAR(100) COMMENT '开放平台的唯一标识',
    username           VARCHAR(50)  NOT NULL UNIQUE COMMENT '用户名，唯一且不能为空',
    password           VARCHAR(255) NOT NULL COMMENT '用户密码，需加密存储',
    passwordUpdateTime TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '用户密码修改时间，初始为当前时间',
    nickName           VARCHAR(50) COMMENT '用户昵称',
    email              VARCHAR(100) COMMENT '用户邮箱',
    phoneNumber        VARCHAR(20) UNIQUE COMMENT '用户电话号码，唯一',
    userType           VARCHAR(50)  NOT NULL COMMENT '用户类型，取值可为：corporate（法人客户）、individual（个人客户）、lawyer（律师）、admin（管理员）',
    sourceType         VARCHAR(50) DEFAULT 'manual_register' COMMENT '数据来源类型，取值可为：database_insert（数据库插入）、manual_register（人工系统注册），默认是人工系统注册',
    relatedEntityId    BIGINT COMMENT '关联的实体 ID，根据 userType 确定关联的表：corporate 对应 corporateClient 表，individual 对应 individualClient 表，lawyer 对应 lawyer 表，admin 对应 administrator 表',
    createTime         TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '用户创建时间，默认为当前时间',
    isValidFlag        CHAR(1)     DEFAULT '1' COMMENT '用户是否有效，1 表示有效，0 表示无效，默认为有效',
    insertTimeForHis   TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '用户信息插入时间，默认为当前时间',
    operateTimeForHis  TIMESTAMP   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '用户信息操作时间，自动更新'
);
CREATE INDEX idx_user_relatedEntityId ON user (relatedEntityId);
CREATE INDEX idx_user_createTime ON user (createTime);
CREATE INDEX idx_user_insertTimeForHis ON user (insertTimeForHis);
CREATE INDEX idx_user_operateTimeForHis ON user (operateTimeForHis);

-- 若需删除 corporateClient 表，可取消此注释
-- DROP TABLE IF EXISTS corporateClient;
-- 法人客户表，存储法人客户的额外信息
CREATE TABLE corporateClient
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '法人客户唯一标识，自增主键',
    companyName       VARCHAR(200) NOT NULL COMMENT '公司名称，不能为空',
    certificateType   VARCHAR(50) COMMENT '证件类型，如营业执照、组织机构代码证等',
    certificateNumber VARCHAR(50) UNIQUE COMMENT '证件号码，唯一',
    contactPerson     VARCHAR(50) COMMENT '联系人姓名',
    isValidFlag       CHAR(1)   DEFAULT '1' COMMENT '法人客户是否有效，1 表示有效，0 表示无效，默认为有效',
    insertTimeForHis  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '法人客户信息插入时间，默认为当前时间',
    operateTimeForHis TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '法人客户信息操作时间，自动更新'
);
CREATE INDEX idx_corporateClient_insertTimeForHis ON corporateClient (insertTimeForHis);
CREATE INDEX idx_corporateClient_operateTimeForHis ON corporateClient (operateTimeForHis);

-- 若需删除 individualClient 表，可取消此注释
-- DROP TABLE IF EXISTS individualClient;
-- 个人客户表，存储个人客户的额外信息
CREATE TABLE individualClient
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '个人客户唯一标识，自增主键',
    fullName          VARCHAR(100) NOT NULL COMMENT '个人客户全名，不能为空',
    gender            VARCHAR(50) COMMENT '性别，取值可为：male（男）、female（女）、other（其他）',
    birthDate         DATE COMMENT '出生日期',
    isValidFlag       CHAR(1)   DEFAULT '1' COMMENT '个人客户是否有效，1 表示有效，0 表示无效，默认为有效',
    insertTimeForHis  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '个人客户信息插入时间，默认为当前时间',
    operateTimeForHis TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '个人客户信息操作时间，自动更新'
);
CREATE INDEX idx_individualClient_insertTimeForHis ON individualClient (insertTimeForHis);
CREATE INDEX idx_individualClient_operateTimeForHis ON individualClient (operateTimeForHis);

-- 若需删除 administrator 表，可取消此注释
-- DROP TABLE IF EXISTS administrator;
-- 管理员表，存储管理员的额外信息
CREATE TABLE administrator
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '管理员唯一标识，自增主键',
    adminRole         VARCHAR(50) COMMENT '管理员角色',
    adminLevel        INT NOT NULL DEFAULT 1 COMMENT '管理员级别，数字越小级别越高',
    parentAdminId     BIGINT COMMENT '上级管理员 ID，顶级管理员该字段为 NULL',
    isValidFlag       CHAR(1)      DEFAULT '1' COMMENT '管理员是否有效，1 表示有效，0 表示无效，默认为有效',
    insertTimeForHis  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP COMMENT '管理员信息插入时间，默认为当前时间',
    operateTimeForHis TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '管理员信息操作时间，自动更新'
);
CREATE INDEX idx_administrator_id ON administrator (id);
CREATE INDEX idx_administrator_parentAdminId ON administrator (parentAdminId);
CREATE INDEX idx_administrator_insertTimeForHis ON administrator (insertTimeForHis);
CREATE INDEX idx_administrator_operateTimeForHis ON administrator (operateTimeForHis);
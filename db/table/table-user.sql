-- 若需删除 User 表，可取消此注释
-- DROP TABLE IF EXISTS User;
-- 用户表，存储所有类型用户的通用信息
CREATE TABLE User
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户唯一标识，自增主键',
    username           VARCHAR(50)  NOT NULL UNIQUE COMMENT '用户名，唯一且不能为空',
    password           VARCHAR(255) NOT NULL COMMENT '用户密码，需加密存储',
    passwordUpdateTime TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '用户密码修改时间，初始为当前时间',
    email              VARCHAR(100) COMMENT '用户邮箱',
    phoneNumber        VARCHAR(20) UNIQUE COMMENT '用户电话号码，唯一',
    userType           VARCHAR(50)  NOT NULL COMMENT '用户类型，取值可为：corporate（法人客户）、individual（个人客户）、lawyer（律师）、admin（管理员）',
    sourceType         VARCHAR(50) DEFAULT 'manual_register' COMMENT '数据来源类型，取值可为：database_insert（数据库插入）、manual_register（人工系统注册），默认是人工系统注册',
    relatedEntityId    BIGINT COMMENT '关联的实体 ID，根据 userType 确定关联的表：corporate 对应 CorporateClient 表，individual 对应 IndividualClient 表，lawyer 对应 Lawyer 表，admin 对应 Administrator 表',
    isValidFlag        CHAR(1)     DEFAULT '1' COMMENT '用户是否有效，1 表示有效，0 表示无效，默认为有效',
    insertTimeForHis   TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '用户信息插入时间，默认为当前时间',
    operateTimeForHis  TIMESTAMP   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '用户信息操作时间，自动更新'
);
CREATE INDEX idx_User_relatedEntityId ON User (relatedEntityId);
CREATE INDEX idx_User_insertTimeForHis ON User (insertTimeForHis);
CREATE INDEX idx_User_operateTimeForHis ON User (operateTimeForHis);

-- 若需删除 CorporateClient 表，可取消此注释
-- DROP TABLE IF EXISTS CorporateClient;
-- 法人客户表，存储法人客户的额外信息
CREATE TABLE CorporateClient
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
CREATE INDEX idx_CorporateClient_insertTimeForHis ON CorporateClient (insertTimeForHis);
CREATE INDEX idx_CorporateClient_operateTimeForHis ON CorporateClient (operateTimeForHis);

-- 若需删除 IndividualClient 表，可取消此注释
-- DROP TABLE IF EXISTS IndividualClient;
-- 个人客户表，存储个人客户的额外信息
CREATE TABLE IndividualClient
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '个人客户唯一标识，自增主键',
    fullName          VARCHAR(100) NOT NULL COMMENT '个人客户全名，不能为空',
    gender            VARCHAR(50) COMMENT '性别，取值可为：male（男）、female（女）、other（其他）',
    birthDate         DATE COMMENT '出生日期',
    isValidFlag       CHAR(1)   DEFAULT '1' COMMENT '个人客户是否有效，1 表示有效，0 表示无效，默认为有效',
    insertTimeForHis  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '个人客户信息插入时间，默认为当前时间',
    operateTimeForHis TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '个人客户信息操作时间，自动更新'
);
CREATE INDEX idx_IndividualClient_insertTimeForHis ON IndividualClient (insertTimeForHis);
CREATE INDEX idx_IndividualClient_operateTimeForHis ON IndividualClient (operateTimeForHis);

-- 若需删除 Administrator 表，可取消此注释
-- DROP TABLE IF EXISTS Administrator;
-- 管理员表，存储管理员的额外信息
CREATE TABLE Administrator
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '管理员唯一标识，自增主键',
    adminRole         VARCHAR(50) COMMENT '管理员角色',
    adminLevel        INT NOT NULL DEFAULT 1 COMMENT '管理员级别，数字越小级别越高',
    parentAdminId     BIGINT COMMENT '上级管理员 ID，顶级管理员该字段为 NULL',
    isValidFlag       CHAR(1)      DEFAULT '1' COMMENT '管理员是否有效，1 表示有效，0 表示无效，默认为有效',
    insertTimeForHis  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP COMMENT '管理员信息插入时间，默认为当前时间',
    operateTimeForHis TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '管理员信息操作时间，自动更新'
);
CREATE INDEX idx_Administrator_id ON Administrator (id);
CREATE INDEX idx_Administrator_parentAdminId ON Administrator (parentAdminId);
CREATE INDEX idx_Administrator_insertTimeForHis ON Administrator (insertTimeForHis);
CREATE INDEX idx_Administrator_operateTimeForHis ON Administrator (operateTimeForHis);
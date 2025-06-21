-- 若需删除 lawyer 表，可取消此注释
-- DROP TABLE IF EXISTS lawyer;
-- 律师表，存储律师的额外信息
CREATE TABLE lawyer
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '律师唯一标识，自增主键',
    lawyerName          VARCHAR(100) COMMENT '律师名称',
    lawFirm             VARCHAR(200) COMMENT '所在律师事务所名称',
    lawyerLicenseNumber VARCHAR(50) COMMENT '律师执业证号',
    provinceCode        VARCHAR(20) COMMENT '律师所属省级行政区编码',
    cityCode            VARCHAR(20) COMMENT '律师所属地市级行政区编码',
    districtCode        VARCHAR(20) COMMENT '律师所属县区编码',
    lawyerIntroduction  TEXT COMMENT '律师简介',
    lawyerDetails       TEXT COMMENT '律师详情',
    workYears           INT COMMENT '律师职业年限',
    rating              DECIMAL(3, 2) COMMENT '律师评分',
    isValidFlag         CHAR(1)   DEFAULT '1' COMMENT '律师是否有效，1 表示有效，0 表示无效，默认为有效',
    insertTimeForHis    TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '律师信息插入时间，默认为当前时间',
    operateTimeForHis   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '律师信息操作时间，自动更新'
);
-- 创建律师表插入时间的索引，提高按插入时间查询的效率
CREATE INDEX idx_lawyer_insertTimeForHis ON lawyer (insertTimeForHis);
-- 创建律师表操作时间的索引，提高按操作时间查询的效率
CREATE INDEX idx_lawyer_operateTimeForHis ON lawyer (operateTimeForHis);

-- 律师专长表，用于存储律师的各类专长信息
-- 若需删除 lawyerSpecialty 表，可取消此注释
-- DROP TABLE IF EXISTS lawyerSpecialty;
CREATE TABLE lawyerSpecialty
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '专长唯一标识，自增主键',
    specialtyName     VARCHAR(100) NOT NULL UNIQUE COMMENT '专长名称，唯一',
    description       TEXT COMMENT '专长描述',
    insertTimeForHis  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '专长信息插入时间，默认为当前时间',
    operateTimeForHis TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '专长信息操作时间，自动更新'
);
-- 创建律师专长表插入时间的索引，提高按插入时间查询的效率
CREATE INDEX idx_lawyerSpecialty_insertTimeForHis ON lawyerSpecialty (insertTimeForHis);
-- 创建律师专长表操作时间的索引，提高按操作时间查询的效率
CREATE INDEX idx_lawyerSpecialty_operateTimeForHis ON lawyerSpecialty (operateTimeForHis);

-- 律师与专长的关联表，用于建立律师和专长之间的多对多关系
-- 若需删除 lawyerSpecialtyRelation 表，可取消此注释
-- DROP TABLE IF EXISTS lawyerSpecialtyRelation;
CREATE TABLE lawyerSpecialtyRelation
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '关联记录唯一标识，自增主键',
    lawyerId          BIGINT NOT NULL COMMENT '关联的律师 ID',
    specialtyId       BIGINT NOT NULL COMMENT '关联的专长 ID',
    insertTimeForHis  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '关联信息插入时间，默认为当前时间',
    operateTimeForHis TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '关联信息操作时间，自动更新',
    -- 外键约束，当删除 lawyer 表中的记录时，此表中关联的记录会自动删除
    FOREIGN KEY (lawyerId) REFERENCES lawyer (id) ON DELETE CASCADE,
    -- 外键约束，当删除 lawyerSpecialty 表中的记录时，此表中关联的记录会自动删除
    FOREIGN KEY (specialtyId) REFERENCES lawyerSpecialty (id) ON DELETE CASCADE
);
-- 创建律师与专长关联表律师 ID 的索引，提高按律师 ID 查询的效率
CREATE INDEX idx_lawyerSpecialtyRelation_lawyerId ON lawyerSpecialtyRelation (lawyerId);
-- 创建律师与专长关联表专长 ID 的索引，提高按专长 ID 查询的效率
CREATE INDEX idx_lawyerSpecialtyRelation_specialtyId ON lawyerSpecialtyRelation (specialtyId);
-- 创建律师与专长关联表插入时间的索引，提高按插入时间查询的效率
CREATE INDEX idx_lawyerSpecialtyRelation_insertTimeForHis ON lawyerSpecialtyRelation (insertTimeForHis);
-- 创建律师与专长关联表操作时间的索引，提高按操作时间查询的效率
CREATE INDEX idx_lawyerSpecialtyRelation_operateTimeForHis ON lawyerSpecialtyRelation (operateTimeForHis);

-- 若需删除 LawyerConsultation 表，可取消此注释
-- DROP TABLE IF EXISTS LawyerConsultation;
-- 律师免费咨询主表，存储用户咨询请求及律师回复信息
CREATE TABLE LawyerConsultation
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '咨询记录唯一标识，自增主键',
    userId             BIGINT NOT NULL COMMENT '用户唯一标识（如小程序openid、APP用户ID等）',
    consultContent     TEXT NOT NULL COMMENT '用户填写的咨询内容（情况说明+问题）',
    questionType       BIGINT NOT NULL COMMENT '问题类型（关联律师专长表lawyerSpecialty）',
    lawyerId           BIGINT COMMENT '匹配的律师ID（若已分配）',
    lawyerReply        TEXT COMMENT '律师回复内容',
    createTime         TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '咨询发起时间，默认为当前时间',
    replyTime          TIMESTAMP NULL COMMENT '律师回复时间',
    agreeProtocol      CHAR(1) DEFAULT '0' COMMENT '是否同意协议（0：未同意；1：已同意）',
    status             TINYINT DEFAULT 0 COMMENT '咨询状态（0：待回复；1：已回复；2：已关闭；3：已取消）',
    insertTimeForHis   TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记录插入时间，默认为当前时间',
    operateTimeForHis  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录操作时间，自动更新'
);
CREATE INDEX idx_LawyerConsultation_userId ON LawyerConsultation (userId);
CREATE INDEX idx_LawyerConsultation_createTime ON LawyerConsultation (createTime);
CREATE INDEX idx_LawyerConsultation_status ON LawyerConsultation (status);
CREATE INDEX idx_LawyerConsultation_insertTimeForHis ON LawyerConsultation (insertTimeForHis);
CREATE INDEX idx_LawyerConsultation_operateTimeForHis ON LawyerConsultation (operateTimeForHis);

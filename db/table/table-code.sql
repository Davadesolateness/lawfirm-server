-- 行政区划信息表，用于存储行政区划的相关代码和信息
-- 若需删除 codeInfo 表，可取消此注释
-- DROP TABLE IF EXISTS codeInfo;
CREATE TABLE codeInfo (
                          codeCode VARCHAR(10) PRIMARY KEY COMMENT '唯一标识行政区划的代码',
                          codeName VARCHAR(100) NOT NULL COMMENT '行政区划的名称',
                          upperCode VARCHAR(100) COMMENT '上级行政区划的代码',
                          level VARCHAR(20) COMMENT '行政区划的等级，如省级、市级辖区等',
                          codeType VARCHAR(50) DEFAULT 'AreaCode' COMMENT '代码类型，取值固定为AreaCode，表示行政区划代码',
                          insertTimeForHis TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '数据插入到表中的时间，默认为当前时间',
                          operateTimeForHis TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最后一次更新的时间，自动更新'
);

-- 创建行政区划信息表插入时间的索引，提高按插入时间查询的效率
CREATE INDEX idx_insertTimeForHis ON codeInfo (insertTimeForHis);

-- 创建行政区划信息表操作时间的索引，提高按操作时间查询的效率
CREATE INDEX idx_operateTimeForHis ON codeInfo (operateTimeForHis);

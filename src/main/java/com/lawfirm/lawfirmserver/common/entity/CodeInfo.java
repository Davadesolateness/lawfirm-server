package com.lawfirm.lawfirmserver.common.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 系统编码信息实体类
 * 对应数据库表：codeInfo
 * 用于存储省市区等地区编码信息
 */
public class CodeInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识行政区划的代码（主键）
     */
    private String codeCode;

    /**
     * 行政区划的名称
     */
    private String codeName;

    /**
     * 上级行政区划的代码
     */
    private String upperCode;

    /**
     * 行政区划的等级，如省级、市级辖区等
     */
    private String level;

    /**
     * 代码类型，取值固定为AreaCode，表示行政区划代码
     */
    private String codeType;

    /**
     * 数据插入到表中的时间，默认为当前时间
     */
    private Timestamp insertTimeForHis;

    /**
     * 数据最后一次更新的时间，自动更新
     */
    private Timestamp operateTimeForHis;

    public CodeInfo() {
    }

    public String getCodeCode() {
        return codeCode;
    }

    public void setCodeCode(String codeCode) {
        this.codeCode = codeCode;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getUpperCode() {
        return upperCode;
    }

    public void setUpperCode(String upperCode) {
        this.upperCode = upperCode;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public Timestamp getInsertTimeForHis() {
        return insertTimeForHis;
    }

    public void setInsertTimeForHis(Timestamp insertTimeForHis) {
        this.insertTimeForHis = insertTimeForHis;
    }

    public Timestamp getOperateTimeForHis() {
        return operateTimeForHis;
    }

    public void setOperateTimeForHis(Timestamp operateTimeForHis) {
        this.operateTimeForHis = operateTimeForHis;
    }

    @Override
    public String toString() {
        return "CodeInfo{" +
                "codeCode='" + codeCode + '\'' +
                ", codeName='" + codeName + '\'' +
                ", upperCode='" + upperCode + '\'' +
                ", level='" + level + '\'' +
                ", codeType='" + codeType + '\'' +
                ", insertTimeForHis=" + insertTimeForHis +
                ", operateTimeForHis=" + operateTimeForHis +
                '}';
    }
} 
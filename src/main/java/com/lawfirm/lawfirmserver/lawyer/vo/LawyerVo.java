package com.lawfirm.lawfirmserver.lawyer.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 通过ins-framework-mybatis工具自动生成，表lawyer的VO对象<br/>
 * 对应表名：lawyer
 * @author dong
 */
@Data
public class LawyerVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 对应字段：id,备注：律师唯一标识，自增主键
     */
    private Long id;
    /**
     * 对应字段：lawyerName,备注：律师名称
     */
    private String lawyerName;
    /**
     * 对应字段：lawFirm,备注：所在律师事务所名称
     */
    private String lawFirm;
    /**
     * 对应字段：lawyerLicenseNumber,备注：律师执业证号
     */
    private String lawyerLicenseNumber;
    /**
     * 对应字段：provinceCode,备注：律师所属省级行政区编码
     */
    private String provinceCode;
    /**
     * 对应字段：cityCode,备注：律师所属地市级行政区编码
     */
    private String cityCode;
    /**
     * 对应字段：districtCode,备注：律师所属县区编码
     */
    private String districtCode;
    /**
     * 对应字段：lawyerIntroduction,备注：律师简介
     */
    private String lawyerIntroduction;
    /**
     * 对应字段：lawyerDetails,备注：律师详情
     */
    private String lawyerDetails;
    /**
     * 对应字段：workYears,备注：律师职业年限
     */
    private Integer workYears;
    /**
     * 对应字段：rating,备注：律师评分
     */
    private BigDecimal rating;
    /**
     * 对应字段：isValidFlag,备注：律师是否有效，1 表示有效，0 表示无效，默认为有效
     */
    private String isValidFlag;
    /**
     * 对应字段：insertTimeForHis,备注：律师信息插入时间，默认为当前时间
     */
    private Date insertTimeForHis;
    /**
     * 对应字段：operateTimeForHis,备注：律师信息操作时间，自动更新
     */
    private Date operateTimeForHis;

    private List<LawyerSpecialtyRelationVo> lawyerSpecialtyRelationVolist;
    
    /**
     * 律师专长列表（逗号分隔的字符串），由LawyerSpecialtyRelationVo中的specialtyName汇总而来
     */
    private String specialtyNames;
    
    /**
     * 根据关联的专长关系计算并获取逗号分隔的专长名称字符串
     */
    public String getSpecialtyNames() {
        if (specialtyNames != null) {
            return specialtyNames;
        }
        
        if (lawyerSpecialtyRelationVolist == null || lawyerSpecialtyRelationVolist.isEmpty()) {
            return "";
        }
        
        // 提取专长名称列表
        List<String> names = new ArrayList<>();
        for (LawyerSpecialtyRelationVo relation : lawyerSpecialtyRelationVolist) {
            if (relation.getLawyerSpecialtyVo() != null 
                    && relation.getLawyerSpecialtyVo().getSpecialtyName() != null 
                    && !relation.getLawyerSpecialtyVo().getSpecialtyName().isEmpty()) {
                names.add(relation.getLawyerSpecialtyVo().getSpecialtyName());
            }
        }
        
        // 使用逗号连接专长名称
        return String.join(",", names);
    }
}

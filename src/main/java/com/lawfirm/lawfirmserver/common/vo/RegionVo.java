package com.lawfirm.lawfirmserver.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @projectName: lawfirm-server
 * @package: com.lawfirm.lawfirmserver.common.vo
 * @className: RegionVO
 * @author: Eric
 * @description: 区域信息视图对象
 * @date: 2025/6/21 10:15
 * @version: 1.0
 */
@Data
@ApiModel("区域信息")
public class RegionVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("区域代码")
    private String code;

    @ApiModelProperty("区域名称")
    private String name;

    @ApiModelProperty("上级区域代码")
    private String parentCode;

    @ApiModelProperty("区域等级")
    private String level;

    public RegionVo() {
    }

    public RegionVo(String code, String name, String parentCode, String level) {
        this.code = code;
        this.name = name;
        this.parentCode = parentCode;
        this.level = level;
    }
} 
package com.lawfirm.lawfirmserver.lawyer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * @projectName: lawfirm-server
 * @package: com.lawfirm.lawfirmserver.lawyer.vo
 * @className: LawyerSearchParams
 * @author: Eric
 * @description: TODO
 * @date: 2025/5/1 15:44
 * @version: 1.0
 */
@Data
@ApiModel(description = "律师搜索参数")
public class LawyerSearchVo {

    @ApiModelProperty(value = "关键词(律师姓名/简介)", example = "刑事辩护")
    private String keyword;

    @ApiModelProperty(value = "专业领域", example = "刑事案件")
    private String specialty;
    
    @ApiModelProperty(value = "排除的专业领域列表", example = "[\"刑事案件\", \"经济纠纷\"]")
    private List<String> excludeSpecialties;

    @ApiModelProperty(value = "省份编码", example = "330000")
    private String provinceCode;

    @ApiModelProperty(value = "城市编码", example = "330100")
    private String cityCode;

    @ApiModelProperty(value = "区域编码", example = "330102")
    private String districtCode;

    @ApiModelProperty(value = "是否推荐", example = "true")
    private Boolean recommend;

    @Min(value = 1, message = "页码不能小于1")
    @ApiModelProperty(value = "页码", required = true, example = "1")
    private Integer page = 1;

    @Min(value = 1, message = "每页大小不能小于1")
    @ApiModelProperty(value = "每页大小", required = true, example = "10")
    private Integer pageSize = 10;
}

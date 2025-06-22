package com.lawfirm.lawfirmserver.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @projectName: lawfirm-server
 * @package: com.lawfirm.lawfirmserver.common.vo
 * @className: RegionListVO
 * @author: Eric
 * @description: 省市县区域列表视图对象
 * @date: 2025/6/21 10:20
 * @version: 1.0
 */
@Data
@ApiModel("省市县区域列表")
public class RegionListVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("省份列表")
    private List<RegionVo> provinces;

    @ApiModelProperty("市级列表")
    private List<RegionVo> cities;

    @ApiModelProperty("县级列表")
    private List<RegionVo> districts;

    public RegionListVo() {
    }

    public RegionListVo(List<RegionVo> provinces, List<RegionVo> cities, List<RegionVo> districts) {
        this.provinces = provinces;
        this.cities = cities;
        this.districts = districts;
    }
} 
package com.lawfirm.lawfirmserver.common.api;

import com.lawfirm.lawfirmserver.common.Result;
import com.lawfirm.lawfirmserver.common.service.CommonService;
import com.lawfirm.lawfirmserver.common.vo.RegionListVo;
import com.lawfirm.lawfirmserver.common.vo.RegionVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @projectName: lawfirm-server
 * @package: com.lawfirm.lawfirmserver.common.api
 * @className: CommonApi
 * @author: Eric
 * @description: 通用接口控制器，提供省市县等公共数据接口
 * @date: 2025/6/21 10:11
 * @version: 1.0
 */
@RestController
@RequestMapping("/api/common")
@Api(tags = "通用接口")
public class CommonApi {

    @Autowired
    private CommonService commonService;

    /**
     * 获取所有省市县区域信息
     * @return 包含省市县列表的Result对象
     */
    @GetMapping("/regions")
    @ApiOperation("获取所有省市县区域信息")
    public Result<RegionListVo> getAllRegions() {
        try {
            RegionListVo regionListVO = commonService.getAllRegions();
            return Result.success("获取省市县信息成功", regionListVO);
        } catch (Exception e) {
            return Result.fail("获取省市县信息失败：" + e.getMessage());
        }
    }

    /**
     * 获取所有省份列表
     * @return 省份列表
     */
    @GetMapping("/provinces")
    @ApiOperation("获取所有省份列表")
    public Result<List<RegionVo>> getAllProvinces() {
        try {
            List<RegionVo> provinces = commonService.getAllProvinces();
            return Result.success("获取省份列表成功", provinces);
        } catch (Exception e) {
            return Result.fail("获取省份列表失败：" + e.getMessage());
        }
    }

    /**
     * 根据省份代码获取市级区域列表
     * @param provinceCode 省份代码
     * @return 市级区域列表
     */
    @GetMapping("/cities/{provinceCode}")
    @ApiOperation("根据省份代码获取市级区域列表")
    public Result<List<RegionVo>> getCitiesByProvince(
            @ApiParam(value = "省份代码", required = true) @PathVariable String provinceCode) {
        try {
            List<RegionVo> cities = commonService.getCitiesByProvince(provinceCode);
            return Result.success("获取市级区域列表成功", cities);
        } catch (Exception e) {
            return Result.fail("获取市级区域列表失败：" + e.getMessage());
        }
    }

    /**
     * 根据市级代码获取县级区域列表
     * @param cityCode 市级代码
     * @return 县级区域列表
     */
    @GetMapping("/districts/{cityCode}")
    @ApiOperation("根据市级代码获取县级区域列表")
    public Result<List<RegionVo>> getDistrictsByCity(
            @ApiParam(value = "市级代码", required = true) @PathVariable String cityCode) {
        try {
            List<RegionVo> districts = commonService.getDistrictsByCity(cityCode);
            return Result.success("获取县级区域列表成功", districts);
        } catch (Exception e) {
            return Result.fail("获取县级区域列表失败：" + e.getMessage());
        }
    }
}

package com.lawfirm.lawfirmserver.common.service;

import com.lawfirm.lawfirmserver.common.dao.BannerDao;
import com.lawfirm.lawfirmserver.common.dao.CodeInfoDao;
import com.lawfirm.lawfirmserver.common.po.Banner;
import com.lawfirm.lawfirmserver.common.po.CodeInfo;
import com.lawfirm.lawfirmserver.common.vo.BannerVo;
import com.lawfirm.lawfirmserver.common.vo.RegionListVo;
import com.lawfirm.lawfirmserver.common.vo.RegionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @projectName: lawfirm-server
 * @package: com.lawfirm.lawfirmserver.common.service
 * @className: CommonService
 * @author: Eric
 * @description: 通用服务类，提供省市县等公共数据服务
 * @date: 2025/6/21 10:10
 * @version: 1.0
 */
@Service
public class CommonService {

    @Autowired
    private CodeInfoDao codeInfoDao;

    @Autowired
    private BannerDao bannerDao;

    @Value("${image.access.base-url:http://localhost:8080/images}")
    private String imageAccessBaseUrl;

    /**
     * 获取所有省市县区域信息
     *
     * @return 包含省市县列表的RegionListVO对象
     */
    public RegionListVo getAllRegions() {
        // 获取所有区域编码类型为AreaCode的数据
        List<CodeInfo> allRegions = codeInfoDao.selectByCodeType("AreaCode");

        // 按等级分类
        List<RegionVo> provinces = allRegions.stream()
                .filter(region -> "1".equals(region.getLevel()))
                .map(this::convertToRegionVO)
                .collect(Collectors.toList());

        List<RegionVo> cities = allRegions.stream()
                .filter(region -> "2".equals(region.getLevel()))
                .map(this::convertToRegionVO)
                .collect(Collectors.toList());

        List<RegionVo> districts = allRegions.stream()
                .filter(region -> "3".equals(region.getLevel()))
                .map(this::convertToRegionVO)
                .collect(Collectors.toList());

        return new RegionListVo(provinces, cities, districts);
    }

    /**
     * 根据省份代码获取该省份下的市级区域
     *
     * @param provinceCode 省份代码
     * @return 市级区域列表
     */
    public List<RegionVo> getCitiesByProvince(String provinceCode) {
        List<CodeInfo> cities = codeInfoDao.selectByUpperCode(provinceCode);
        return cities.stream()
                .filter(city -> "2".equals(city.getLevel()))
                .map(this::convertToRegionVO)
                .collect(Collectors.toList());
    }

    /**
     * 根据市级代码获取该市下的县级区域
     *
     * @param cityCode 市级代码
     * @return 县级区域列表
     */
    public List<RegionVo> getDistrictsByCity(String cityCode) {
        List<CodeInfo> districts = codeInfoDao.selectByUpperCode(cityCode);
        return districts.stream()
                .filter(district -> "3".equals(district.getLevel()))
                .map(this::convertToRegionVO)
                .collect(Collectors.toList());
    }

    /**
     * 获取所有省份列表
     *
     * @return 省份列表
     */
    public List<RegionVo> getAllProvinces() {
        List<CodeInfo> provinces = codeInfoDao.selectByCodeTypeAndLevel("AreaCode", "1");
        return provinces.stream()
                .map(this::convertToRegionVO)
                .collect(Collectors.toList());
    }

    /**
     * 将CodeInfo实体转换为RegionVO视图对象
     *
     * @param codeInfo CodeInfo实体
     * @return RegionVO视图对象
     */
    private RegionVo convertToRegionVO(CodeInfo codeInfo) {
        return new RegionVo(
                codeInfo.getCodeCode(),
                codeInfo.getCodeName(),
                codeInfo.getUpperCode(),
                codeInfo.getLevel()
        );
    }

    /**
     * 获取所有启用的轮播图列表
     *
     * @return 轮播图列表
     */
    public List<BannerVo> getEnabledBanners() {
        List<Banner> banners = bannerDao.selectEnabledBanners();
        return banners.stream()
                .map(this::convertToBannerVO)
                .collect(Collectors.toList());
    }

    /**
     * 获取指定数量的启用轮播图列表
     *
     * @param limit 限制数量
     * @return 轮播图列表
     */
    public List<BannerVo> getEnabledBannersWithLimit(Integer limit) {
        List<Banner> banners = bannerDao.selectEnabledBannersWithLimit(limit);
        return banners.stream()
                .map(this::convertToBannerVO)
                .collect(Collectors.toList());
    }

    /**
     * 将Banner实体转换为BannerVO视图对象
     *
     * @param banner Banner实体
     * @return BannerVO视图对象
     */
    private BannerVo convertToBannerVO(Banner banner) {
        return new BannerVo(
                banner.getId(),
                banner.getTitle(),
                convertToAbsoluteUrl(banner.getImageUrl()),
                banner.getLinkUrl(),
                banner.getSortOrder(),
                banner.getDescription()
        );
    }

    /**
     * 将相对路径转换为绝对URL
     *
     * @param relativePath 相对路径
     * @return 绝对URL
     */
    private String convertToAbsoluteUrl(String relativePath) {
        if (relativePath == null || relativePath.trim().isEmpty()) {
            return null;
        }
        
        // 如果已经是完整的URL，直接返回
        if (relativePath.startsWith("http://") || relativePath.startsWith("https://")) {
            return relativePath;
        }
        
        // 确保路径以/开头
        String normalizedPath = relativePath.startsWith("/") ? relativePath : "/" + relativePath;
        
        // 拼接基础URL和相对路径
        return imageAccessBaseUrl + normalizedPath;
    }
}

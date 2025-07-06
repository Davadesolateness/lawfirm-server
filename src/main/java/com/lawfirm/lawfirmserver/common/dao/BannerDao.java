package com.lawfirm.lawfirmserver.common.dao;

import com.lawfirm.lawfirmserver.common.po.Banner;
import ins.framework.mybatis.MybatisBaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 轮播图数据访问对象
 * 对应数据库表：banner
 * 用于处理轮播图信息的数据库操作
 * @author dong
 */
@Mapper
public interface BannerDao extends MybatisBaseDao<Banner, Long> {

    /**
     * 获取所有启用的轮播图，按排序序号升序排列
     *
     * @return 轮播图列表
     */
    List<Banner> selectEnabledBanners();

    /**
     * 根据ID查询轮播图信息
     *
     * @param id 轮播图ID
     * @return 轮播图信息对象，如果不存在则返回null
     */
    Banner selectById(@Param("id") Long id);

    /**
     * 获取指定数量的启用轮播图，按排序序号升序排列
     *
     * @param limit 限制数量
     * @return 轮播图列表
     */
    List<Banner> selectEnabledBannersWithLimit(@Param("limit") Integer limit);
} 
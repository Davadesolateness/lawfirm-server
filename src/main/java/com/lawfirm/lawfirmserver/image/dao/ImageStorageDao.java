package com.lawfirm.lawfirmserver.image.dao;

import com.lawfirm.lawfirmserver.image.po.ImageStorage;
import ins.framework.mybatis.MybatisBaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 影像存储数据访问接口
 */
@Mapper
public interface ImageStorageDao extends MybatisBaseDao<ImageStorage, Long> {

    /**
     * 根据用户ID和影像类型查询影像
     *
     * @param userId    用户ID
     * @param imageType 影像类型
     * @return 影像列表
     */
    List<ImageStorage> selectByUserIdAndType(@Param("userId") Long userId, @Param("imageType") String imageType);

    /**
     * 插入影像并返回主键ID
     *
     * @param imageStorage 影像信息
     */
    void insertAndReturnId(ImageStorage imageStorage);

    /**
     * 获取用户最新的头像
     *
     * @param userId 用户ID
     * @return 头像影像信息
     */
    ImageStorage selectLatestAvatarByUserId(@Param("userId") Long userId);
} 
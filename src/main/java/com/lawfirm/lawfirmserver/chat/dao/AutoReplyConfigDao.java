package com.lawfirm.lawfirmserver.chat.dao;

import com.lawfirm.lawfirmserver.chat.po.AutoReplyConfig;
import ins.framework.mybatis.MybatisBaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 表auto_reply_config对应的基于MyBatis实现的Dao接口<br/>
 * 在其中添加自定义方法
 */
@Mapper
public interface AutoReplyConfigDao extends MybatisBaseDao<AutoReplyConfig, Long> {

    /**
     * 查询所有启用的自动回复配置，按优先级排序
     */
    List<AutoReplyConfig> selectActiveConfigs();

    /**
     * 根据分类查询启用的自动回复配置
     */
    List<AutoReplyConfig> selectActiveConfigsByCategory(String category);

    /**
     * 根据关键词精确匹配查询配置
     */
    AutoReplyConfig selectByKeyword(String keyword);
} 
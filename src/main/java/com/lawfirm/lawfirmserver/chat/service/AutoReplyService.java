package com.lawfirm.lawfirmserver.chat.service;

import com.lawfirm.lawfirmserver.chat.dao.AutoReplyConfigDao;
import com.lawfirm.lawfirmserver.chat.po.AutoReplyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 自动回复服务
 */
@Service
public class AutoReplyService {

    @Autowired
    private AutoReplyConfigDao autoReplyConfigDao;

    /**
     * 根据用户消息内容匹配自动回复
     * @param messageContent 用户发送的消息内容
     * @return 匹配到的回复内容，如果没有匹配返回null
     */
    public String getAutoReply(String messageContent) {
        if (messageContent == null || messageContent.trim().isEmpty()) {
            return null;
        }

        // 获取所有启用的自动回复配置，按优先级排序
        List<AutoReplyConfig> configs = autoReplyConfigDao.selectActiveConfigs();
        
        for (AutoReplyConfig config : configs) {
            if (isMatch(messageContent, config)) {
                return config.getReplyContent();
            }
        }
        
        return null;
    }

    /**
     * 判断消息内容是否匹配配置规则
     */
    private boolean isMatch(String messageContent, AutoReplyConfig config) {
        String keyword = config.getKeyword();
        String matchType = config.getMatchType();
        
        if (keyword == null || keyword.trim().isEmpty()) {
            return false;
        }

        switch (matchType.toLowerCase()) {
            case "exact":
                // 精确匹配
                return messageContent.trim().equals(keyword.trim());
            case "contains":
                // 包含匹配
                return messageContent.toLowerCase().contains(keyword.toLowerCase());
            case "regex":
                // 正则匹配
                try {
                    Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
                    return pattern.matcher(messageContent).find();
                } catch (Exception e) {
                    // 正则表达式错误，返回false
                    return false;
                }
            default:
                return false;
        }
    }

    /**
     * 创建自动回复配置
     */
    public AutoReplyConfig createAutoReplyConfig(AutoReplyConfig config) {
        config.setInsertTimeForHis(new Date());
        config.setOperateTimeForHis(new Date());
        autoReplyConfigDao.insert(config);
        return config;
    }

    /**
     * 更新自动回复配置
     */
    public AutoReplyConfig updateAutoReplyConfig(AutoReplyConfig config) {
        config.setOperateTimeForHis(new Date());
        autoReplyConfigDao.updateByPrimaryKey(config);
        return config;
    }

    /**
     * 删除自动回复配置
     */
    public void deleteAutoReplyConfig(Long configId) {
        autoReplyConfigDao.deleteByPrimaryKey(configId);
    }

    /**
     * 获取自动回复配置详情
     */
    public AutoReplyConfig getAutoReplyConfig(Long configId) {
        return autoReplyConfigDao.selectByPrimaryKey(configId);
    }

    /**
     * 获取所有启用的自动回复配置
     */
    public List<AutoReplyConfig> getActiveConfigs() {
        return autoReplyConfigDao.selectActiveConfigs();
    }

    /**
     * 根据分类获取启用的自动回复配置
     */
    public List<AutoReplyConfig> getActiveConfigsByCategory(String category) {
        return autoReplyConfigDao.selectActiveConfigsByCategory(category);
    }
} 
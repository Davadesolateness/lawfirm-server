package com.lawfirm.lawfirmserver.chat.dao;

import com.lawfirm.lawfirmserver.chat.po.ChatMessage;
import ins.framework.mybatis.MybatisBaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 表chatmessage对应的基于MyBatis实现的Dao接口<br/>
 * 在其中添加自定义方法
 */
@Mapper
public interface ChatMessageDao extends MybatisBaseDao<ChatMessage, Long> {

}
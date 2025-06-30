package com.lawfirm.lawfirmserver.chat.dao;

import com.lawfirm.lawfirmserver.chat.po.ChatMessage;
import ins.framework.mybatis.MybatisBaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 表chatmessage对应的基于MyBatis实现的Dao接口<br/>
 * 在其中添加自定义方法
 */
@Mapper
public interface ChatMessageDao extends MybatisBaseDao<ChatMessage, Long> {

    /**
     * 根据会话ID查询消息历史，按发送时间排序
     */
    List<ChatMessage> selectBySessionId(Long sessionId);

    /**
     * 根据会话ID查询未读消息数量
     */
    int countUnreadBySessionId(Long sessionId);
}
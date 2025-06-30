package com.lawfirm.lawfirmserver.chat.dao;

import com.lawfirm.lawfirmserver.chat.po.ChatSession;
import ins.framework.mybatis.MybatisBaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 表chatsession对应的基于MyBatis实现的Dao接口<br/>
 * 在其中添加自定义方法
 */
@Mapper
public interface ChatSessionDao extends MybatisBaseDao<ChatSession, Long> {

    /**
     * 根据创建者ID查询活跃会话
     */
    List<ChatSession> selectActiveSessionsByCreator(Long userId);

    /**
     * 根据参与者ID查询活跃会话
     */
    List<ChatSession> selectActiveSessionsByParticipant(Long userId);
}
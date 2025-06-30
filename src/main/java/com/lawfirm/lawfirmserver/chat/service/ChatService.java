package com.lawfirm.lawfirmserver.chat.service;

import com.lawfirm.lawfirmserver.chat.dao.ChatSessionDao;
import com.lawfirm.lawfirmserver.chat.dao.ChatMessageDao;
import com.lawfirm.lawfirmserver.chat.po.ChatSession;
import com.lawfirm.lawfirmserver.chat.po.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatService {
    
    @Autowired
    private ChatSessionDao chatSessionDao;
    
    @Autowired
    private ChatMessageDao chatMessageDao;

    /**
     * 创建聊天会话
     */
    public ChatSession createChatSession(Long userId, String userType, Long serviceId, String serviceType) {
        ChatSession chatSession = new ChatSession();
        chatSession.setCreatorId(userId);
        chatSession.setCreatorType(userType);
        chatSession.setParticipantId(serviceId);
        chatSession.setParticipantType(serviceType);
        chatSession.setChatType(userType + "-" + serviceType);
        chatSession.setStartTime(new Date());
        chatSession.setStatus("active");
        chatSession.setInsertTimeForHis(new Date());
        chatSession.setOperateTimeForHis(new Date());
        
        chatSessionDao.insert(chatSession);
        return chatSession;
    }

    /**
     * 发送消息
     */
    public ChatMessage sendMessage(Long sessionId, Long senderId, Long receiverId, String messageContent) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSessionId(sessionId);
        chatMessage.setSenderId(senderId);
        chatMessage.setReceiverId(receiverId);
        chatMessage.setMessageContent(messageContent);
        chatMessage.setSendTime(new Date());
        chatMessage.setIsRead(false);
        chatMessage.setInsertTimeForHis(new Date());
        chatMessage.setOperateTimeForHis(new Date());
        
        chatMessageDao.insert(chatMessage);
        return chatMessage;
    }

    /**
     * 获取会话信息
     */
    public ChatSession getChatSession(Long sessionId) {
        return chatSessionDao.selectByPrimaryKey(sessionId);
    }

    /**
     * 获取会话消息历史
     */
    public List<ChatMessage> getChatHistory(Long sessionId) {
        return chatMessageDao.selectBySessionId(sessionId);
    }

    /**
     * 获取用户的活跃会话列表
     */
    public List<ChatSession> getUserActiveSessions(Long userId) {
        // 查询用户作为创建者的会话
        List<ChatSession> creatorSessions = chatSessionDao.selectActiveSessionsByCreator(userId);
        // 查询用户作为参与者的会话
        List<ChatSession> participantSessions = chatSessionDao.selectActiveSessionsByParticipant(userId);
        
        // 合并结果（这里简单返回创建者会话，实际可能需要更复杂的合并逻辑）
        creatorSessions.addAll(participantSessions);
        return creatorSessions;
    }

    /**
     * 获取会话未读消息数量
     */
    public int getUnreadCount(Long sessionId) {
        return chatMessageDao.countUnreadBySessionId(sessionId);
    }

    /**
     * 标记消息为已读
     */
    public void markAsRead(Long messageId) {
        ChatMessage message = chatMessageDao.selectByPrimaryKey(messageId);
        if (message != null) {
            message.setIsRead(true);
            message.setOperateTimeForHis(new Date());
            chatMessageDao.updateByPrimaryKey(message);
        }
    }

    /**
     * 结束会话
     */
    public void endChatSession(Long sessionId) {
        ChatSession session = chatSessionDao.selectByPrimaryKey(sessionId);
        if (session != null) {
            session.setEndTime(new Date());
            session.setStatus("closed");
            session.setOperateTimeForHis(new Date());
            chatSessionDao.updateByPrimaryKey(session);
        }
    }
}

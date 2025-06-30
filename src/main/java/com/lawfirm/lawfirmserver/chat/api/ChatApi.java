package com.lawfirm.lawfirmserver.chat.api;

import com.lawfirm.lawfirmserver.chat.dto.CreateSessionRequest;
import com.lawfirm.lawfirmserver.chat.dto.SendMessageRequest;
import com.lawfirm.lawfirmserver.chat.po.ChatSession;
import com.lawfirm.lawfirmserver.chat.po.ChatMessage;
import com.lawfirm.lawfirmserver.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 聊天相关API接口
 */
@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatApi {
    
    @Autowired
    private ChatService chatService;

    /**
     * 创建聊天会话
     * POST /api/chat/session
     */
    @PostMapping("/session")
    public Map<String, Object> createSession(@RequestBody CreateSessionRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            ChatSession session = chatService.createChatSession(
                request.getUserId(), 
                request.getUserType(), 
                request.getServiceId(), 
                request.getServiceType()
            );
            result.put("success", true);
            result.put("data", session);
            result.put("message", "会话创建成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "会话创建失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 发送消息
     * POST /api/chat/message
     */
    @PostMapping("/message")
    public Map<String, Object> sendMessage(@RequestBody SendMessageRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            ChatMessage message = chatService.sendMessage(
                request.getSessionId(),
                request.getSenderId(),
                request.getReceiverId(),
                request.getMessageContent()
            );
            result.put("success", true);
            result.put("data", message);
            result.put("message", "消息发送成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "消息发送失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 获取会话信息
     * GET /api/chat/session/{sessionId}
     */
    @GetMapping("/session/{sessionId}")
    public Map<String, Object> getSession(@PathVariable Long sessionId) {
        Map<String, Object> result = new HashMap<>();
        try {
            ChatSession session = chatService.getChatSession(sessionId);
            if (session != null) {
                result.put("success", true);
                result.put("data", session);
                result.put("message", "获取会话信息成功");
            } else {
                result.put("success", false);
                result.put("message", "会话不存在");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取会话信息失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 获取会话消息历史
     * GET /api/chat/session/{sessionId}/messages
     */
    @GetMapping("/session/{sessionId}/messages")
    public Map<String, Object> getChatHistory(@PathVariable Long sessionId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<ChatMessage> messages = chatService.getChatHistory(sessionId);
            result.put("success", true);
            result.put("data", messages);
            result.put("message", "获取消息历史成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取消息历史失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 获取用户的活跃会话列表
     * GET /api/chat/user/{userId}/sessions
     */
    @GetMapping("/user/{userId}/sessions")
    public Map<String, Object> getUserSessions(@PathVariable Long userId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<ChatSession> sessions = chatService.getUserActiveSessions(userId);
            result.put("success", true);
            result.put("data", sessions);
            result.put("message", "获取用户会话列表成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取用户会话列表失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 获取会话未读消息数量
     * GET /api/chat/session/{sessionId}/unread-count
     */
    @GetMapping("/session/{sessionId}/unread-count")
    public Map<String, Object> getUnreadCount(@PathVariable Long sessionId) {
        Map<String, Object> result = new HashMap<>();
        try {
            int count = chatService.getUnreadCount(sessionId);
            result.put("success", true);
            result.put("data", count);
            result.put("message", "获取未读消息数量成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取未读消息数量失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 标记消息为已读
     * PUT /api/chat/message/{messageId}/read
     */
    @PutMapping("/message/{messageId}/read")
    public Map<String, Object> markAsRead(@PathVariable Long messageId) {
        Map<String, Object> result = new HashMap<>();
        try {
            chatService.markAsRead(messageId);
            result.put("success", true);
            result.put("message", "消息已标记为已读");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "标记失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 结束会话
     * PUT /api/chat/session/{sessionId}/end
     */
    @PutMapping("/session/{sessionId}/end")
    public Map<String, Object> endSession(@PathVariable Long sessionId) {
        Map<String, Object> result = new HashMap<>();
        try {
            chatService.endChatSession(sessionId);
            result.put("success", true);
            result.put("message", "会话已结束");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "结束会话失败: " + e.getMessage());
        }
        return result;
    }
}

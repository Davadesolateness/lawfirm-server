package com.lawfirm.lawfirmserver.chat.api;

import com.lawfirm.lawfirmserver.chat.dto.AutoReplyConfigRequest;
import com.lawfirm.lawfirmserver.chat.po.AutoReplyConfig;
import com.lawfirm.lawfirmserver.chat.service.AutoReplyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自动回复配置管理API接口
 */
@RestController
@RequestMapping("/api/chat/auto-reply")
@CrossOrigin(origins = "*")
public class AutoReplyApi {

    @Autowired
    private AutoReplyService autoReplyService;

    /**
     * 创建自动回复配置
     * POST /api/chat/auto-reply/config
     */
    @PostMapping("/config")
    public Map<String, Object> createAutoReplyConfig(@RequestBody AutoReplyConfigRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            AutoReplyConfig config = new AutoReplyConfig();
            BeanUtils.copyProperties(request, config);
            
            AutoReplyConfig savedConfig = autoReplyService.createAutoReplyConfig(config);
            result.put("success", true);
            result.put("data", savedConfig);
            result.put("message", "自动回复配置创建成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "自动回复配置创建失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 更新自动回复配置
     * PUT /api/chat/auto-reply/config/{configId}
     */
    @PutMapping("/config/{configId}")
    public Map<String, Object> updateAutoReplyConfig(@PathVariable Long configId, @RequestBody AutoReplyConfigRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            AutoReplyConfig config = autoReplyService.getAutoReplyConfig(configId);
            if (config == null) {
                result.put("success", false);
                result.put("message", "配置不存在");
                return result;
            }

            BeanUtils.copyProperties(request, config);
            config.setConfigId(configId);
            
            AutoReplyConfig updatedConfig = autoReplyService.updateAutoReplyConfig(config);
            result.put("success", true);
            result.put("data", updatedConfig);
            result.put("message", "自动回复配置更新成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "自动回复配置更新失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 删除自动回复配置
     * DELETE /api/chat/auto-reply/config/{configId}
     */
    @DeleteMapping("/config/{configId}")
    public Map<String, Object> deleteAutoReplyConfig(@PathVariable Long configId) {
        Map<String, Object> result = new HashMap<>();
        try {
            autoReplyService.deleteAutoReplyConfig(configId);
            result.put("success", true);
            result.put("message", "自动回复配置删除成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "自动回复配置删除失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 获取自动回复配置详情
     * GET /api/chat/auto-reply/config/{configId}
     */
    @GetMapping("/config/{configId}")
    public Map<String, Object> getAutoReplyConfig(@PathVariable Long configId) {
        Map<String, Object> result = new HashMap<>();
        try {
            AutoReplyConfig config = autoReplyService.getAutoReplyConfig(configId);
            if (config != null) {
                result.put("success", true);
                result.put("data", config);
                result.put("message", "获取配置成功");
            } else {
                result.put("success", false);
                result.put("message", "配置不存在");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取配置失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 获取所有启用的自动回复配置
     * GET /api/chat/auto-reply/configs
     */
    @GetMapping("/configs")
    public Map<String, Object> getActiveConfigs(@RequestParam(required = false) String category) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<AutoReplyConfig> configs;
            if (category != null && !category.trim().isEmpty()) {
                configs = autoReplyService.getActiveConfigsByCategory(category);
            } else {
                configs = autoReplyService.getActiveConfigs();
            }
            
            result.put("success", true);
            result.put("data", configs);
            result.put("message", "获取配置列表成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取配置列表失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 测试自动回复匹配
     * POST /api/chat/auto-reply/test
     */
    @PostMapping("/test")
    public Map<String, Object> testAutoReply(@RequestBody Map<String, String> request) {
        Map<String, Object> result = new HashMap<>();
        try {
            String messageContent = request.get("messageContent");
            if (messageContent == null || messageContent.trim().isEmpty()) {
                result.put("success", false);
                result.put("message", "消息内容不能为空");
                return result;
            }

            String autoReply = autoReplyService.getAutoReply(messageContent);
            
            Map<String, Object> testResult = new HashMap<>();
            testResult.put("messageContent", messageContent);
            testResult.put("autoReply", autoReply != null ? autoReply : "无匹配的自动回复");
            testResult.put("hasMatch", autoReply != null);
            
            result.put("success", true);
            result.put("data", testResult);
            result.put("message", "测试完成");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "测试失败: " + e.getMessage());
        }
        return result;
    }
} 
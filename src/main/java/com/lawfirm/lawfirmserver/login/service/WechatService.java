package com.lawfirm.lawfirmserver.login.service;

import com.lawfirm.lawfirmserver.login.dto.WechatLoginResult;
import com.lawfirm.lawfirmserver.login.dto.WechatUserInfo;
import org.springframework.stereotype.Service;

/**
 * @author dongzhibo
 * @version 1.0
 * @description: 微信相关服务接口
 * @date 2025/4/16 20:46
 */
@Service
public class WechatService {

    /**
     * 小程序登录，通过code获取openId和sessionKey
     *
     * @param code 微信临时登录凭证
     * @return 登录结果
     */
    public WechatLoginResult code2Session(String code) {
        // 实际实现需要调用微信接口，这里模拟返回
        WechatLoginResult result = new WechatLoginResult();
        result.setOpenid("mock_open_id_" + code.substring(code.length() - 5));
        result.setSessionKey("mock_session_key");
        return result;
    }

    /**
     * 解密微信用户信息
     *
     * @param sessionKey    会话密钥
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param iv            加密算法的初始向量
     * @return 解密后的用户信息
     */
    public WechatUserInfo decryptUserInfo(String sessionKey, String encryptedData, String iv) {
        // 实际实现需要解密用户数据，这里模拟返回
        WechatUserInfo userInfo = new WechatUserInfo();
        userInfo.setNickName("微信用户");
        userInfo.setAvatarUrl("");
        userInfo.setGender(0);
        return userInfo;
    }
} 
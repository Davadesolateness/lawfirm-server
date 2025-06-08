package com.lawfirm.lawfirmserver.login.service;

import com.lawfirm.lawfirmserver.login.dto.WechatLoginResult;
import com.lawfirm.lawfirmserver.login.dto.WechatUserInfo;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * @author dongzhibo
 * @version 1.0
 * @description: 微信相关服务接口
 * @date 2025/4/16 20:46
 */
@Service
public class WechatService {

    private static final Logger log = LoggerFactory.getLogger(WechatService.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 小程序登录，通过code获取openId和sessionKey
     *
     * @param code 微信临时登录凭证
     * @return 登录结果
     */
    public WechatLoginResult code2Session(String code) {
        // 实际实现需要调用微信接口，这里模拟返回
        // TODO: 调用微信API - https://api.weixin.qq.com/sns/jscode2session
        WechatLoginResult result = new WechatLoginResult();
        result.setOpenid("mock_open_id_" + code.substring(code.length() - 5));
        result.setSessionKey("mock_session_key_12345678901234567890123456789012");
        log.info("微信登录code2Session完成，openid: {}", result.getOpenid());
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
        try {
            // 实际实现需要解密用户数据
            String decryptedData = decrypt(encryptedData, sessionKey, iv);
            JsonNode jsonNode = objectMapper.readTree(decryptedData);
            
            WechatUserInfo userInfo = new WechatUserInfo();
            userInfo.setNickName(jsonNode.get("nickName").asText());
            userInfo.setAvatarUrl(jsonNode.get("avatarUrl").asText());
            userInfo.setGender(jsonNode.get("gender").asInt());
            userInfo.setCountry(jsonNode.get("country").asText());
            userInfo.setProvince(jsonNode.get("province").asText());
            userInfo.setCity(jsonNode.get("city").asText());
            userInfo.setOpenId(jsonNode.get("openId").asText());
            if (jsonNode.has("unionId")) {
                userInfo.setUnionId(jsonNode.get("unionId").asText());
            }
            
            log.info("微信用户信息解密成功，昵称: {}", userInfo.getNickName());
            return userInfo;
        } catch (Exception e) {
            log.error("解密微信用户信息失败", e);
            // 返回模拟数据
            WechatUserInfo userInfo = new WechatUserInfo();
            userInfo.setNickName("微信用户");
            userInfo.setAvatarUrl("");
            userInfo.setGender(0);
            return userInfo;
        }
    }

    /**
     * 解密微信手机号
     *
     * @param sessionKey    会话密钥
     * @param encryptedData 手机号加密数据
     * @param iv            加密算法的初始向量
     * @return 解密后的手机号，解密失败返回null
     */
    public String decryptPhoneNumber(String sessionKey, String encryptedData, String iv) {
        try {
            String decryptedData = decrypt(encryptedData, sessionKey, iv);
            JsonNode jsonNode = objectMapper.readTree(decryptedData);
            
            String phoneNumber = jsonNode.get("phoneNumber").asText();
            log.info("微信手机号解密成功: {}", phoneNumber.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
            return phoneNumber;
        } catch (Exception e) {
            log.error("解密微信手机号失败", e);
            return null;
        }
    }

    /**
     * AES解密
     *
     * @param encryptedData 加密数据
     * @param sessionKey    会话密钥
     * @param iv            初始向量
     * @return 解密后的数据
     */
    private String decrypt(String encryptedData, String sessionKey, String iv) throws Exception {
        byte[] dataByte = Base64.decodeBase64(encryptedData);
        byte[] keyByte = Base64.decodeBase64(sessionKey);
        byte[] ivByte = Base64.decodeBase64(iv);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivByte);
        cipher.init(Cipher.DECRYPT_MODE, spec, ivSpec);

        byte[] decryptedBytes = cipher.doFinal(dataByte);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
} 
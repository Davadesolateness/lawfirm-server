package com.lawfirm.lawfirmserver.login.service;

import com.lawfirm.lawfirmserver.common.Result;
import com.lawfirm.lawfirmserver.common.util.CommonUtil;
import com.lawfirm.lawfirmserver.common.util.WebUtils;
import com.lawfirm.lawfirmserver.login.dao.LoginLogDao;
import com.lawfirm.lawfirmserver.login.dao.SmsVerificationDao;
import com.lawfirm.lawfirmserver.login.dto.*;
import com.lawfirm.lawfirmserver.login.po.LoginLog;
import com.lawfirm.lawfirmserver.login.vo.LoginVo;
import com.lawfirm.lawfirmserver.security.JwtTokenProvider;
import com.lawfirm.lawfirmserver.user.consts.UserContant;
import com.lawfirm.lawfirmserver.user.dao.UsersDao;
import com.lawfirm.lawfirmserver.user.po.Users;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author dongzhibo
 * @version 1.0
 * @description: 登录相关实现类
 * @date 2025/4/15 21:09
 */
@Service
public class LoginService {

    private static final Logger log = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private UsersDao userDao;

    @Autowired
    private SmsVerificationDao smsCodeMapper;

    @Autowired
    private LoginLogDao loginLogDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private WechatService wechatService;

    /**
     * 验证码登录
     */
    public Result<LoginVo> loginByCode(LoginByCodeDTO dto) {
      /*  // 1. 验证验证码
        SmsCode smsCode = smsCodeMapper.selectLatestByPhoneAndType(dto.getPhone(), 1);
        if (smsCode == null || !smsCode.getCode().equals(dto.getCode())) {
            saveLoginLog(dto.getPhone(), 2, null, "验证码错误");
            return Result.fail("验证码错误");
        }

        if (smsCode.getExpireTime().before(new Date())) {
            saveLoginLog(dto.getPhone(), 2, null, "验证码已过期");
            return Result.fail("验证码已过期");
        }

        // 2. 查询用户
        User user = userMapper.selectByPhone(dto.getPhone());
        if (user == null) {
            saveLoginLog(dto.getPhone(), 2, null, "用户不存在");
            return Result.fail("用户不存在");
        }

        if (user.getStatus() == 0) {
            saveLoginLog(dto.getPhone(), 2, user.getId(), "账号已禁用");
            return Result.fail("账号已禁用");
        }

        // 3. 更新用户登录信息
        updateUserLoginInfo(user, dto.getIp());

        // 4. 生成token
        String token = jwtTokenProvider.generateToken(user.getId().toString());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId().toString());

        // 5. 记录登录日志
        saveLoginLog(dto.getPhone(), 2, user.getId(), "登录成功");

        return Result.success(new LoginVo(token, user));*/
        return null;
    }

    /**
     * 密码登录
     */
    public Result<LoginVo> loginByPassword(LoginByPasswordDTO dto) {
        // 1. 查询用户
        Users user = userDao.selectByPhone(dto.getPhone());
        if (user == null) {
            saveLoginLog(dto.getPhone(), 1, null, "用户不存在");
            return Result.fail("用户不存在");
        }

        if (CommonUtil.equals(user.getIsValidFlag(), "0")) {
            saveLoginLog(dto.getPhone(), 1, user.getId(), "账号已禁用");
            return Result.fail("账号已禁用");
        }
        System.out.println(passwordEncoder.encode(dto.getPassword()));
        // 2. 验证密码
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            saveLoginLog(dto.getPhone(), 1, user.getId(), "密码错误");
            return Result.fail("密码错误");
        }

        // 3. 更新用户登录信息
        // updateUserLoginInfo(user, dto.getIp());

        // 4. 生成token
        String token = jwtTokenProvider.generateToken(user.getId().toString());

        // 5. 记录登录日志
        saveLoginLog(dto.getPhone(), 1, user.getId(), "登录成功");

        return Result.success(new LoginVo(token, user));
    }

    /**
     * 微信登录
     */
    public Result<LoginVo> loginByWechat(WechatLoginDTO dto) {
        try {
            // 1. 通过code获取微信openId和sessionKey
                WechatLoginResult wxResult = wechatService.code2Session(dto.getCode());
            if (wxResult == null || StringUtils.isBlank(wxResult.getOpenid())) {
                saveLoginLog("微信用户", 3, null, "获取微信信息失败");
                return Result.fail("获取微信信息失败");
            }

            String openId = wxResult.getOpenid();
            String sessionKey = wxResult.getSessionKey();
            
            // 2. 获取手机号（直接从DTO中获取）
            String phoneNumber = dto.getPhoneNumber();
            if (StringUtils.isNotBlank(phoneNumber)) {
                // 简单的手机号格式验证
                if (!phoneNumber.matches("^1[3-9]\\d{9}$")) {
                    saveLoginLog("微信用户", 3, null, "手机号格式不正确");
                    return Result.fail("手机号格式不正确");
                }
                log.info("微信登录获取手机号: {}", phoneNumber.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
            }

            // 3. 检查用户是否已存在（优先通过openId查找，其次通过手机号）
            Users user = userDao.selectByOpenId(openId);
            
            // 如果通过openId没找到，但有手机号，则通过手机号查找
            if (user == null && StringUtils.isNotBlank(phoneNumber)) {
                Users existingUser = userDao.selectByPhone(phoneNumber);
                if (existingUser != null) {
                    // 用户存在但没有绑定微信，更新openId
                    existingUser.setOpenId(openId);
                    userDao.updateSelectiveByPrimaryKey(existingUser);
                    user = existingUser;
                    log.info("用户{}绑定微信openId成功", phoneNumber);
                }
            }

            // 4. 如果用户不存在，则创建新用户
            if (user == null) {
                // 解密用户基本信息
                WechatUserInfo userInfo = null;
                if (StringUtils.isNotBlank(dto.getEncryptedData()) && StringUtils.isNotBlank(dto.getIv())) {
                    userInfo = wechatService.decryptUserInfo(sessionKey, dto.getEncryptedData(), dto.getIv());
                }

                // 创建新用户
                user = new Users();
                user.setOpenId(openId);
                
                // 设置用户基本信息
                if (userInfo != null) {
                    user.setNickName(userInfo.getNickName());
                    user.setUsername(StringUtils.isNotBlank(userInfo.getNickName()) ? userInfo.getNickName() : "微信用户");
                } else {
                    // 使用DTO中直接传递的用户信息
                    user.setNickName(StringUtils.isNotBlank(dto.getNickName()) ? dto.getNickName() : "微信用户");
                    user.setUsername(StringUtils.isNotBlank(dto.getNickName()) ? dto.getNickName() : "微信用户");
                }
                
                // 设置手机号
                if (StringUtils.isNotBlank(phoneNumber)) {
                    user.setPhoneNumber(phoneNumber);
                } else {
                    log.warn("微信登录未提供手机号，用户: {}", user.getNickName());
                }
                
                user.setUserType(UserContant.USERTYPE_INDIVIDUAL);
                user.setSourceType("wechat_register");
                user.setIsValidFlag("1");
                user.setCreateTime(new Date());
                user.setInsertTimeForHis(new Date());
                user.setOperateTimeForHis(new Date());
                
                // 插入用户
                try {
                    userDao.insert(user);
                    log.info("微信用户创建成功，用户ID: {}, 昵称: {}, 手机号: {}", 
                            user.getId(), user.getNickName(), 
                            StringUtils.isNotBlank(phoneNumber) ? phoneNumber.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2") : "未提供");
                } catch (Exception e) {
                    log.error("创建微信用户失败", e);
                    saveLoginLog(phoneNumber != null ? phoneNumber : "微信用户", 3, null, "创建用户失败: " + e.getMessage());
                    return Result.fail("创建用户失败，请稍后重试");
                }
            } else {
                // 用户已存在，检查是否被禁用
                if (CommonUtil.equals(user.getIsValidFlag(), "0")) {
                    saveLoginLog(user.getPhoneNumber() != null ? user.getPhoneNumber() : "微信用户", 3, user.getId(), "账号已禁用");
                    return Result.fail("账号已禁用");
                }
                
                // 更新最后操作时间
                user.setOperateTimeForHis(new Date());
                userDao.updateSelectiveByPrimaryKey(user);
                log.info("微信用户登录成功，用户ID: {}, 昵称: {}", user.getId(), user.getNickName());
            }

            // 5. 生成token
            String token = jwtTokenProvider.generateToken(user.getId().toString());
            String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId().toString());

            // 6. 构建登录返回信息
            LoginVo loginVo = new LoginVo();
            loginVo.setUserId(user.getId().toString());
            loginVo.setUserName(user.getUsername());
            loginVo.setUserType(user.getUserType());
            loginVo.setPhone(user.getPhoneNumber());
            loginVo.setRelatedEntityId(user.getRelatedEntityId());
            loginVo.setToken(token);
            loginVo.setRefreshToken(refreshToken);
            loginVo.setTokenExpireTime(jwtTokenProvider.getTokenExpireTime());
            loginVo.setRefreshTokenExpireTime(jwtTokenProvider.getRefreshTokenExpireTime());

            // 7. 记录登录日志
            saveLoginLog(user.getPhoneNumber() != null ? user.getPhoneNumber() : "微信用户", 3, user.getId(), "微信登录成功");
            
            return Result.success(loginVo);
            
        } catch (Exception e) {
            log.error("微信登录异常", e);
            saveLoginLog("微信用户", 3, null, "微信登录异常: " + e.getMessage());
            return Result.fail("微信登录异常: " + e.getMessage());
        }
    }

    /**
     * 用户注册
     */
    public Result<Long> registerUser(RegisterDTO dto) {
        // 1. 检查手机号是否已注册
        Users existUser = userDao.selectByPhone(dto.getPhone());
        if (existUser != null) {
            return Result.fail("手机号已注册");
        }

        /*// 2. 验证验证码
        SmsCode smsCode = smsCodeMapper.selectLatestByPhoneAndType(dto.getPhone(), 2);
        if (smsCode == null || !smsCode.getCode().equals(dto.getCode())) {
            return Result.fail("验证码错误");
        }

        if (smsCode.getExpireTime().before(new Date())) {
            return Result.fail("验证码已过期");
        }*/

        // 2. 创建用户
        Users user = new Users();
        user.setPhoneNumber(dto.getPhone());
        user.setUsername(dto.getUserName() == null ? "用户" + dto.getPhone().substring(7) : dto.getUserName());
        user.setUserType(UserContant.USERTYPE_INDIVIDUAL);
        user.setIsValidFlag("1");
        user.setCreateTime(new Date());

        // 密码加密
        if (StringUtils.isNotBlank(dto.getPassword())) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        userDao.insert(user);

        return Result.success(user.getId());
    }

    /**
     * 发送验证码
     */
    public Result<Void> sendCode(String phone, Integer type) {
        // 1. 检查验证码类型是否有效
        if (type != 1 && type != 2 && type != 3) {
            return Result.fail("验证码类型无效");
        }

        // 2. 如果是注册验证码，检查手机号是否已注册
        if (type == 2) {
            Users user = userDao.selectByPhone(phone);
            if (user != null) {
                return Result.fail("手机号已注册");
            }
        }

        // 3. 生成验证码
        String code = RandomStringUtils.randomNumeric(6);

     /*   // 4. 保存验证码(有效期5分钟)
        SmsCode smsCode = new SmsCode();
        smsCode.setPhone(phone);
        smsCode.setCode(code);
        smsCode.setType(type);
        smsCode.setExpireTime(new Date(System.currentTimeMillis() + 5 * 60 * 1000));
        smsCodeMapper.insert(smsCode);*/

        // 5. 发送短信(这里模拟发送，实际应调用短信服务)
        log.info("发送短信验证码: phone={}, code={}, type={}", phone, code, type);

        return Result.success();
    }

    /**
     * 修改密码
     */
    public Result<Void> resetPassword(ResetPasswordDTO dto) {
        // 1. 查询用户
        Users user = userDao.selectByPhone(dto.getPhone());
        if (user == null) {
            return Result.fail("用户不存在");
        }

        if (CommonUtil.equals(user.getIsValidFlag(), "0")) {
            return Result.fail("账号已禁用");
        }

        /*// 2. 验证旧密码
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            return Result.fail("旧密码错误");
        }*/

        // 3. 更新密码
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userDao.updateSelectiveByPrimaryKey(user);

        return Result.success();
    }

    /**
     * 刷新token
     */
    public Result<LoginVo> refreshToken(String refreshToken) {
        try {
            // 1. 验证refreshToken是否有效
            if (!jwtTokenProvider.validateRefreshToken(refreshToken)) {
                return Result.fail("刷新token已过期，请重新登录");
            }

            // 2. 从refreshToken中获取用户ID
            String userId = jwtTokenProvider.getUserIdFromRefreshToken(refreshToken);
            if (StringUtils.isBlank(userId)) {
                return Result.fail("无效的刷新token");
            }

            // 3. 查询用户
            Users user = userDao.selectById(Long.valueOf(userId));
            if (user == null) {
                return Result.fail("用户不存在");
            }

            if (CommonUtil.equals(user.getIsValidFlag(), "0")) {
                return Result.fail("账号已禁用");
            }

            // 4. 生成新的token
            String newToken = jwtTokenProvider.generateToken(userId);
            String newRefreshToken = jwtTokenProvider.generateRefreshToken(userId);

            LoginVo LoginVo = new LoginVo();
            LoginVo.setUserId(userId);
            LoginVo.setToken(newToken);
            LoginVo.setRefreshToken(newRefreshToken);
            LoginVo.setTokenExpireTime(jwtTokenProvider.getTokenExpireTime());
            LoginVo.setRefreshTokenExpireTime(jwtTokenProvider.getRefreshTokenExpireTime());

            return Result.success(LoginVo);
        } catch (Exception e) {
            log.error("刷新token异常", e);
            return Result.fail("刷新token异常: " + e.getMessage());
        }
    }

    /* private void updateUserLoginInfo(User user, String ip) {
         user.setLastLoginTime(new Date());
         user.setLastLoginIp(ip);
         userDao.updateById(user);
     }
 */
    private void saveLoginLog(String phone, Integer loginType, Long userId, String remark) {
        LoginLog log = new LoginLog();
        log.setPhone(phone);
        log.setLoginType(loginType);
        log.setUserId(userId);
        log.setStatus(userId != null ? 1 : 0);
        log.setIp(WebUtils.getRequestIp());
        log.setDevice(WebUtils.getDeviceInfo());
        log.setRemark(remark);
        log.setCreateTime(new Date());
        loginLogDao.insertSelective(log);
    }
}

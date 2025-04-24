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
        String salt = RandomStringUtils.randomAlphanumeric(20);
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
                //saveLoginLog("微信用户", 3, null, "获取微信信息失败");
                return Result.fail("获取微信信息失败");
            }

            String openId = wxResult.getOpenid();
            String sessionKey = wxResult.getSessionKey();

            // 2. 检查用户是否已存在
            Users user = null;//userDao.selectByOpenId(openId);

            // 3. 如果用户不存在，则创建新用户
            if (user == null) {
                // 解密用户信息，实际实现中需要处理加密数据
                WechatUserInfo userInfo = null;
                if (StringUtils.isNotBlank(dto.getEncryptedData()) && StringUtils.isNotBlank(dto.getIv())) {
                    userInfo = wechatService.decryptUserInfo(sessionKey, dto.getEncryptedData(), dto.getIv());
                }

                // 创建新用户
                user = new Users();
           /*     user.setOpenId(openId);
                user.setUnionId(wxResult.getUnionid());
                user.setNickname(userInfo != null ? userInfo.getNickName() : "微信用户");*/
                //user.setAvatarUrl(userInfo != null ? userInfo.getAvatarUrl() : "");
                /*   user.setGender(userInfo != null ? userInfo.getGender() : 0);*/
                user.setUserType("USER");
                user.setIsValidFlag("1");
                /*user.setCreateTime(new Date());*/
                userDao.insert(user);
            }

            // 4. 生成token
            String token = jwtTokenProvider.generateToken(user.getId().toString());
            String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId().toString());

            // 5. 记录登录日志
            saveLoginLog(user.getPhoneNumber() != null ? user.getPhoneNumber() : "微信用户", 3, user.getId(), "登录成功");
            return Result.success(new LoginVo(token, user));
        } catch (Exception e) {
            log.error("微信登录异常", e);
            return Result.fail("微信登录异常: " + e.getMessage());
        }
    }

    /**
     * 用户注册
     */
    public Result<Long> register(RegisterDTO dto) {
       /* // 1. 检查手机号是否已注册
        User existUser = userMapper.selectByPhone(dto.getPhone());
        if (existUser != null) {
            return Result.fail("手机号已注册");
        }

        // 2. 验证验证码
        SmsCode smsCode = smsCodeMapper.selectLatestByPhoneAndType(dto.getPhone(), 2);
        if (smsCode == null || !smsCode.getCode().equals(dto.getCode())) {
            return Result.fail("验证码错误");
        }

        if (smsCode.getExpireTime().before(new Date())) {
            return Result.fail("验证码已过期");
        }

        // 3. 创建用户
        User user = new User();
        user.setPhone(dto.getPhone());
        user.setNickname(dto.getNickname() == null ? "用户" + dto.getPhone().substring(7) : dto.getNickname());
        user.setUserType("USER");
        user.setStatus(1);
        user.setCreateTime(new Date());

        // 密码加密
        if (StringUtils.isNotBlank(dto.getPassword())) {
            String salt = RandomStringUtils.randomAlphanumeric(20);
            user.setSalt(salt);
            user.setPassword(passwordEncoder.encode(dto.getPassword() + salt));
        }

        userMapper.insert(user);

        return Result.success(user.getId());*/
        return null;
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
        loginLogDao.insertLoginLog(log);
    }
}

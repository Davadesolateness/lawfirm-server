package com.lawfirm.lawfirmserver.login.api;


import com.lawfirm.lawfirmserver.login.service.LoginService;
import com.lawfirm.lawfirmserver.login.vo.LoginVo;
import com.lawfirm.lawfirmserver.login.vo.PhoneVerifyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(tags = "登录接口")
@RestController
@RequestMapping("/api/auth")
public class LoginApi {

    @Resource
    private LoginService loginService;

    @ApiOperation("手机号登录")
    @PostMapping("/phone-login")
    public LoginVo phoneLogin(@RequestBody @Valid PhoneVerifyVO dto) {
        return loginService.phoneLogin(new LoginVo());
    }

    @ApiOperation("账号密码登录")
    @PostMapping("/account-login")
    public LoginVo accountLogin(@RequestBody @Valid LoginVo dto) {
        return loginService.accountLogin(dto);
    }

    /**
     * @description:
     * @author: dongzhibo 
     * @date: 2025/4/13 17:50
     * @param: [dto]
     * @return: Result<LoginVo>
     **/
    @ApiOperation("微信登录")
    @PostMapping("/wechat-login")
    public LoginVo wechatLogin(@RequestBody @Valid LoginVo vo) {
        return loginService.wechatLogin();
    }

    @ApiOperation("发送验证码")
    @PostMapping("/send-code")
    public void sendVerificationCode(@RequestParam String phone) {
        loginService.sendVerificationCode(phone);
    }

    @ApiOperation("验证手机号")
    @PostMapping("/verify-phone")
    public boolean verifyPhone(@RequestParam String phone) {
        return loginService.verifyPhone(phone);
    }

    @ApiOperation("刷新token")
    @PostMapping("/refresh-token")
    public String refreshToken() {
        return loginService.refreshToken();
    }

    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public void logout() {
        loginService.logout();
    }
}

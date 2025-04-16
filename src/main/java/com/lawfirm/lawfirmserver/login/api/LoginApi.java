package com.lawfirm.lawfirmserver.login.api;

import com.lawfirm.lawfirmserver.common.Result;
import com.lawfirm.lawfirmserver.login.dto.LoginByCodeDTO;
import com.lawfirm.lawfirmserver.login.dto.LoginByPasswordDTO;
import com.lawfirm.lawfirmserver.login.dto.RegisterDTO;
import com.lawfirm.lawfirmserver.login.dto.WechatLoginDTO;
import com.lawfirm.lawfirmserver.login.service.LoginService;
import com.lawfirm.lawfirmserver.login.vo.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @description: 登录认证api层
 * @author dongzhibo
 * @date 2025/4/15 21:12
 * @version 1.0
 */
@RestController
@RequestMapping("/auth")
@Api(tags = "认证管理", description = "登录、注册、验证码等接口")
public class LoginApi {

    @Autowired
    private LoginService loginService;

    /**
     * 验证码登录
     */
    @PostMapping("/loginByCode")
    @ApiOperation("验证码登录")
    public Result<LoginVo> loginByCode(@RequestBody @Valid LoginByCodeDTO dto) {
        return loginService.loginByCode(dto);
    }

    /**
     * 密码登录
     */
    @PostMapping("/loginByPassword")
    @ApiOperation("密码登录")
    public Result<LoginVo> loginByPassword(@RequestBody @Valid LoginByPasswordDTO dto) {
        return loginService.loginByPassword(dto);
    }

    /**
     * 微信登录
     */
    @PostMapping("/loginByWechat")
    @ApiOperation("微信登录")
    public Result<LoginVo> loginByWechat(@RequestBody @Valid WechatLoginDTO dto) {
        return loginService.loginByWechat(dto);
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Result<Long> register(@RequestBody @Valid RegisterDTO dto) {
        return loginService.register(dto);
    }

    /**
     * 发送验证码
     */
    @PostMapping("/sendCode")
    @ApiOperation("发送验证码")
    public Result<Void> sendCode(
            @ApiParam(value = "手机号", required = true, example = "13800138000") @RequestParam String phone,
            @ApiParam(value = "验证码类型: 1-登录验证码，2-注册验证码", required = true, example = "1") @RequestParam Integer type) {
        return loginService.sendCode(phone, type);
    }

    /**
     * 刷新token
     */
    @PostMapping("/refreshToken")
    @ApiOperation("刷新token")
    public Result<LoginVo> refreshToken(@RequestParam String refreshToken) {
        return loginService.refreshToken(refreshToken);
    }
}

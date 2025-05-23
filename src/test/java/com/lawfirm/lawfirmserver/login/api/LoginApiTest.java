package com.lawfirm.lawfirmserver.login.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.lawfirmserver.common.Result;
import com.lawfirm.lawfirmserver.login.dto.LoginByCodeDTO;
import com.lawfirm.lawfirmserver.login.dto.LoginByPasswordDTO;
import com.lawfirm.lawfirmserver.login.dto.WechatLoginDTO;
import com.lawfirm.lawfirmserver.login.service.LoginService;
import com.lawfirm.lawfirmserver.login.vo.LoginVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class LoginApiTest {

    private MockMvc mockMvc;

    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginApi loginApi;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(loginApi).build();
    }

    @Test
    @DisplayName("验证码登录 - 成功")
    public void testLoginByCode_Success() throws Exception {
        // 准备测试数据
        LoginByCodeDTO dto = new LoginByCodeDTO();
        dto.setPhone("13800138000");
        dto.setCode("123456");

        LoginVo mockLoginVo = new LoginVo();
        mockLoginVo.setUserId("1");
        mockLoginVo.setToken("mock-token");
        mockLoginVo.setRefreshToken("mock-refresh-token");

        // 模拟service返回
        when(loginService.loginByCode(any(LoginByCodeDTO.class)))
                .thenReturn(Result.success(mockLoginVo));

        // 执行请求并验证
        mockMvc.perform(post("/auth/loginByCode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.userId").value("1"))
                .andExpect(jsonPath("$.data.token").value("mock-token"));
    }

    @Test
    @DisplayName("验证码登录 - 失败（验证码错误）")
    public void testLoginByCode_InvalidCode() throws Exception {
        // 准备测试数据
        LoginByCodeDTO dto = new LoginByCodeDTO();
        dto.setPhone("13800138000");
        dto.setCode("wrong-code");

        // 模拟service返回
        when(loginService.loginByCode(any(LoginByCodeDTO.class)))
                .thenReturn(Result.fail("验证码错误"));

        // 执行请求并验证
        mockMvc.perform(post("/auth/loginByCode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("验证码错误"));
    }

    @Test
    @DisplayName("密码登录 - 成功")
    public void testLoginByPassword_Success() throws Exception {
        // 准备测试数据
        LoginByPasswordDTO dto = new LoginByPasswordDTO();
        dto.setPhone("13800138000");
        dto.setPassword("password123");

        LoginVo mockLoginVo = new LoginVo();
        mockLoginVo.setUserId("1");
        mockLoginVo.setToken("mock-token");
        mockLoginVo.setRefreshToken("mock-refresh-token");

        // 模拟service返回
        when(loginService.loginByPassword(any(LoginByPasswordDTO.class)))
                .thenReturn(Result.success(mockLoginVo));

        // 执行请求并验证
        mockMvc.perform(post("/auth/loginByPassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.userId").value("1"))
                .andExpect(jsonPath("$.data.token").value("mock-token"));
    }

    @Test
    @DisplayName("密码登录 - 失败（密码错误）")
    public void testLoginByPassword_InvalidPassword() throws Exception {
        // 准备测试数据
        LoginByPasswordDTO dto = new LoginByPasswordDTO();
        dto.setPhone("13800138000");
        dto.setPassword("wrong-password");

        // 模拟service返回
        when(loginService.loginByPassword(any(LoginByPasswordDTO.class)))
                .thenReturn(Result.fail("密码错误"));

        // 执行请求并验证
        mockMvc.perform(post("/auth/loginByPassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("密码错误"));
    }

    @Test
    @DisplayName("微信登录 - 成功")
    public void testLoginByWechat_Success() throws Exception {
        // 准备测试数据
        WechatLoginDTO dto = new WechatLoginDTO();
        dto.setCode("mock-wechat-code");

        LoginVo mockLoginVo = new LoginVo();
        mockLoginVo.setUserId("1");
        mockLoginVo.setToken("mock-token");
        mockLoginVo.setRefreshToken("mock-refresh-token");

        // 模拟service返回
        when(loginService.loginByWechat(any(WechatLoginDTO.class)))
                .thenReturn(Result.success(mockLoginVo));

        // 执行请求并验证
        mockMvc.perform(post("/auth/loginByWechat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.userId").value("1"))
                .andExpect(jsonPath("$.data.token").value("mock-token"));
    }

    @Test
    @DisplayName("发送验证码 - 成功")
    public void testSendCode_Success() throws Exception {
        // 模拟service返回
        when(loginService.sendCode(anyString(), anyInt()))
                .thenReturn(Result.success());

        // 执行请求并验证
        mockMvc.perform(post("/auth/sendCode")
                        .param("phone", "13800138000")
                        .param("type", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    @DisplayName("刷新Token - 成功")
    public void testRefreshToken_Success() throws Exception {
        // 准备测试数据
        LoginVo mockLoginVo = new LoginVo();
        mockLoginVo.setUserId("1");
        mockLoginVo.setToken("new-mock-token");
        mockLoginVo.setRefreshToken("new-mock-refresh-token");

        // 模拟service返回
        when(loginService.refreshToken(anyString()))
                .thenReturn(Result.success(mockLoginVo));

        // 执行请求并验证
        mockMvc.perform(post("/auth/refreshToken")
                        .param("refreshToken", "old-refresh-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.token").value("new-mock-token"))
                .andExpect(jsonPath("$.data.refreshToken").value("new-mock-refresh-token"));
    }
}
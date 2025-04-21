# JWT认证系统使用说明

## 1. 简介

JWT（JSON Web Token）是一种基于JSON的开放标准（RFC 7519），用于在网络应用环境间传递声明。本项目实现了基于JWT的认证系统，支持以下功能：

- 用户名密码登录
- 手机验证码登录
- 微信登录
- 用户注册
- Token刷新
- 登录日志记录

## 2. 系统组件

系统主要包含以下组件：

1. **JwtTokenProvider**：负责生成、验证和解析JWT令牌
2. **JwtAuthenticationFilter**：Spring Security过滤器，用于拦截请求并验证JWT令牌
3. **SecurityConfig**：Spring Security配置类
4. **WebUtils**：Web工具类，用于获取请求IP和设备信息
5. **LoginLog**：登录日志实体类
6. **SmsCode**：短信验证码实体类

## 3. 数据库设计

相关表结构在 `db/schema.sql` 文件中，主要包含：

- `user`：用户表
- `login_log`：登录日志表
- `sms_code`：短信验证码表
- `token_blacklist`：黑名单Token表（用于主动注销登录）

## 4. 配置说明

### 4.1 JWT配置（application.yml）

```yaml
security:
  jwt:
    token:
      secret-key: ${JWT_SECRET:your_secret_key_here_at_least_32_chars}
      expire-length: ${JWT_EXPIRE:86400000}  # 24小时
      refresh-expire-length: ${JWT_REFRESH_EXPIRE:2592000000}  # 30天
```

- `secret-key`：JWT签名密钥，建议使用环境变量注入
- `expire-length`：访问令牌有效期（毫秒）
- `refresh-expire-length`：刷新令牌有效期（毫秒）

### 4.2 数据源配置

项目默认使用HikariCP连接池，配置详见`application.yml`。如需使用多数据源，请参考`application-multi-datasource.yml`
文件并修改`DataSourceConfig`类。如需使用Druid连接池，请参考`application-druid.yml`文件。

## 5. API接口说明

### 5.1 登录认证接口

1. **密码登录**

   ```
   POST /api/auth/loginByPassword
   
   请求体：
   {
     "phone": "13800138000",
     "password": "password123"
   }
   
   响应：
   {
     "code": 200,
     "message": "操作成功",
     "data": {
       "userId": "1",
       "userName": "张三",
       "token": "eyJhbGciOiJIUzI1NiJ9...",
       "refreshToken": "eyJhbGciOiJIUzI1NiJ9...",
       "tokenExpireTime": 1682912345678,
       "refreshTokenExpireTime": 1685504345678
     }
   }
   ```

2. **验证码登录**

   ```
   POST /api/auth/loginByCode
   
   请求体：
   {
     "phone": "13800138000",
     "code": "123456"
   }
   
   响应：同密码登录
   ```

3. **微信登录**

   ```
   POST /api/auth/loginByWechat
   
   请求体：
   {
     "code": "0319QrGa1R0Pmj0rp3Ga1YrsxW49QrGP",
     "encryptedData": "加密数据",
     "iv": "初始向量"
   }
   
   响应：同密码登录
   ```

4. **用户注册**

   ```
   POST /api/auth/register
   
   请求体：
   {
     "phone": "13800138000",
     "code": "123456",
     "password": "password123",
     "nickname": "张三"
   }
   
   响应：
   {
     "code": 200,
     "message": "操作成功",
     "data": 1  // 用户ID
   }
   ```

5. **发送验证码**

   ```
   POST /api/auth/sendCode?phone=13800138000&type=1
   
   参数说明：
   - phone: 手机号
   - type: 验证码类型，1-登录验证码，2-注册验证码，3-重置密码验证码
   
   响应：
   {
     "code": 200,
     "message": "操作成功",
     "data": null
   }
   ```

6. **刷新令牌**

   ```
   POST /api/auth/refreshToken?refreshToken=eyJhbGciOiJIUzI1NiJ9...
   
   响应：
   {
     "code": 200,
     "message": "操作成功",
     "data": {
       "userId": "1",
       "token": "eyJhbGciOiJIUzI1NiJ9...",
       "refreshToken": "eyJhbGciOiJIUzI1NiJ9...",
       "tokenExpireTime": 1682912345678,
       "refreshTokenExpireTime": 1685504345678
     }
   }
   ```

## 6. 使用方法

1. 用户登录后，前端需要保存返回的`token`和`refreshToken`
2. 后续请求需要在HTTP请求头中加入`Authorization: Bearer {token}`
3. 当token过期时，使用refreshToken调用刷新接口获取新的token
4. refreshToken过期后，用户需要重新登录

## 7. 安全建议

1. 生产环境应通过环境变量设置JWT签名密钥，避免硬编码
2. 使用HTTPS传输JWT令牌，防止中间人攻击
3. 定期清理过期的Token黑名单记录
4. 登录失败次数限制，防止暴力破解
5. 验证码有效期和尝试次数限制 
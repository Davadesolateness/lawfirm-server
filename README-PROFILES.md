# 多环境配置说明

## 1. 环境配置概述

系统支持以下环境配置：

- **开发环境 (dev)**: 用于本地开发和调试
- **测试环境 (test)**: 用于功能测试和集成测试
- **生产环境 (prod)**: 用于正式生产环境部署
- **生产集群环境 (prod-cluster)**: 用于高可用的生产环境，支持读写分离和集群

## 2. 配置文件说明

系统使用Spring Profiles机制管理不同环境的配置：

- **application.yml**: 基础配置，包含所有环境共享的配置
- **application-dev.yml**: 开发环境特定配置
- **application-test.yml**: 测试环境特定配置
- **application-prod.yml**: 生产环境特定配置
- **application-prod-cluster.yml**: 生产集群环境特定配置

## 3. 数据源配置

### 3.1 开发环境 (dev)

开发环境使用本地数据库：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:5001/lawfirm_dev
    username: root
    password: root
    hikari:
      minimum-idle: 5
      maximum-pool-size: 15
```

### 3.2 测试环境 (test)

测试环境使用独立的测试数据库：

```yaml
spring:
  datasource:
    url: jdbc:mysql://192.168.1.100:3306/lawfirm_test
    username: test_user
    password: test_password
    hikari:
      minimum-idle: 10
      maximum-pool-size: 20
```

### 3.3 生产环境 (prod)

生产环境使用生产数据库，并启用安全配置：

```yaml
spring:
  datasource:
    url: jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/${MYSQL_DATABASE}?useSSL=true&requireSSL=true
    username: ${MYSQL_USERNAME}
    password: ${MYSQL_PASSWORD}
    hikari:
      minimum-idle: 20
      maximum-pool-size: 50
```

### 3.4 生产集群环境 (prod-cluster)

生产集群环境使用主从数据库，实现读写分离：

```yaml
spring:
  shardingsphere:
    datasource:
      names: master,slave1,slave2
      master:
        url: jdbc:mysql://${MYSQL_MASTER_HOST}:${MYSQL_MASTER_PORT}/${MYSQL_DATABASE}
        # 其他配置...
      slave1:
        url: jdbc:mysql://${MYSQL_SLAVE1_HOST}:${MYSQL_SLAVE1_PORT}/${MYSQL_DATABASE}
        # 其他配置...
      slave2:
        url: jdbc:mysql://${MYSQL_SLAVE2_HOST}:${MYSQL_SLAVE2_PORT}/${MYSQL_DATABASE}
        # 其他配置...
    masterslave:
      load-balance-algorithm-type: round_robin
      name: ms
      master-data-source-name: master
      slave-data-source-names: slave1,slave2
```

## 4. 激活指定环境

### 4.1 使用命令行参数

启动应用时，可以使用命令行参数指定激活的环境：

```bash
# 使用Maven启动
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# 使用Java启动
java -jar target/lawfirm-server.jar --spring.profiles.active=prod
```

### 4.2 使用环境变量

可以通过设置环境变量 `SPRING_PROFILES_ACTIVE` 来指定激活的环境：

```bash
export SPRING_PROFILES_ACTIVE=test
java -jar target/lawfirm-server.jar
```

### 4.3 使用激活脚本

系统提供了 `activate-profile.sh` 脚本，可以快速激活指定环境：

```bash
# 激活开发环境
./activate-profile.sh dev

# 激活测试环境
./activate-profile.sh test

# 激活生产环境
./activate-profile.sh prod

# 激活生产集群环境
./activate-profile.sh prod-cluster
```

## 5. 特定环境配置 - 生产环境

对于生产环境，建议使用环境变量或外部配置文件设置敏感信息，可以创建 `.env.prod` 文件来管理这些环境变量：

```properties
# 数据库配置
MYSQL_HOST=prod-db.example.com
MYSQL_PORT=3306
MYSQL_DATABASE=lawfirm_prod
MYSQL_USERNAME=prod_user
MYSQL_PASSWORD=secure_password_here

# Redis配置
REDIS_HOST=redis.example.com
REDIS_PORT=6379
REDIS_PASSWORD=redis_secure_password

# JWT配置
JWT_SECRET=your_production_jwt_secret_key_here

# 服务器配置
SERVER_PORT=8080
LOG_PATH=/var/log/lawfirm
```

## 6. 环境变量说明

系统中使用的主要环境变量：

| 环境变量                  | 描述                  | 默认值                          | 使用环境              |
|-------------------------|-----------------------|--------------------------------|---------------------|
| SPRING_PROFILES_ACTIVE  | 激活的环境配置            | dev                             | 所有                 |
| MYSQL_HOST              | 数据库主机              | localhost                       | 所有                 |
| MYSQL_PORT              | 数据库端口              | 5001 (dev), 3306 (其他)          | 所有                 |
| MYSQL_DATABASE          | 数据库名称              | lawfirm_dev/lawfirm_test/lawfirm | 所有                 |
| MYSQL_USERNAME          | 数据库用户名            | root                            | 所有                 |
| MYSQL_PASSWORD          | 数据库密码              | root                            | 所有                 |
| JWT_SECRET              | JWT签名密钥            | dev/test环境有默认值，生产环境必须设置    | 所有                 |
| REDIS_HOST              | Redis主机             | localhost                       | prod, prod-cluster  |
| REDIS_PORT              | Redis端口             | 6379                            | prod, prod-cluster  |
| REDIS_PASSWORD          | Redis密码             | 无默认值                         | prod, prod-cluster  |
| SERVER_PORT             | 服务器端口             | 8080                            | 所有                 |
| LOG_PATH                | 日志路径              | logs/ (dev/test), /var/log/lawfirm (prod) | 所有        | 
# ShopLook
多元化高复用性登陆注册实现
# ShopLook 2025 - 用户认证系统

## 项目概述
ShopLook 2025 是一个基于Spring Boot微服务架构的电商平台项目。本项目采用模块化设计，包含用户认证、商品管理、品牌管理、分类管理等多个功能模块。

## 本次提交内容说明

**⚠️ 重要说明：本次提交仅包含登录注册功能模块，其他功能模块的详细实现暂未提交。**

### 负责模块：用户认证系统 (loginRegister-api)

本人负责实现完整的用户登录注册功能，包括以下核心特性：

## 🔐 登录注册功能特性

### 1. 多种登录方式支持
- **密码登录**：支持手机号、邮箱、QQ、微信账号密码登录
- **短信验证码登录**：手机号 + 验证码快速登录
- **图形验证码**：防止恶意登录攻击

### 2. 多种注册方式支持
- **邮箱注册**：邮箱 + 验证码注册
- **手机号注册**：手机号 + 短信验证码注册

### 3. 安全特性
- **密码加密**：使用StrongPasswordEncryptor进行密码加密存储
- **JWT令牌**：基于JWT的身份认证，30分钟有效期
- **验证码机制**：支持邮箱验证码和短信验证码
- **防重复提交**：Redis缓存防止验证码重复发送
- **IP记录**：记录用户登录IP和最后登录时间

### 4. 技术实现亮点

#### 核心API接口
- `POST /api/v1/members/login` - 密码登录
- `POST /api/v1/members/phone/login` - 短信验证码登录
- `POST /api/v1/members/register` - 邮箱注册
- `POST /api/v1/members/phone/register` - 手机号注册
- `GET /api/v1/members/captcha` - 获取图形验证码
- `GET /api/v1/members/send-code` - 发送邮箱验证码
- `GET /api/v1/members/code` - 发送短信验证码

#### 技术栈
- **框架**：Spring Boot 3.x
- **数据库**：MySQL + MyBatis
- **缓存**：Redis (验证码存储、会话管理)
- **消息服务**：Spring Mail (邮箱验证码)
- **短信服务**：阿里云市场短信API
- **安全**：JWT + StrongPasswordEncryptor
- **服务发现**：Nacos
- **验证码**：EasyCaptcha

#### 核心组件
- `MemberLoginRegisterApi` - 登录注册控制器
- `JwtUtils` - JWT工具类
- `CodeService` - 验证码服务
- `EmailService` - 邮件服务
- `SmsSender` - 短信发送器
- `MemberServiceImpl` - 用户服务实现

## 🏗️ 项目架构

## 🚀 快速开始

### 环境要求
- JDK 17+
- MySQL 8.0+
- Redis 6.0+
- Nacos 2.0+

### 配置说明
1. 修改 `application.yml` 中的数据库连接信息
2. 配置Nacos服务发现地址
3. 配置邮件服务参数（QQ邮箱）
4. 配置阿里云短信服务AppCode

### 启动服务
```bash
# 启动登录注册服务
cd shoplook2025-parent/shoplook2025-api/shoplook2025-loginRegister-api
mvn spring-boot:run
```

## �� API使用示例

### 邮箱注册
```bash
# 1. 发送验证码
GET /api/v1/members/send-code?email=user@example.com

# 2. 注册
POST /api/v1/members/register
{
  "email": "user@example.com",
  "password": "123456",
  "code": "123456"
}
```

### 手机号注册
```bash
# 1. 发送短信验证码
GET /api/v1/members/code?phone=13800138000

# 2. 注册
POST /api/v1/members/phone/register?code=123456
{
  "phone": "13800138000",
  "password": "123456"
}
```

### 密码登录
```bash
# 1. 获取图形验证码
GET /api/v1/members/captcha?id=uuid

# 2. 登录
POST /api/v1/members/login
{
  "phone": "13800138000",  // 或 email, qq, wechat
  "password": "123456",
  "captcha": "abcd",
  "captchaId": "uuid"
}
```

### 短信验证码登录
```bash
# 1. 发送验证码
GET /api/v1/members/code?phone=13800138000

# 2. 登录
POST /api/v1/members/phone/login?phone=13800138000&code=123456
```

## 🔧 开发说明

### 代码结构

### 关键设计模式
- **策略模式**：多种登录方式的统一处理
- **模板方法**：验证码发送的统一流程
- **工厂模式**：JWT令牌的创建
- **代理模式**：Feign客户端调用

## ⚠️ 注意事项

1. **本次提交范围**：仅包含登录注册功能模块的完整实现
2. **其他模块**：商品、品牌、分类等模块的具体实现细节暂未提交
3. **配置信息**：生产环境配置信息已脱敏处理
4. **依赖服务**：需要确保Redis、MySQL、Nacos等服务正常运行

## �� 联系方式

如有问题或需要其他模块的详细实现，请联系开发团队。

---

**开发时间**：2025年1月  
**技术栈**：Spring Boot 3.x + MySQL + Redis + Nacos + JWT  
**负责模块**：用户认证系统 (登录注册)

package com.situ.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@TableName("user")
@Getter
@Setter
public class User {

  private long id;                    // 用户唯一标识符
  private String username;            // 用户名
  private String password;            // 密码
  private String avatarUrl;           // 头像URL地址
  private String enabled;             // 账户是否启用状态
  private long status;                // 用户状态码
  private String locked;              // 账户是否锁定
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime userExpireTime;      // 用户过期时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime credentialExpireTime; // 凭证过期时间
  private long loginTimes;            // 登录次数统计
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime lastLoginTime;       // 最后登录时间
  private String lastLoginIp;         // 最后登录IP地址
  private String description;         // 用户描述信息
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdTime;         // 创建时间
  private String createdBy;           // 创建人
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updatedTime;         // 更新时间
  private String updatedBy;           // 更新人
  private long opMode;                // 操作模式

}


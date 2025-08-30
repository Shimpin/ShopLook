package com.shoplook2025.member.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@TableName("member")
@Getter
@Setter
public class Member {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private long id;

    /**
     * 验证码（非数据库字段）
     */
    @TableField(exist = false)
    private String captcha;
    @TableField(exist = false)
    private String captchaId;

    // 基本账户信息
    private String account;           // 账户名
    private String password;          // 密码

    // 姓名相关
    private String name;              // 姓名
    private String pinyin;            // 姓名拼音
    private String pinyinUntoned;     // 无音调拼音
    private String firstName;         // 名
    private String lastName;          // 姓

    // 联系方式
    private String qq;                // QQ号码
    private String wechat;            // 微信
    private String phone;             // 手机号
    private String email;             // 邮箱

    // 登录信息
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginTime; // 最后登录时间
    private String lastLoginIp;       // 最后登录IP

    // 扩展信息
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;   // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedTime;

    private String createdBy;
    private String updatedBy;

}
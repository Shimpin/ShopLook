package com.situ.shoplook2025.login.api.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.shoplook2025.member.model.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper extends BaseMapper<Member> {
    default Member findByPhone(String phone) {
        LambdaQueryWrapper<Member> qw = Wrappers.lambdaQuery();
        qw.eq(Member::getPhone, phone);
        return selectOne(qw);
    }
    default Member findByEmail(String email) {
        LambdaQueryWrapper<Member> qw = Wrappers.lambdaQuery();
        qw.eq(Member::getEmail, email);
        return selectOne(qw);
    }

    default Member findByWechat(String wechat) {
        LambdaQueryWrapper<Member> qw = Wrappers.lambdaQuery();
        qw.eq(Member::getWechat, wechat);
        return selectOne(qw);
    }

    default Member findByQq(String qq) {
        LambdaQueryWrapper<Member> qw = Wrappers.lambdaQuery();
        qw.eq(Member::getQq, qq);
        return selectOne(qw);
    }
}

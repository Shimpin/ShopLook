package com.shoplook2025.member.service;

import com.shoplook2025.member.model.Member;

public interface MemberService {
    Member findByPhone(String phone);
    Member findByEmail(String email);
    Member findByWechat(String wechat);
    Member findByQq(String qq);
    boolean save(Member member);
}

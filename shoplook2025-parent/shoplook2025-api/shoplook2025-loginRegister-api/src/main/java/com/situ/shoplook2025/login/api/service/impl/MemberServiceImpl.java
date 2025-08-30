package com.situ.shoplook2025.login.api.service.impl;

import com.shoplook2025.member.model.Member;
import com.shoplook2025.member.service.MemberService;
import com.situ.shoplook2025.core.utils.JsonResult;
import com.situ.shoplook2025.login.api.dao.MemberMapper;
import com.situ.shoplook2025.login.api.service.MemberFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    private MemberMapper memberMapper;
    private MemberFeignService memberFeignService;

    @Autowired
    public void setMemberFeignService(MemberFeignService memberFeignService) {
        this.memberFeignService = memberFeignService;
    }

    @Autowired
    public void setMemberMapper(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public Member findByPhone(String phone) {
        return memberMapper.findByPhone(phone);
    }

    @Override
    public Member findByEmail(String email) {
        return memberMapper.findByEmail(email);
    }

    @Override
    public Member findByWechat(String wechat) {
        return memberMapper.findByWechat(wechat);
    }

    @Override
    public Member findByQq(String qq) {
        return memberMapper.findByQq(qq);
    }

    @Override
    public boolean save(Member member) {
        try {
            // 通过Feign客户端调用member服务的新增接口
            ResponseEntity<JsonResult<Member>> response = memberFeignService.saveMember(member);

            // 检查响应结果
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JsonResult<Member> result = response.getBody();
                return result.isSuccess();
            }
            return false;
        } catch (Exception e) {
            // 记录异常日志
            e.printStackTrace();
            return false;
        }
    }
}

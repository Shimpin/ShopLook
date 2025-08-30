package com.situ.shoplook2025.login.api.service;

import com.shoplook2025.member.model.Member;
import com.situ.shoplook2025.core.utils.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(value = "shoplook-gateway",contextId = "login-api")
public interface MemberFeignService {
    @GetMapping("/member-api/api/v1/members/phone/{phone}")
    Member findByPhone(@PathVariable String phone);

    @GetMapping("/member-api/api/v1/members/email/{email}")
    Member findByEmail(@PathVariable String email);

    @GetMapping("/member-api/api/v1/members/wechat/{wechat}")
    Member findByWechat(@PathVariable String wechat);

    @GetMapping("/member-api/api/v1/members/qq/{qq}")
    Member findByQq(@PathVariable String qq);

    @PostMapping("/member-api/api/v1/members")
    ResponseEntity<JsonResult<Member>> saveMember(Member member);

    // 添加更新用户登录信息的方法
    @PutMapping("/member-api/api/v1/members")
    ResponseEntity<JsonResult<Member>> updateMemberLoginInfo(Member member);

}


package com.situ.shoplook2025.user.api.api;


import com.shoplook2025.member.model.Member;
import com.shoplook2025.member.service.MemberService;
import com.situ.shoplook2025.core.utils.JsonResult;
import com.situ.shoplook2025.login.api.service.MemberFeignService;
import com.situ.shoplook2025.login.api.utils.JwtUtils;
import com.wf.captcha.SpecCaptcha;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/members",produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberLoginRegisterApi {
    private RedisTemplate<Object, Object> redisTemplate;
    private MemberFeignService memberFeignService;
    private MemberService memberService;
    private static final StrongPasswordEncryptor PE = new StrongPasswordEncryptor();
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Autowired
    public void setMemberService(MemberFeignService memberFeignService) {
        this.memberFeignService = memberFeignService;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/captcha")
    public void captcha(String id,HttpServletRequest req, HttpServletResponse resp) throws IOException {
        SpecCaptcha captcha = new SpecCaptcha(140, 40, 4);
        resp.setContentType("image/gif");
        resp.setHeader("Pragma", "No-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);

//        //req.getSession().setAttribute("captcha", captcha.text().toLowerCase());
        redisTemplate.opsForValue().set("captcha-"+id,
                captcha.text().toLowerCase(), Duration.ofMinutes(3));

        captcha.out(resp.getOutputStream());
    }

    @PostMapping("/login")
    public ResponseEntity<JsonResult<?>> login(@RequestBody Member account,HttpServletRequest request) {
        String correct = (String) redisTemplate.opsForValue().get("captcha-"+account.getCaptchaId());
        if (!account.getCaptcha().equals(correct)) {
            return ResponseEntity.ok(JsonResult.fail("验证码错误"));
        }
        Member userPhone = null;
        if(account.getPhone()!=null){
             userPhone = memberFeignService.findByPhone(account.getPhone());
        }
        Member userEmail = null;
        if(account.getEmail()!=null){
             userEmail = memberFeignService.findByEmail(account.getEmail());
        }
        Member userQq = null;
        if(account.getQq()!=null){
             userQq = memberFeignService.findByQq(account.getQq());
        }
        Member userWechat = null;
        if(account.getWechat()!=null){
             userWechat = memberFeignService.findByWechat(account.getWechat());
        }

        Member user = null;
        if (userPhone != null) {
            user = userPhone;
        } else if (userEmail != null) {
            user = userEmail;
        } else if (userQq != null) {
            user = userQq;
        } else if (userWechat != null) {
            user = userWechat;
        }

        if (user == null) {
            return ResponseEntity.ok(JsonResult.fail("用户不存在"));
        }

        // 校验密码
        boolean pass = PE.checkPassword(account.getPassword(), user.getPassword());
        if (!pass) {
            return ResponseEntity.ok(JsonResult.fail("密码不正确"));
        }

        // 更新最后登录时间和IP地址
        user.setLastLoginTime(LocalDateTime.now());
        user.setLastLoginIp(getClientIp(request));
        user.setUpdatedTime(LocalDateTime.now());

        // 调用Feign服务更新用户信息
        try {
            memberFeignService.updateMemberLoginInfo(user);
        } catch (Exception e) {
            System.err.println("更新用户登录信息失败: " + e.getMessage());
        }

        //颁发jwt
        String jwt = JwtUtils.createJwt(
                UUID.randomUUID().toString(),
                user.getPhone() != null ? user.getPhone() :
                        user.getEmail() != null ? user.getEmail() :
                                user.getAccount(),
                Map.of(
                        "username", user.getName() != null ? user.getName() : user.getAccount(),
                        "userId", user.getId()
                ),
                LocalDateTime.now().plusMinutes(30),
                jwtSecret
        );
        return ResponseEntity.ok(JsonResult.success(jwt));
    }

    //获取客户端IP
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    @PostMapping("/register")
    public ResponseEntity<JsonResult<?>> save(@RequestBody @Validated Member member,HttpServletRequest request) {
        member.setCreatedTime(LocalDateTime.now());
        member.setUpdatedTime(LocalDateTime.now());
        member.setCreatedBy(getClientIp(request));
        member.setUpdatedBy(getClientIp(request));
        // 在保存前确保密码被正确加密
        String encryptedPassword = PE.encryptPassword(member.getPassword());
        member.setPassword(encryptedPassword);

        boolean success = this.memberService.save(member);
        if (success) {
            return ResponseEntity.ok(JsonResult.success(member));
        } else {
            return ResponseEntity.ok(JsonResult.fail("保存会员败"));
        }
    }
}

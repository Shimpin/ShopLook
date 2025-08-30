package com.situ.shoplook2025.login.api.utils;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
 
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
 
import java.util.concurrent.TimeUnit;
/**
 * @Description: redis验证码工具
 * @DateTime: 2025-05-13 16:52
 **/
 
    @Component
    public class RedisUtil {
 
        @Autowired
        private StringRedisTemplate stringRedisTemplate;
 
        /**
         * 设置验证码到 Redis，并设置过期时间
         * @param key Redis 键
         * @param value 验证码值
         * @param timeout 过期时间（秒）
         */
        public void setVerificationCode(String key, String value, long timeout) {
            stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
        }
 
        /**
         * 从 Redis 获取验证码
         * @param key Redis 键
         * @return 验证码值
         */
        public String getVerificationCode(String key) {
            return stringRedisTemplate.opsForValue().get(key);
        }
 
        /**
         * 删除验证码
         * @param key Redis 键
         */
        public void deleteVerificationCode(String key) {
            stringRedisTemplate.delete(key);
        }
    }
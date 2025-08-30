package com.situ.shoplook2025.login.api.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.situ.shoplook2025.login.api.common.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableCaching
@Configuration
public class CommonConfig implements WebMvcConfigurer {
    private JwtInterceptor jwtInterceptor;

    @Autowired
    public void setJwtInterceptor(JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    //mybatis-plus自动分页拦截器
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/*/members/**",
                        "/api/*/users/register/**",
                        "/api/*/users/logout/**",
                        "/api/*/users/captcha/**",
                        "/api/v1/auth/**",
                        //todo: 添加ai接口
                        "/api/v1/ai/**"
                        );
    }
}

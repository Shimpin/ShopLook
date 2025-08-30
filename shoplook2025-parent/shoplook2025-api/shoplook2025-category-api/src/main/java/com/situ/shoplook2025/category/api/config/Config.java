package com.situ.shoplook2025.category.api.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
//暴露代理
@EnableAspectJAutoProxy(exposeProxy = true)
@Configuration
@EnableCaching
public class Config {
    //mybatis-plus分页插件
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 自定义json序列化器
     *
     * @return 自定义json序列化器
     */
    @Bean
    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        //克隆一个新的ObjectMapper实例
        ObjectMapper om = new ObjectMapper();
        //添加对jdk8日期类型的支持，需要在pom文件中引入jackson-datatype-jsr310，spring-boot-starter-web已默认引入
        om.registerModule(new JavaTimeModule());
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //必须进行以下配置，否则会出现java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to xxx的异常
        //详见：https://knife.blog.csdn.net/article/details/122427607
        //配置在序列化时，将类型序列化到json中，所以能在反序列化时，自动根据类型进行反序列化
        om.activateDefaultTyping(om.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        //配置对于无法反序列化的字段，不报错。比如有getter方法，但无setter方法或者无对应字段，则在反序列化时，不报错
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式：JdkSerializationRedisSerializer）
        return new Jackson2JsonRedisSerializer<>(om, Object.class);
    }

    /**
     * 自定义RedisCacheManager是为了自定义序列化器。
     * 默认的CacheManager使用默认的JDK序列化器，序列化结果为字节数组，不易读。且需要模型类必须实现Serializable接口（目标类的引用类型的属性也需要实现此接口）
     *
     * @return 自定义RedisCacheManager，可以序列化对象
     */
    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory connectionFactory, Jackson2JsonRedisSerializer<Object> valueSerializer) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer))
                .entryTtl(Duration.ofHours(1));// 设置缓存全局统一有效期，ttl表示time to live，即存活时间。
        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }

}

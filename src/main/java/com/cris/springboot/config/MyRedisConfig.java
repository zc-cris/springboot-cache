package com.cris.springboot.config;

import com.cris.springboot.bean.Department;
import com.cris.springboot.bean.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.net.UnknownHostException;

/**
 * @ClassName MyRedisConfig
 * @Description 自定义Redis的序列化规则（以json格式保存我们的javaBean对象）
 * @Author zc-cris
 * @Version 1.0
 **/
@Configuration
public class MyRedisConfig {


    // 自定义redis的序列化规则
    @Bean
    public RedisTemplate<Object, Employee> employeeRedisTemplate(
            RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<Object, Employee> template = new RedisTemplate<Object, Employee>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setDefaultSerializer(new Jackson2JsonRedisSerializer<Employee>(Employee.class));
        return template;
    }


    // 自定义redis的cacheManager，将自定义的序列化规则器类传递进去
    @Bean
    @Primary        // 如果是有多个cacheManager的情况下，一定要使用@Primary 指定默认的cachaManager
    public RedisCacheManager EmpCacheManager(RedisTemplate<Object, Employee> employeeRedisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(employeeRedisTemplate);
        // 使用cache组件的cacheName作为前缀，方便区分数据
        cacheManager.setUsePrefix(true);
        return cacheManager;
    }

    // 自定义redis的序列化规则
    @Bean
    public RedisTemplate<Object, Department> departmentRedisTemplate(
            RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<Object, Department> template = new RedisTemplate<Object, Department>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setDefaultSerializer(new Jackson2JsonRedisSerializer<Department>(Department.class));
        return template;
    }

    // 自定义redis的cacheManager，将自定义的序列化规则器类传递进去
    @Bean
    public RedisCacheManager deptCacheManager(RedisTemplate<Object, Department> departmentRedisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(departmentRedisTemplate);
        // 使用cache组件的cacheName作为前缀，方便区分数据
        cacheManager.setUsePrefix(true);
        return cacheManager;
    }


}

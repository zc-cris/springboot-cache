package com.cris.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
* @Author zc-cris
* @Description 1. 创建数据库 2. 创建对应的javaBean 3. 创建数据源（Druid）4. 使用Mybatis（注解扫描）4. 使用缓存
* ①。开启基于注解的缓存 ②。标注缓存注解即可(@Cachealbe, @CacheEvict, @CachePut)
* @Param
* @return
**/
@MapperScan("com.cris.springboot.mapper")
@SpringBootApplication
@EnableCaching
public class SpringbootCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootCacheApplication.class, args);
    }
}

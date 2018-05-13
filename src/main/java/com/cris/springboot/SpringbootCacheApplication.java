package com.cris.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
* @Author zc-cris
* @Description 1. 创建数据库 2. 创建对应的javaBean 3. 创建数据源（Druid）4. 使用Mybatis（注解扫描）4. 使用缓存
* ①。开启基于注解的缓存 ②。标注缓存注解即可(@Cachealbe, @CacheEvict, @CachePut)
 * 默认使用的是ConcurrentMapCacheManager，将数据保存到ConcurrentMap中
 * 开发中常用的则是Redis，memcached，ehcache
 *
 * - 整合redis作为缓存
 * Redis是一个开源的，内存中的数据结构存储系统，可以用作数据库，缓存和消息中间件
 * 1. 使用docker 安装redis
 * 2. 工程引入redis的starter
 * 3. 配置redis
 * 4. 测试redis
 * 5. 测试缓存
 *  - 原理：
 *      - CacheManager--》Cache 缓存组件来给实际的缓存中存取数据
 *      - 引入了redis的starter以后，容器中保存的就是RedisManager 了
 *      - RedisManager 会帮我们创建RedisCache 缓存组件，而RedisCache 又是通过远程的redis服务器来缓存数据的
 *  - 默认保存数据k-v都是Object的时候，使用jdk的序列化来保存数据，如果需要将数据转换为json格式再保存？
 *      1. 引入了redis的starter后，cacheManager 就变为了RedisCacheManager
 *      2. 默认创建的RedisCacheManger 操作redis的时候使用的是RedisTemplate<Object，Object>
 *      3. RedisTemplate<Object，Object> 默认使用的是jdk的序列化机制
 *      4. 自定义cacheManager
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

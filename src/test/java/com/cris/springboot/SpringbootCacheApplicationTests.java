package com.cris.springboot;

import com.cris.springboot.bean.Employee;
import com.cris.springboot.mapper.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootCacheApplicationTests {

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;    // 操作k-v都是字符串

    @Autowired
    RedisTemplate redisTemplate;    // 操作k-v都是对象的

    @Autowired
    RedisTemplate<Object, Employee> employeeRedisTemplate; // 自定义序列化规则的redisTemplate

    /**
     * @return void
     * @Author zc-cris
     * @Description Redis 常见的五大数据类型
     * String，List，Set，Hash，ZSet（有序集合）
     * @Param []
     **/
    @Test
    public void testRedis() {
        //给远程redis服务器中保存数据
//        stringRedisTemplate.opsForValue().append("msg", "hello");

        // 从远程redis服务器读取数据
        String msg = stringRedisTemplate.opsForValue().get("msg");
        System.out.println(msg);

        stringRedisTemplate.opsForList().leftPush("list", "1");
        stringRedisTemplate.opsForList().leftPush("list", "2");

    }

    @Test
    public void testRedis2() {
        // 保存对象到redis服务器,默认使用jdk的序列化机制，也就是说将序列化后的数据保存到redis
//        redisTemplate.opsForValue().set("emp-01", employeeMapper.getEmpById(1));
        // 如果想将数据以josn形式保存：一般有两种方式：1. 自己将对象转换为json；2.自定义redis的序列化规则（推荐）

        employeeRedisTemplate.opsForValue().set("emp-01", employeeMapper.getEmpById(1));

    }



    // 测试能否从远程服务器的mysql中获取数据（通过Druid数据源）
    @Test
    public void contextLoads() {
        Employee emp = employeeMapper.getEmpById(1);
        System.out.println(emp);
    }

}

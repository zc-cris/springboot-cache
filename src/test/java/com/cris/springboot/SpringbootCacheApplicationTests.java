package com.cris.springboot;

import com.cris.springboot.bean.Employee;
import com.cris.springboot.mapper.EmployeeMapper;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootCacheApplicationTests {

    @Autowired
    EmployeeMapper employeeMapper;

    // 测试能否从远程服务器的mysql中获取数据（通过Druid数据源）
    @Test
    public void contextLoads() {
        Employee emp = employeeMapper.getEmpById(1);
        System.out.println(emp);
    }

}

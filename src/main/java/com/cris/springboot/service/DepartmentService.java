package com.cris.springboot.service;

import com.cris.springboot.bean.Department;
import com.cris.springboot.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * @ClassName DepartmentService
 * @Description TODO
 * @Author zc-cris
 * @Version 1.0
 **/
@Service
public class DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    @Qualifier("deptCacheManager")
    @Autowired
    CacheManager deptCacheManager;

    /**
     * @return com.cris.springboot.bean.Department
     * @Author zc-cris
     * @Description 缓存的数据可以存入redis；但是第二次从缓存中查询却无法反序列化回来，因为存入dept的json格式的数据
     * 但是默认使用RedisTemplate<Object, Employee> 序列化类来反序列化
     * 解决方案：为每个单独的需要序列化的javaBean对象创建CacheManager 和 cacheTemplate，service层调用dao层方法的时候缓存注解需要注明指定的属性
     * @Param [id]
     **/
//    @Cacheable(cacheNames = "dept", cacheManager = "deptCacheManager")
//    public Department getDept(Integer id){
//        return departmentMapper.getDeptById(id);
//    }
    public Department getDept(Integer id) {
        System.out.println("查询" + id + "号部门");
        Department dept = departmentMapper.getDeptById(id);
        // 使用cacheManager 来获取指定的缓存组件
        Cache cache = deptCacheManager.getCache("dept");
        // 编码方式将数据放入到缓存组件中
        cache.put("dept:1", dept);
        return dept;
    }

}

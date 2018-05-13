package com.cris.springboot.service;

import com.cris.springboot.bean.Employee;
import com.cris.springboot.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

/**
 * @ClassName EmployeeService
 * @Description TODO
 * @Author zc-cris
 * @Version 1.0
 **/
// 对于缓存的一些通用配置可以使用@CacheConfig 注解完成，例如通用的cache组件等
@CacheConfig(cacheNames = "emp", cacheManager = "EmpCacheManager")
@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    /**
    * @Author zc-cris
    * @Description 将方法的运行结果放入缓存，下次使用相同的数据直接从缓存中取，不再调用方法
     * - CacheManager 管理多个Cache 组件，对缓存的真正CRUD 操作在Cache组件中，每一个缓存组件都有一个唯一的名字
     * - 默认会配置一个SimpleCacheConfiguration；给容器中注册了一个CacheManager：ConcurrentMapCacheManager；
     *      可以获取和创建ConcurrentMapCache 类型的缓存组件：将数据保存在CocurrentMap 中
     *
     * - 运行流程：@Cacheable
     *  1. 方法运行前：先去查询Cache（缓存组件），按照cacheNames 指定的名字获取（CacheManager先获取到对应的缓存组件，第一次获取组件如果没有就自动创建）
     *  2. 去Cache组件中查找缓存的内容，默认key就是方法的参数：
     *      key是按照某种策略生成的：默认使用SimpleKeyGenerator 生成key
     *          SimpleKeyGenerator生成key 的默认策略：
     *              如果没有参数：key=new SimpleKey();
     *              如果有一个参数：key=参数的值
     *              如果有多个参数：key=new SimpleKey(params)
     *  3. 没有查询到数据就调用目标方法（所以目标方法不一定会执行 ）
     *  4. 将目标方法的返回值放入到缓存组件中
     *
     *  - 流程总结：1. 使用CacheManager【ConcurrentMapCacheManager】按照名字得到Cache组件【ConcurrentMapCache】
     *            2. key使用keyGenerator生成，默认是SimpleKeyGenerator（我们可以自定义）
     *
     * - 核心属性：
     * 1. cacheNames/value:指定缓存组件的名字
     * 2. key：缓存数据用的key，默认使用方法的参数值；可以使用SpEl；#id：参数id的值；#a0 #p0 #root.args[0]
     * 3. keyGenerator:key的生成器，可以自定义
     *          key/keyGenerator : 二选一
     * 4. cacheManager：指定缓存管理器；或者使用cacheResolver指定解析器，两者选一个即可
     * 5. condition：指定符合条件才缓存数据，condition = "#id > 0"
     * 6. unless:否定缓存，unless 指定的条件为true，方法的返回值就不会被缓存，可以获取到结果进行判断
     *          unless = "#result == null"
     *          unless = "#a0 == 2":如果第一个参数的值为2，那么不缓存查询出来的数据
     * 7. sync：是否使用异步模式（默认为false）：如果为true，unless属性不支持
     *
    * @Param [id]
    * @return com.cris.springboot.bean.Employee
    **/
    //@Cacheable(cacheNames = {"emp"}, key = "#root.methodName + '[' + #id + ']'")
    @Cacheable(cacheNames = {"emp"} /*, keyGenerator = "myKeyGenerator", condition = "#a0 > 1", unless = "#a0 == 2"*/)
    public Employee getEmp(Integer id){
        System.out.println("查询"+id+"号员工...");
        return employeeMapper.getEmpById(id);
    }

    /**
    * @Author zc-cris
    * @Description @CachePut:先调用方法更新数据库数据，然后更新缓存，达到了同步更新缓存的目的，保证后面查询对应的数据都是缓存中最新的数据
     * 运行时机：和@Cacheable 不同，一定要先调用目标方法；然后将目标方法的返回结果缓存起来
     * 注意：缓存数据的key需要和@Cacheable 指定的key相同，保证更新和查询的数据都是同一个key指定的数据
     *  key = "#employee.id":使用传入的employee对象的id
     *  key = "#result.id":使用返回后的employee对象的id
     *      但是@Cacheable 中的key不能使用#result（因为是在目标方法执行前进行调用，拿不到目标方法的返回值）
     *
    * @Param [employee]
    * @return com.cris.springboot.bean.Employee
    **/
    @CachePut(value = "emp", key = "#result.id")
    public Employee updateEmp(Employee employee){
        employeeMapper.updateEmp(employee);
        return employee;
    }

    /**
    * @Author zc-cris
    * @Description @CacheEvict 代表缓存清除
     *  key:指定要清除的缓存中的数据的key
     *  allEntries = true：指定清除环村组件中的所有缓存；默认为false
     *  beforeInvocation = false：缓存的清除是否在目标方法执行之前进行
     *      默认为false，即缓存清除在方法执行后进行；如果方法出现异常，那么缓存清理失败
     *      如果为true：代表缓存清理是在目标方法执行前就进行了，所以缓存肯定会清空
     *
     *
    * @Param [id]
    * @return void
    **/
    @CacheEvict(/*value = "emp", */key = "#id"/*, beforeInvocation = true, allEntries = true*/)
    public void deleteEmp(Integer id){
        // 打印这句话代表数据已经被删除
        System.out.println("deleteEmp" + id);
//        employeeMapper.removeEmpById(id);
//        int i = 10/0;
    }

    /**
    * @Author zc-cris
    * @Description 多个缓存注解可以联合使用，以适应更加复杂的缓存需求
    * @Param [lastName]
    * @return com.cris.springboot.bean.Employee
    **/
    @Caching(cacheable = {
            @Cacheable(/*value = "emp",*/ key = "#lastName")
    },
            put = {
            @CachePut(/*value = "emp",*/ key = "#result.id"),
                    @CachePut(/*value = "emp",*/ key = "#result.email")
            })
    public Employee getEmpByLastName(String lastName){
        return employeeMapper.getEmpByLastName(lastName);
    }






}

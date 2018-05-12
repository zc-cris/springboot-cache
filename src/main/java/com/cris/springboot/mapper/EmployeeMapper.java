package com.cris.springboot.mapper;

import com.cris.springboot.bean.Employee;
import org.apache.ibatis.annotations.*;

// 这里使用注解版来标识mapper每个方法的执行sql，实际开发中基本使用mapper映射文件
public interface EmployeeMapper {

    // 插入数据完成后，将自增长的id值又放入当前department 对象中
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into employee (lastName, email, gender, d_id) values (#{lastName}, #{email}, #{gender}, #{dId})")
    public int saveEmp(Employee employee);

    @Delete("delete from employee where id = #{id}")
    public int removeEmpById(Integer id);

    @Update("update employee set lastName = #{lastName}, email = #{email}, gender = #{gender}, d_id = #{dId} where id = #{id}")
    public int updateEmp(Employee employee);

    @Select("select id, lastName, email, gender, d_id from employee where id = #{id}")
    public Employee getEmpById(Integer id);

    @Select("select id, lastName, email, gender, d_id from employee where lastName = #{lastName}")
    Employee getEmpByLastName(String lastName);
}

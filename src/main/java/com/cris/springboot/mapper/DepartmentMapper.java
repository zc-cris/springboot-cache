package com.cris.springboot.mapper;

import com.cris.springboot.bean.Department;
import org.apache.ibatis.annotations.Select;

public interface DepartmentMapper {

    @Select("select * from department where id = #{id}")
    public Department getDeptById(Integer id);

}

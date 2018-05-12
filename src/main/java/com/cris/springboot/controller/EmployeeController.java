package com.cris.springboot.controller;

import com.cris.springboot.bean.Employee;
import com.cris.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName EmployeeController
 * @Description TODO
 * @Author zc-cris
 * @Version 1.0
 **/
@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/emp/{id}")
    public Employee getEmp(@PathVariable("id") Integer id){
        return employeeService.getEmp(id);
    }

    @GetMapping("/emp")
    public Employee updateEmp(Employee employee){
        employeeService.updateEmp(employee);
        return employee;
    }

    @GetMapping("/delEmp/{id}")
    public void deleteEmp(@PathVariable("id") Integer id){
        employeeService.deleteEmp(id);
    }

    @GetMapping("/emp/lastName/{lastName}")
    public Employee getEmpByLastName(@PathVariable("lastName") String lastName){
        return employeeService.getEmpByLastName(lastName);
    }

}

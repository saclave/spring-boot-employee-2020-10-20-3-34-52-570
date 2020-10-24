package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.model.*;

public class EmployeeMapper {

    private Employee employee;

    public EmployeeResponse toResponse(Employee employee){
        EmployeeResponse response = new EmployeeResponse();

        response.setId(employee.getId());
        response.setAge(employee.getAge());
        response.setGender(employee.getGender());
        response.setCompanyId(employee.getCompanyId());
        response.setName(employee.getName());
        response.setSalary(employee.getSalary());

        return response;
    }

    public Employee toEntity(EmployeeRequest request){
        if(request == null) {
           employee = new Employee();
        }
        employee.setAge(request.getAge());
        employee.setGender(request.getGender());
        employee.setName(request.getName());
        employee.setSalary(request.getSalary());

        return  employee;
    }

}

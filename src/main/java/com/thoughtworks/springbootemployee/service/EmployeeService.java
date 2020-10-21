package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;

import java.util.List;

public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAllEmployees();
    }

    public Employee create(Employee employeeRequest) {
        return employeeRepository.saveEmployee(employeeRequest);
    }

    public Employee update(Employee employeeRequest) {
        return employeeRepository.updateEmployee(employeeRequest);
    }
}

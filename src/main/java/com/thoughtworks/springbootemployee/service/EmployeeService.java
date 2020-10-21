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

    public Employee update(Integer employeeId, Employee employeeUpdate) {
        Employee employee = employeeRepository.findEmployeeById(employeeId);
        if (employeeUpdate.getName() != null) {
            employee.setName(employeeUpdate.getName());
        }
        if (employeeUpdate.getAge() != null) {
            employee.setAge(employeeUpdate.getAge());
        }
        if (employeeUpdate.getGender() != null) {
            employee.setGender(employeeUpdate.getGender());
        }
        if (employeeUpdate.getSalary() != null) {
            employee.setSalary(employeeUpdate.getSalary());
        }
        return employee;
    }

    public Employee getEmployee(Integer employeeId) {
        return employeeRepository.findEmployeeById(employeeId);
    }

    public void deleteEmployee(int employeeId) {
        employeeRepository.deleteEmployee(employeeId);
    }
}

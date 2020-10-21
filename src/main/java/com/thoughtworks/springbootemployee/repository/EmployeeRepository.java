package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    List<Employee> employees = new ArrayList<>();

    public List<Employee> findAllEmployees() {
        return null;
    }

    public Employee saveEmployee(Employee employeeRequest) {
        employees.add(employeeRequest);
        return employeeRequest;
    }

    public Employee updateEmployee(Employee employeeRequest) {
        if (employees.contains(employeeRequest)){
            return null;
        }
        employees.remove(employeeRequest);
        employees.add(employeeRequest);
        return employeeRequest;
    }

    public Employee findEmployeeById(Integer employeeId) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(employeeId))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException("Employee Not Found"));
    }

    public void deleteEmployee(int employeeId) {
        employees.stream()
                .filter(employee -> employee.getId().equals(employeeId))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException("Employee Not Found"));
    }
}

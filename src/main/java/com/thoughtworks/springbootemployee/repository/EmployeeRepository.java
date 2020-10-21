package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.thoughtworks.springbootemployee.utils.ExceptionConstants.EMPLOYEE_NOT_FOUND;

@Repository
public class EmployeeRepository {
    private List<Employee> employees = new ArrayList<>();

    public List<Employee> findAllEmployees() {
        return employees;
    }

    public Employee saveEmployee(Employee employeeRequest) {
        employees.add(employeeRequest);
        return employeeRequest;
    }

    public Employee updateEmployee(Employee employeeRequest) {
        if (employees.contains(employeeRequest)) {
            throw new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND);
        }
        employees.remove(employeeRequest);
        employees.add(employeeRequest);
        return employeeRequest;
    }

    public Employee findEmployeeById(Integer employeeId) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(employeeId))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND));
    }

    public void deleteEmployee(int employeeId) {
        Employee deleteEmployee = employees.stream()
                .filter(employee -> employee.getId().equals(employeeId))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND));

        employees.remove(deleteEmployee);
    }
}

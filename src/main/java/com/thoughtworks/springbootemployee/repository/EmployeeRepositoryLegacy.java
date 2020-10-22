package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException.EMPLOYEE_NOT_FOUND;

@Repository
public class EmployeeRepositoryLegacy {
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

    public List<Employee> findEmployeesByGender(String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public List<Employee> findEmployeesByPagination(Long page, Long pageSize) {
        return employees.stream()
                .sorted(Comparator.comparing(Employee::getId))
                .skip(pageSize * (page - 1))
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public List<Employee> findEmployeesByCompanyId(Integer companyId) {
        return employees.stream()
                .filter(employee -> employee.getCompanyId().equals(companyId))
                .collect(Collectors.toList());
    }
}

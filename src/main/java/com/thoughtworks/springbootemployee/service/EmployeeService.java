package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepositoryLegacy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private EmployeeRepositoryLegacy employeeRepositoryLegacy;

    public EmployeeService(EmployeeRepositoryLegacy employeeRepositoryLegacy) {
        this.employeeRepositoryLegacy = employeeRepositoryLegacy;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepositoryLegacy.findAllEmployees();
    }

    public Employee create(Employee employeeRequest) {
        return employeeRepositoryLegacy.saveEmployee(employeeRequest);
    }

    public Employee update(Integer employeeId, Employee employeeUpdate) {
        Employee employee = employeeRepositoryLegacy.findEmployeeById(employeeId);
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
        return employeeRepositoryLegacy.findEmployeeById(employeeId);
    }

    public void deleteEmployee(int employeeId) {
        employeeRepositoryLegacy.deleteEmployee(employeeId);
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeRepositoryLegacy.findEmployeesByGender(gender);
    }

    public List<Employee> getPaginatedEmployee(Long page, Long pageSize) {
        return employeeRepositoryLegacy.findEmployeesByPagination(page, pageSize);
    }

    public List<Employee> getEmployeesByCompanyId(Integer companyId) {
        return employeeRepositoryLegacy.findEmployeesByCompanyId(companyId);
    }
}

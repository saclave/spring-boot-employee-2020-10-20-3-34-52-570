package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.exception.FieldIsNullException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException.EMPLOYEE_NOT_FOUND;
import static com.thoughtworks.springbootemployee.exception.FieldIsNullException.FIELD_IS_NULL;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee create(Employee employeeRequest) {
        return employeeRepository.save(employeeRequest);
    }

    public Employee update(Integer employeeId, Employee employeeUpdate) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND));
        validateEmployeeName(employeeUpdate.getName());


        employee.setName(employeeUpdate.getName());
        employee.setAge(employeeUpdate.getAge());
        employee.setGender(employeeUpdate.getGender());
        employee.setSalary(employeeUpdate.getSalary());

        return employeeRepository.save(employee);
    }

    private void validateEmployeeName(String name) {
        if (name == null) {
            throw new FieldIsNullException(FIELD_IS_NULL);
        }
    }

    public Employee getEmployee(Integer employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND));
    }

    public void deleteEmployee(int employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public List<Employee> getPaginatedEmployee(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return employeeRepository.findAll(pageable).toList();
    }

}

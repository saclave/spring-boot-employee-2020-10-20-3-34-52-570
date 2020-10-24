package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.model.EmployeeRequest;
import com.thoughtworks.springbootemployee.model.EmployeeResponse;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException.EMPLOYEE_NOT_FOUND;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private List<Employee> employees;
    private EmployeeMapper employeeMapper;
    private Employee savedEmployee;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeResponse> getAllEmployees() {
        employees = employeeRepository.findAll();
        return employees.stream().map(employeeMapper::toResponse).collect(Collectors.toList());
    }

    public EmployeeResponse create(EmployeeRequest employeeRequest) {
        savedEmployee = employeeRepository.save(employeeMapper.toEntity(employeeRequest));
        return employeeMapper.toResponse(savedEmployee);
    }

    public EmployeeResponse update(Integer employeeId, Employee employeeRequest) {
        employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND));

        savedEmployee = employeeRepository.save(employeeMapper.toEntity(employeeRequest));
        return employeeMapper.toResponse(savedEmployee);
    }

    public EmployeeResponse getEmployee(Integer employeeId) {
        savedEmployee = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND));

        return employeeMapper.toResponse(savedEmployee);
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

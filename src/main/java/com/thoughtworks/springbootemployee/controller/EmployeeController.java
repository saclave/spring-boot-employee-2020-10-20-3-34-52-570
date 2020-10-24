package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.model.EmployeeRequest;
import com.thoughtworks.springbootemployee.model.EmployeeResponse;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    public EmployeeController(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping
    public List<EmployeeResponse> getEmployeeList() {
        return employeeMapper.toResponseList(employeeService.getAllEmployees());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse addEmployee(@RequestBody EmployeeRequest employeeRequest) {
        Employee createdEmployee = employeeService.create(employeeMapper.toEntity(employeeRequest));
        return employeeMapper.toResponse(createdEmployee);
    }

    @GetMapping("/{employeeId}")
    public EmployeeResponse getEmployee(@PathVariable int employeeId) {
        return employeeMapper.toResponse(employeeService.getEmployee(employeeId));
    }

    @PutMapping("/{employeeId}")
    public EmployeeResponse updateEmployee(@PathVariable("employeeId") Integer employeeId,
                                           @RequestBody EmployeeRequest employeeRequest) {
        Employee employee = employeeMapper.toEntity(employeeRequest);
        Employee updatedEmployee = employeeService.update(employeeId, employee);
        return employeeMapper.toResponse(updatedEmployee);
    }

    @DeleteMapping("/{employeeId}")
    public void deleteEmployee(@PathVariable Integer employeeId) {
        employeeService.deleteEmployee(employeeId);
    }

    @GetMapping(params = "gender")
    public List<EmployeeResponse> getEmployeeGender(@RequestParam("gender") String gender) {
        return employeeMapper.toResponseList(employeeService.getEmployeesByGender(gender));
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<EmployeeResponse> pagination(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        return employeeMapper.toResponseList(employeeService.getPaginatedEmployee(page, pageSize));
    }
}

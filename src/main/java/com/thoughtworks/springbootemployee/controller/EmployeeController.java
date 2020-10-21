package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private List<Employee> employeeList = new ArrayList<>();

    @GetMapping
    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        employeeList.add(employee);
        return employee;
    }

    @GetMapping("/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId) {
        return employeeList.stream()
                .filter(employee -> employee.getId().equals(employeeId))
                .findFirst()
                .orElse(null);
    }

    @PutMapping("/{employeeId}")
    public Employee updateEmployee(@PathVariable Integer employeeId, @RequestBody Employee employeeUpdate) {
        employeeList.stream()
                .filter(employee -> employee.getId().equals(employeeId))
                .findFirst()
                .ifPresent(employee -> {
                    employeeList.remove(employee);
                    employeeList.add(employeeUpdate);
                });
        return employeeUpdate;
    }

    @DeleteMapping("/{employeeId}")
    public void deleteEmployee(@PathVariable Integer employeeId) {
        employeeList.stream()
                .filter(employee -> employee.getId().equals(employeeId))
                .findFirst()
                .ifPresent(employeeList::remove);
    }

    @GetMapping(params = "gender")
    public List<Employee> getEmployeeGender(@RequestParam("gender") String gender) {
        return employeeList.stream()
                .filter(employee -> employee.getGender().equalsIgnoreCase(gender))
                .collect(Collectors.toList());
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> pagination(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize) {
        return employeeList.stream()
                .sorted(Comparator.comparing(Employee::getId))
                .skip(page)
                .limit(pageSize)
                .collect(Collectors.toList());
    }
}

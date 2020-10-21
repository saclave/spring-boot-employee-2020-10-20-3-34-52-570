package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private List<Company> companyList = new ArrayList<>();
    private List<Employee> employeeList = new ArrayList<>();

    @GetMapping
    public List<Company> getCompanyList() {
        return companyList;
    }

    @PostMapping
    public Company addCompany(@RequestBody Company company) {
        companyList.add(company);
        return company;
    }

    @GetMapping("/{companyId}")
    public List<Employee> getEmployeeList(@PathVariable int companyId) {
        return employeeList.stream()
                .filter(employee -> employee.getCompanyId().equals(companyId))
                .collect(Collectors.toList());
    }

    @GetMapping("/{companyId}")
    public Company getCompany(@PathVariable int companyId) {
        return companyList.stream()
                .filter(company -> company.getCompanyId().equals(companyId))
                .findFirst()
                .orElse(null);
    }

    @PutMapping("/{companyId}")
    public Company updateCompany(@PathVariable Integer companyId, @RequestBody Company companyUpdate) {
        companyList.stream()
                .filter(company -> company.getCompanyId().equals(companyId))
                .findFirst()
                .ifPresent(company -> {
                    companyList.remove(company);
                    companyList.add(companyUpdate);
                });
        return companyUpdate;
    }

    @DeleteMapping("/{companyId}")
    public void deleteCompany(@PathVariable Integer companyId) {
        companyList.stream()
                .filter(company -> company.getCompanyId().equals(companyId))
                .findFirst()
                .ifPresent(companyList::remove);
    }
}

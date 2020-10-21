package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }

    @GetMapping
    public List<Company> getCompanyList() {
        return companyService.getAllCompanies();
    }

    @PostMapping
    public Company addCompany(@RequestBody Company company) {
        return companyService.create(company);
    }

    @GetMapping("/employeeList/{companyId}")
    public List<Employee> getEmployeeList(@PathVariable int companyId) {
        return companyService.getEmployeesByCompanyId(companyId);
    }

    @GetMapping("/{companyId}")
    public Company getCompany(@PathVariable int companyId) {
        return companyService.getCompany(companyId);
    }

    @PutMapping("/{companyId}")
    public Company updateCompany(@PathVariable Integer companyId, @RequestBody Company companyUpdate) {
        return companyService.updateCompany(companyId, companyUpdate);
    }

    @DeleteMapping("/{companyId}")
    public void deleteCompany(@PathVariable Integer companyId) {
        companyService.deleteCompany(companyId);
    }
}

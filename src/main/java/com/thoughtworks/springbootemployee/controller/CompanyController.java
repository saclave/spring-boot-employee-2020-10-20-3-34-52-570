package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;
    private final EmployeeService employeeService;

    public CompanyController(CompanyService companyService, EmployeeService employeeService){
        this.companyService = companyService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Company> getCompanyList() {
        return companyService.getAllCompanies();
    }

    @PostMapping
    public Company addCompany(@RequestBody Company company) {
        return companyService.create(company);
    }

    @GetMapping("/{companyId}/employeeList")
    public List<Employee> getEmployeeList(@PathVariable int companyId) {
        return employeeService.getEmployeesByCompanyId(companyId);
    }

    @GetMapping("/{companyId}")
    public Company getCompany(@PathVariable int companyId) {
        return companyService.getCompany(companyId);
    }

    @PutMapping("/{companyId}")
    public Company updateCompany(@PathVariable Integer companyId, @RequestBody Company companyUpdate) {
        return companyService.updateCompany(companyId, companyUpdate);
    }

    @DeleteMapping("/{companyId}/")
    public void deleteCompany(@PathVariable Integer companyId) {
        companyService.deleteCompany(companyId);
    }

//    @DeleteMapping("/{companyId}/companyEmployees")
//    public void deleteCompanyEmployees(@PathVariable Integer companyId) { companyService.deleteCompanyEmployees(companyId); }
}

package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;
    private final EmployeeService employeeService;

    public CompanyController(CompanyService companyService, EmployeeService employeeService) {
        this.companyService = companyService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Company> getCompanyList() {
        return companyService.getAllCompanies();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
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

    @DeleteMapping("/{companyId}/deleteCompanyEmployees")
    public void deleteCompanyEmployees(@PathVariable Integer companyId) {
        companyService.deleteCompanyEmployees(companyId);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getPaginated(@RequestParam("page") Long page, @RequestParam("pageSize") Long pageSize) {
        return companyService.getPaginatedEmployee(page, pageSize);
    }
}

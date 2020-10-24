package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.CompanyRequest;
import com.thoughtworks.springbootemployee.model.CompanyResponse;
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
    public List<CompanyResponse> getCompanyList() {
        return companyService.getAllCompanies();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponse addCompany(@RequestBody CompanyRequest companyRequest) {
        return companyService.create(companyRequest);
    }

    @GetMapping("/{companyId}/employeeList")
    public List<Employee> getEmployeeList(@PathVariable int companyId) {
        return companyService.getEmployeesByCompanyId(companyId);
    }

    @GetMapping("/{companyId}")
    public CompanyResponse getCompany(@PathVariable int companyId) {
        return companyService.getCompany(companyId);
    }

    @PutMapping("/{companyId}")
    public CompanyResponse updateCompany(@PathVariable Integer companyId, @RequestBody CompanyRequest companyUpdate) {
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
    public List<Company> getPaginated(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        return companyService.getPaginationByCompany(page, pageSize);
    }
}

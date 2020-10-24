package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.mapper.CompanyMapper;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.CompanyRequest;
import com.thoughtworks.springbootemployee.model.CompanyResponse;
import com.thoughtworks.springbootemployee.model.EmployeeResponse;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;
    private EmployeeMapper employeeMapper;

    public CompanyController(CompanyMapper companyMapper, CompanyService companyService,
                             EmployeeMapper employeeMapper) {
        this.companyService = companyService;
        this.employeeMapper = employeeMapper;
        this.companyMapper = companyMapper;
    }

    @GetMapping
    public List<Company> getCompanyList() {
        return companyService.getAllCompanies();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponse addCompany(@RequestBody CompanyRequest companyRequest) {
        Company company = companyMapper.toEntity(companyRequest);
        return companyMapper.toResponse(companyService.create(company));
    }

    @GetMapping("/{companyId}/employeeList")
    public List<CompanyResponse> getEmployeeList() {
        return companyMapper.toResponseList(companyService.getAllCompanies());
    }

    @GetMapping("/{companyId}")
    public CompanyResponse getCompany(@PathVariable("companyId") Integer companyId) {
        return companyMapper.toResponse(companyService.getCompany(companyId));
    }

    @PutMapping("/{companyId}")
    public CompanyResponse updateCompany(@PathVariable("companyId") Integer companyId, @RequestBody CompanyRequest companyRequest) {
        Company company = companyMapper.toEntity(companyRequest);
        Company updatedCompany = companyService.updateCompany(companyId, company);
        return companyMapper.toResponse(updatedCompany);
    }

    @GetMapping("/{companyId}/employees")
    public List<EmployeeResponse> getEmployeesByCompanyId(@PathVariable("companyId") Integer companyId) {
        return employeeMapper.toResponseList(companyService.getEmployeesByCompanyId(companyId));
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
    public List<CompanyResponse> getPaginated(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        return companyMapper.toResponseList(companyService.getPaginationByCompany(page, pageSize));
    }
}

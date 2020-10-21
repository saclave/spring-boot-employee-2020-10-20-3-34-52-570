package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;

import java.util.List;

public class CompanyService {
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    public CompanyService(CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Company> getAllCompanies() { return companyRepository.findAllCompanies(); }

    public Company create(Company company) { return companyRepository.saveCompany(company); }

    public Company updateCompany(Integer companyId, Company companyRequest) {
        Company company = companyRepository
                .findByCompanyId(companyId);

        if(companyRequest.getCompanyName() != null){
                company.setCompanyName(companyRequest.getCompanyName());
        }

        if(companyRequest.getNumOfEmployees() != null){
                company.setNumOfEmployees(companyRequest.getNumOfEmployees());
        }
        return company;
    }

    public Company getCompany(Integer companyId) {
        return companyRepository.findByCompanyId(companyId);
    }

    public void deleteCompany(Integer companyId) {
        companyRepository.deleteCompany(companyId);
    }

//    public List<Employee> findCompanyEmployees(Integer companyId){
//        return employeeRepository.stream()
//                .findEmployeesByCompanyId(companyId);
//    }
}

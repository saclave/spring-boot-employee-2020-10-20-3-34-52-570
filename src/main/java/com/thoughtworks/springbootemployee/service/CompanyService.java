package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;

import java.util.List;

public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAllCompanies() { return companyRepository.findAllCompanies(); }

    public Company create(Company company) { return companyRepository.saveCompany(company); }

    public Company updateCompany(Integer companyId, Company companyRequest) {
        Company company = companyRepository
                .findById(companyId);

        if(company != null){
            if(companyRequest.getCompanyName() != null){
                company.setCompanyName(companyRequest.getCompanyName());
            }
            if(companyRequest.getNumOfEmployees() != null){
                company.setNumOfEmployees(companyRequest.getNumOfEmployees());
            }
            return company;
        }
        throw new RuntimeException("Company not found!");
    }
}

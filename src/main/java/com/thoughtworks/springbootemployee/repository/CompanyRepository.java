package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyRepository {
    List<Company> companyList = new ArrayList<>();

    public List<Company> findAllCompanies() { return null; }

    public Company saveCompany(Company companyRequest) {
        companyList.add(companyRequest);
        return companyRequest;
    }

    public Company updateCompany(Company companyRequest) {
        if (companyList.contains(companyRequest)){
            return null;
        }
        companyList.remove(companyRequest);
        companyList.add(companyRequest);
        return companyRequest;
    }

    public Company findById(Integer companyId) {
        return companyList.stream()
                .filter(company -> company.getCompanyId().equals(companyId))
                .findFirst()
                .orElse(null);
    }
}

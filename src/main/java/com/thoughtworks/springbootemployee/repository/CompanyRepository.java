package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;

import java.util.ArrayList;
import java.util.List;

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
}

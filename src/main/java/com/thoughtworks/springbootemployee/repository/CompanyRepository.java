package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;

import java.util.ArrayList;
import java.util.List;

public class CompanyRepository {
    List<Company> companyList = new ArrayList<>();

    public List<Company> findAllCompanies() { return null; }

    public Company saveCompany(Company companyRequest) {
        companyList.add(companyRequest);
        return companyRequest;
    }
}

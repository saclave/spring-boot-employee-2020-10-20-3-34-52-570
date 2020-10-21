package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.thoughtworks.springbootemployee.exception.CompanyNotFoundException.COMPANY_NOT_FOUND;

public class CompanyRepository {
    List<Company> companyList = new ArrayList<>();

    public List<Company> findAllCompanies() { return companyList; }

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

    public Company findByCompanyId(Integer companyId) {
        return companyList.stream()
                .filter(company -> company.getCompanyId().equals(companyId))
                .findFirst()
                .orElseThrow(() -> new CompanyNotFoundException(COMPANY_NOT_FOUND));
    }

    public void deleteCompany(Integer companyId) {
        Company deleteCompany = companyList.stream()
                .filter(company -> company.getCompanyId().equals(companyId))
                .findFirst()
                .orElseThrow(() -> new CompanyNotFoundException(COMPANY_NOT_FOUND));

        companyList.remove(deleteCompany);
    }

    public List<Company> findCompanyPagination(Long page, Long pageSize) {
        return companyList.stream()
                .sorted(Comparator.comparing(Company::getCompanyId))
                .skip(pageSize * (page - 1))
                .limit(pageSize)
                .collect(Collectors.toList());
    }

}

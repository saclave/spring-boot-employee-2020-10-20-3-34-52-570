package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepositoryLegacy;
import com.thoughtworks.springbootemployee.repository.EmployeeRepositoryLegacy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepositoryLegacy companyRepositoryLegacy;
    private final EmployeeRepositoryLegacy employeeRepositoryLegacy;

    public CompanyService(CompanyRepositoryLegacy companyRepositoryLegacy, EmployeeRepositoryLegacy employeeRepositoryLegacy) {
        this.companyRepositoryLegacy = companyRepositoryLegacy;
        this.employeeRepositoryLegacy = employeeRepositoryLegacy;
    }

    public List<Company> getAllCompanies() {
        return companyRepositoryLegacy.findAllCompanies();
    }

    public Company create(Company company) {
        return companyRepositoryLegacy.saveCompany(company);
    }

    public Company updateCompany(Integer companyId, Company companyRequest) {
        Company company = companyRepositoryLegacy.findByCompanyId(companyId);
        if (companyRequest.getCompanyName() != null) {
            company.setCompanyName(companyRequest.getCompanyName());
        }
        return company;
    }

    public Company getCompany(Integer companyId) {
        return companyRepositoryLegacy.findByCompanyId(companyId);
    }

    public void deleteCompany(Integer companyId) {
        companyRepositoryLegacy.deleteCompany(companyId);
    }

    public List<Company> getPaginationByCompany(long page, long pageSize) {
        return companyRepositoryLegacy.findCompanyPagination(page, pageSize);
    }

    public List<Employee> getEmployeesByCompanyId(int companyId) {
        return employeeRepositoryLegacy.findEmployeesByCompanyId(companyId);
    }

    public List<Company> getPaginatedEmployee(Long page, Long pageSize) {
        return companyRepositoryLegacy.findCompanyPagination(page, pageSize);
    }

    public void deleteCompanyEmployees(Integer companyId) {
        companyRepositoryLegacy.deleteCompanyEmployeesByCompanyId(companyId);
    }
}

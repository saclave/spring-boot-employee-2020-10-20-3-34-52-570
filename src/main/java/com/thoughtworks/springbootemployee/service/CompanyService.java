package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.CompanyRepositoryLegacy;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepositoryLegacy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private CompanyRepositoryLegacy companyRepositoryLegacy;
    private EmployeeRepositoryLegacy employeeRepositoryLegacy;
    private EmployeeRepository employeeRepository;
    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }


    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company create(Company company) {
        return companyRepository.save(company);
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
//        return employeeRepository
//                .findById(companyId)
//                .map(Company::getEmployeeList)
//                .orElse(null);
    }

    public List<Company> getPaginatedEmployee(Long page, Long pageSize) {
        return companyRepositoryLegacy.findCompanyPagination(page, pageSize);
    }

    public void deleteCompanyEmployees(Integer companyId) {
        companyRepositoryLegacy.deleteCompanyEmployeesByCompanyId(companyId);
    }
}

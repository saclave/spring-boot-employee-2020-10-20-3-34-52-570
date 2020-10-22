package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
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
        companyRequest.setCompanyId(companyId);
        return companyRepository.save(companyRequest);
    }

    public Company getCompany(Integer companyId) {
        return companyRepository.findById(companyId).orElse(null);
    }

    public void deleteCompany(Integer companyId) {
        companyRepository.deleteById(companyId);
    }

    public List<Company> getPaginationByCompany(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return companyRepository.findAll(pageable).toList();
    }

    public List<Employee> getEmployeesByCompanyId(int companyId) {
        return companyRepository.findById(companyId)
                .map(Company::getEmployeeList)
                .orElse(null);
    }

    public List<Company> getPaginatedEmployee(Long page, Long pageSize) {
       // return companyRepositoryLegacy.findCompanyPagination(page, pageSize);
        return null;
    }

    public void deleteCompanyEmployees(Integer companyId) {
        List<Employee> employees = companyRepository.findById(companyId)
                .map(Company::getEmployeeList)
                .orElse(null);

        if(employees != null) {
            employees.stream().forEach(employee -> employeeRepository.deleteById(employee.getId()));
        }
    }
}

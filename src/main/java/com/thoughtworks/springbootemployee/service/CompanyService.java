package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.mapper.CompanyMapper;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.CompanyRequest;
import com.thoughtworks.springbootemployee.model.CompanyResponse;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.thoughtworks.springbootemployee.exception.CompanyNotFoundException.COMPANY_NOT_FOUND;

@Service
public class CompanyService {
    private EmployeeRepository employeeRepository;
    private CompanyRepository companyRepository;
    private CompanyMapper companyMapper;
    private List<Company> companies;
    private Company savedCompany;

    public CompanyService(CompanyRepository companyRepository, EmployeeRepository employeeRepository,
                          CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
        this.companyMapper = companyMapper;
    }


    public List<CompanyResponse> getAllCompanies() {
        companies = companyRepository.findAll();
        return companies.stream().map(companyMapper::toResponse).collect(Collectors.toList());
    }

    public CompanyResponse create(CompanyRequest companyRequest) {
        savedCompany = companyRepository.save(companyMapper.toEntity(companyRequest));
        return companyMapper.toResponse(savedCompany);
    }

    public CompanyResponse updateCompany(Integer companyId, CompanyRequest companyRequest) {
        companyRepository.findById(companyId).orElseThrow(() -> new CompanyNotFoundException(COMPANY_NOT_FOUND));

        savedCompany = companyRepository.save(companyMapper.toEntity(companyRequest));
        return companyMapper.toResponse(savedCompany);
    }

    public CompanyResponse getCompany(Integer companyId) {
        savedCompany = companyRepository.findById(companyId).orElseThrow(()->
                new CompanyNotFoundException(COMPANY_NOT_FOUND));

        return companyMapper.toResponse(savedCompany);
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
                .orElseThrow(()-> new CompanyNotFoundException(COMPANY_NOT_FOUND));
    }

    public void deleteCompanyEmployees(Integer companyId) {
        List<Employee> employees = companyRepository.findById(companyId)
                .map(Company::getEmployeeList)
                .orElseThrow(()-> new CompanyNotFoundException(COMPANY_NOT_FOUND));

        if(employees != null) {
            employees.stream().forEach(employee -> employeeRepository.deleteById(employee.getId()));
        }
    }
}

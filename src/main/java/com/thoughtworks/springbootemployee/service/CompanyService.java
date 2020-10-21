package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;

import java.util.List;

public class CompanyService {
    private final CompanyRepository

    public List<Employee> getAllCompanies() { return companyRepository.findAllCompanies(); }
}

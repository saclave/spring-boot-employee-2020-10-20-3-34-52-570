package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CompanyIntegrationTest {
    private CompanyRepository companyRepository;
    private EmployeeRepository employeeRepository;
    private CompanyService companyService;

    @BeforeEach
    void setup(){
        companyRepository = mock(CompanyRepository.class);
        employeeRepository = mock(EmployeeRepository.class);
        companyService = new CompanyService(companyRepository, employeeRepository);
    }

    @Test
    void should_return_list_of_companies_when_get_all_companies_given_get_request(){
        //given
        //when
        when(companyRepository.findAll()).thenReturn(asList(new Company(), new Company()));
        List<Company> companyRequests = companyService.getAllCompanies();
        //then
        assertEquals(2, companyRequests.size());
    }
}

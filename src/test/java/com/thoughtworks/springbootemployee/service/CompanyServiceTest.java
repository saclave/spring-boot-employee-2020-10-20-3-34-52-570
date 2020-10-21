package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompanyServiceTest {

    private CompanyRepository companyRepository;
    private CompanyService companyService;

    @BeforeEach
    void setUp() {
        companyRepository = mock(CompanyRepository.class);
        companyService = new CompanyService(companyRepository);

    }

    @Test
    void should_return_list_of_companies_when_get_all_companies_given_get_request() {
        //given

        //when
        when(companyRepository.findAllCompanies()).thenReturn(Collections.singletonList(new Company()));
        List<Company> actual = companyService.getAllCompanies();

        //then
        assertEquals(1, actual.size());
    }

    @Test
    void should_return_created_company_when_given_a_company_request() {
        //given
        //when
        Company companyRequest = new Company(69, "LODS", 18, Collections.singletonList(new Employee()));
        when(companyRepository.saveCompany(companyRequest)).thenReturn(companyRequest);
        Company actual = companyService.create(companyRequest);

        //then
        assertEquals(69, actual.getCompanyId());
    }

    @Test
    void should_return_updated_company_info_when_given_an_update_request() {
        //given
        Company companyRequest = new Company(69, "LODS", 18, Collections.singletonList(new Employee()));
        Employee employee = new Employee();
        //when
        when(companyRepository.saveCompany(companyRequest)).thenReturn(companyRequest);
        companyRequest.setCompanyName("MIS");
        companyRequest.setEmployeeList(Collections.singletonList(employee));
        companyRequest.setNumOfEmployees(20);
        when(companyRepository.updateCompany(companyRequest)).thenReturn(companyRequest);
        Company actual = companyService.updateCompany(companyRequest);

        //then
        assertEquals(69, actual.getCompanyId());
        assertEquals("MIS", actual.getCompanyName());
        assertEquals(20, actual.getNumOfEmployees());
        assertEquals(Collections.singletonList(employee), actual.getEmployeeList());
    }
}

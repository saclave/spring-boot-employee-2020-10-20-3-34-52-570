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
        List<Employee> actual = companyService.getAllCompanies();

        //then
        assertEquals(1, actual.size());
    }
}

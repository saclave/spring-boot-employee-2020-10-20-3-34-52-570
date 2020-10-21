package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
        Company companyRequest = new Company(69, "LODS", 20, Collections.singletonList(new Employee()));
        Employee employee = new Employee();
        Company companyUpdate = new Company(69, "MIS", 18, Collections.singletonList(employee));
        //when
        when(companyRepository.saveCompany(companyRequest)).thenReturn(companyRequest);
        when(companyRepository.updateCompany(companyUpdate)).thenReturn(companyUpdate);
        when(companyRepository.findByCompanyId(companyUpdate.getCompanyId())).thenReturn(companyUpdate);
        Company actual = companyService.updateCompany(companyUpdate.getCompanyId(), companyUpdate);

        //then
        assertEquals(69, actual.getCompanyId());
        assertEquals("MIS", actual.getCompanyName());
        assertEquals(18, actual.getNumOfEmployees());
        assertEquals(Collections.singletonList(employee), actual.getEmployeeList());
    }

    @Test
    void should_return_company_when_get_given_a_company_id() {
        //given
        Company companyRequest = new Company(69, "LODS", 20, Collections.singletonList(new Employee()));
        Company companyUpdate = new Company(69, "MIS", 18, Collections.singletonList(new Employee()));

        //when
        when(companyRepository.saveCompany(companyRequest)).thenReturn(companyRequest);
        when(companyRepository.saveCompany(companyUpdate)).thenReturn(companyUpdate);
        when(companyRepository.findByCompanyId(companyRequest.getCompanyId())).thenReturn(companyRequest);
        Company actual = companyService.getCompany(companyRequest.getCompanyId());

        //then
        assertSame(companyRequest, actual);
    }

    @Test
    void should_remove_employee_when_delete_given_employee_id() {
        //given
        Company companyRequest = new Company(69, "LODS", 20, Collections.singletonList(new Employee()));

        //when
        companyService.deleteCompany(companyRequest.getCompanyId());

        //then
        verify(companyRepository, times(1)).deleteCompany(69);
    }

}

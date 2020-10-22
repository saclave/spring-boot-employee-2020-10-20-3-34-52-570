package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CompanyServiceTest {

    private CompanyRepository companyRepository;
    private EmployeeRepository employeeRepository;
    private CompanyService companyService;

    @BeforeEach
    void setUp() {
        companyRepository = mock(CompanyRepository.class);
        employeeRepository = mock(EmployeeRepository.class);
        companyService = new CompanyService(companyRepository, employeeRepository);
    }

    @Test
    void should_return_list_of_companies_when_get_all_companies_given_get_request() {
        //given
        //when
        when(companyRepository.findAll()).thenReturn(asList(new Company(), new Company()));
        List<Company> companyRequests = companyService.getAllCompanies();
        //then
        assertEquals(2, companyRequests.size());
    }

    @Test
    void should_return_created_company_when_given_a_company_request() {
        //given
        //when
        Company companyRequest = new Company(69, "LODS", Collections.singletonList(new Employee()));
        when(companyRepository.save(companyRequest)).thenReturn(companyRequest);
        Company actual = companyService.create(companyRequest);

        //then
        assertEquals(69, actual.getCompanyId());
    }

    @Test
    void should_return_updated_company_info_when_given_an_update_request() {
        //given
        Employee employee = new Employee();
        Company companyUpdate = new Company(69, "MIS", Collections.singletonList(employee));
        //when
        when(companyRepository.save(companyUpdate)).thenReturn(companyUpdate);
        Company actual = companyService.updateCompany(companyUpdate.getCompanyId(), companyUpdate);

        //then
        assertEquals(69, actual.getCompanyId());
        assertEquals("MIS", actual.getCompanyName());
        assertEquals(1, actual.getNumOfEmployees());
        assertEquals(Collections.singletonList(employee), actual.getEmployeeList());
    }

    @Test
    void should_return_company_when_get_given_a_company_id() {
        //given
        Company companyRequest = new Company(69, "LODS", Collections.singletonList(new Employee()));

        //when
        when(companyRepository.findById(companyRequest.getCompanyId())).thenReturn(java.util.Optional.of(companyRequest));
        Company actual = companyService.getCompany(companyRequest.getCompanyId());

        //then
        assertSame(companyRequest, actual);
    }

    @Test
    void should_remove_employee_when_delete_given_employee_id() {
        //given
        Company companyRequest = new Company(69, "LODS", Collections.singletonList(new Employee()));

        //when
        companyService.deleteCompany(companyRequest.getCompanyId());

        //then
        verify(companyRepository, times(1)).deleteById(69);
    }


    @Test
    void should_return_employees_when_find_given_company_id() {
        //given
        Employee firstEmployee = new Employee(1, "Vea", 22, "Female", 1000000);
        Employee secondEmployee = new Employee(2, "Joseph", 21, "Male", 1000000);
        firstEmployee.setCompanyId(69);
        secondEmployee.setCompanyId(69);
        Company companyRequest = new Company(69, "LODS", asList(firstEmployee, secondEmployee));

        //when
        when(companyRepository.findById(companyRequest.getCompanyId())).thenReturn(java.util.Optional.of(companyRequest));
        List<Employee> actual = companyService.getEmployeesByCompanyId(69);
        //then
        assertSame(2, actual.size());
    }

    @Test
    void should_return_page_1_and_2_for_companies_when_pagination_given_page_size_1_page_size_2() {
        //given
        Company company1 = new Company(69, "LODS", Collections.singletonList(new Employee()));
        Company company2 = new Company(69, "MIS", Collections.singletonList(new Employee()));
        Company company3 = new Company(69, "MIS", Collections.singletonList(new Employee()));
        Page<Company> companyPage = mock(Page.class);
        //when
        when(companyRepository.save(company1)).thenReturn(company1);
        when(companyRepository.save(company2)).thenReturn(company2);
        when(companyRepository.save(company3)).thenReturn(company3);
        when(companyRepository.findAll(PageRequest.of(2, 3))).thenReturn(companyPage);
        when(companyPage.toList()).thenReturn(asList(company2, company3));
        List<Company> actual = companyService.getPaginationByCompany(2, 3);
        assertEquals(2, actual.size());
    }
}




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
import java.util.Optional;

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
        Company companyRequest = new Company("LODS", Collections.singletonList(new Employee()));
        when(companyRepository.save(companyRequest)).thenReturn(companyRequest);
        Company actual = companyService.create(companyRequest);

        //then
        assertEquals("LODS", actual.getCompanyName());
    }

    @Test
    void should_return_updated_company_info_when_given_an_update_request() {
        //given
        Company company = new Company("OOCL", null);
        company.setId(1);
        Company expectedCompany = new Company("LODS", null);
        expectedCompany.setId(1);
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        when(companyRepository.findById(1)).thenReturn(Optional.of(company));
        when(companyRepository.save(company)).thenReturn(expectedCompany);
        CompanyService companyService = new CompanyService(companyRepository, employeeRepository);

        //when
        Company updatedCompany = companyService.updateCompany(company.getId(), expectedCompany);

        //then
        assertSame(expectedCompany, updatedCompany);
    }

    @Test
    void should_return_company_when_get_given_a_company_id() {
        //given
        Company companyRequest = new Company("LODS", Collections.singletonList(new Employee()));

        //when
        when(companyRepository.findById(companyRequest.getId())).thenReturn(java.util.Optional.of(companyRequest));
        Company actual = companyService.getCompany(companyRequest.getId());

        //then
        assertSame(companyRequest, actual);
    }

    @Test
    void should_remove_employee_when_delete_given_employee_id() {
        //given
        Company companyRequest = new Company("LODS", Collections.singletonList(new Employee()));
        companyRequest.setId(69);

        //when
        companyService.deleteCompany(companyRequest.getId());

        //then
        verify(companyRepository, times(1)).deleteById(69);
    }


    @Test
    void should_return_employees_when_find_given_company_id() {
        //given
        Employee firstEmployee = new Employee(1, "Vea", 22, "Female", 1000000);
        Employee secondEmployee = new Employee(2, "Joseph", 21, "Male", 1000000);
        Company companyRequest = new Company("LODS", asList(firstEmployee, secondEmployee));
        companyRequest.setId(69);

        //when
        when(companyRepository.findById(companyRequest.getId())).thenReturn(Optional.of(companyRequest));
        List<Employee> actual = companyService.getEmployeesByCompanyId(69);
        //then
        assertSame(2, actual.size());
    }

    @Test
    void should_return_page_1_and_2_for_companies_when_pagination_given_page_size_1_page_size_2() {
        //given
        Company company1 = new Company("LODS", Collections.singletonList(new Employee()));
        Company company2 = new Company("MIS", Collections.singletonList(new Employee()));
        Company company3 = new Company("MIS", Collections.singletonList(new Employee()));
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




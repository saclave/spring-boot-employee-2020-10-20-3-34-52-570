//package com.thoughtworks.springbootemployee.service;
//
//import com.thoughtworks.springbootemployee.model.Company;
//import com.thoughtworks.springbootemployee.model.Employee;
//import com.thoughtworks.springbootemployee.repository.CompanyRepositoryLegacy;
//import com.thoughtworks.springbootemployee.repository.EmployeeRepositoryLegacy;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.Collections;
//import java.util.List;
//
//import static java.util.Arrays.asList;
//import static org.junit.Assert.assertSame;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//public class CompanyServiceTest {
//
//    private CompanyRepositoryLegacy companyRepositoryLegacy;
//    private CompanyService companyService;
//    private EmployeeRepositoryLegacy employeeRepositoryLegacy;
//
//    @BeforeEach
//    void setUp() {
//        companyRepositoryLegacy = mock(CompanyRepositoryLegacy.class);
//        employeeRepositoryLegacy = mock(EmployeeRepositoryLegacy.class);
//        companyService = new CompanyService(companyRepositoryLegacy, employeeRepositoryLegacy);
//    }
//
//    @Test
//    void should_return_list_of_companies_when_get_all_companies_given_get_request() {
//        //given
//
//        //when
//        when(companyRepositoryLegacy.findAllCompanies()).thenReturn(Collections.singletonList(new Company()));
//        List<Company> actual = companyService.getAllCompanies();
//
//        //then
//        assertEquals(1, actual.size());
//    }
//
//    @Test
//    void should_return_created_company_when_given_a_company_request() {
//        //given
//        //when
//        Company companyRequest = new Company(69, "LODS", Collections.singletonList(new Employee()));
//        when(companyRepositoryLegacy.saveCompany(companyRequest)).thenReturn(companyRequest);
//        Company actual = companyService.create(companyRequest);
//
//        //then
//        assertEquals(69, actual.getCompanyId());
//    }
//
//    @Test
//    void should_return_updated_company_info_when_given_an_update_request() {
//        //given
//        Employee employee = new Employee();
//        Company companyUpdate = new Company(69, "MIS", Collections.singletonList(employee));
//        //when
//        when(companyRepositoryLegacy.findByCompanyId(companyUpdate.getCompanyId())).thenReturn(companyUpdate);
//        Company actual = companyService.updateCompany(companyUpdate.getCompanyId(), companyUpdate);
//
//        //then
//        assertEquals(69, actual.getCompanyId());
//        assertEquals("MIS", actual.getCompanyName());
//        assertEquals(1, actual.getNumOfEmployees());
//        assertEquals(Collections.singletonList(employee), actual.getEmployeeList());
//    }
//
//    @Test
//    void should_return_company_when_get_given_a_company_id() {
//        //given
//        Company companyRequest = new Company(69, "LODS", Collections.singletonList(new Employee()));
//
//        //when
//        when(companyRepositoryLegacy.findByCompanyId(companyRequest.getCompanyId())).thenReturn(companyRequest);
//        Company actual = companyService.getCompany(companyRequest.getCompanyId());
//
//        //then
//        assertSame(companyRequest, actual);
//    }
//
//    @Test
//    void should_remove_employee_when_delete_given_employee_id() {
//        //given
//        Company companyRequest = new Company(69, "LODS", Collections.singletonList(new Employee()));
//
//        //when
//        companyService.deleteCompany(companyRequest.getCompanyId());
//
//        //then
//        verify(companyRepositoryLegacy, times(1)).deleteCompany(69);
//    }
//
//
//    @Test
//    void should_return_employees_when_find_given_company_id() {
//        //given
//        Employee firstEmployee = new Employee(1, "Vea", 22, "Female", 1000000);
//        Employee secondEmployee = new Employee(2, "Joseph", 21, "Male", 1000000);
//        firstEmployee.setCompanyId(69);
//        secondEmployee.setCompanyId(69);
//        Company companyRequest = new Company(69, "LODS", asList(firstEmployee, secondEmployee));
//
//        //when
//        when(employeeRepositoryLegacy.findEmployeesByCompanyId(companyRequest.getCompanyId())).thenReturn(asList(firstEmployee, secondEmployee));
//        List<Employee> actual = companyService.getEmployeesByCompanyId(69);
//        //then
//        assertSame(2, actual.size());
//    }
//
//    @Test
//    void should_return_page_1_and_2_for_companies_when_pagination_given_page_size_1_page_size_2() {
//        //given
//        Company company1 = new Company(69, "LODS", Collections.singletonList(new Employee()));
//        Company company2 = new Company(69, "MIS", Collections.singletonList(new Employee()));
//        Company company3 = new Company(69, "MIS", Collections.singletonList(new Employee()));
//
//        //when
//        when(companyRepositoryLegacy.findAllCompanies()).thenReturn(asList(company1, company2, company3));
//        when(companyRepositoryLegacy.findCompanyPagination(2L, 3L)).thenReturn(asList(company2, company3));
//
//        List<Company> actual = companyService.getPaginationByCompany(2L, 3L);
//        assertEquals(2, actual.size());
//    }
//}

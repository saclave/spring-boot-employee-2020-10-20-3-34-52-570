package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CompanyIntegrationTest {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        CompanyCleanup.tearDown(companyRepository);
        CompanyCleanup.tearDown(employeeRepository);
    }

    @Test
    void should_return_list_of_companies_when_get_all_companies_given_get_request() throws Exception {
        //given
        Employee employeeRequest = new Employee(1, "joseph", 22, "male", 1000000);
        employeeRepository.save(employeeRequest);

        Company companyRequest = new Company(1, "LODS", Collections.singletonList(employeeRequest));
        companyRepository.save(companyRequest);

        //when
        //then
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].companyId").isNumber())
                .andExpect(jsonPath("$[0].companyName").value("LODS"));
    }

    @Test
    void should_create_company_when_create_given_company_request() throws Exception {
        // given
        Employee employee = new Employee(2, "oblaks", 21, "male", 25000);
        employeeRepository.save(employee);

        String employeeAsJson = "{\n" +
                "    \"companyId\": 1,\n" +
                "    \"companyName\": \"oblaks\",\n" +
                "    \"employeeList\": [\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"companyId\": null,\n" +
                "            \"name\": \"oblaks\",\n" +
                "            \"age\": 21,\n" +
                "            \"gender\": \"male\",\n" +
                "            \"salary\": 25000\n" +
                "        }\n" +
                "    ],\n" +
                "    \"numOfEmployees\": 1\n" +
                "}";

        // when
        // then
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeAsJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].companyId").isNumber())
                .andExpect(jsonPath("$[0].companyName").value("oblaks"))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("oblaks"))
                .andExpect(jsonPath("$.age").value(21))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(25000))
                .andExpect(jsonPath("$.numOfEmployees").value(1));

        List<Company> companies = companyRepository.findAll();
        Assertions.assertEquals(1, companies.size());
    }

    @Test
    void should_return_company_when_get_specific_company_given_get_company_request() throws Exception {
        //given
        Employee employeeRequest = employeeRepository.save(new Employee(1, "joseph", 22, "male", 1000000));

        Company companyRequest = companyRepository.save(new Company(1, "LODS", Collections.singletonList(employeeRequest)));

        //when
        //then
        mockMvc.perform(get("/companies/" + companyRequest.getCompanyId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyId").isNumber())
                .andExpect(jsonPath("$.companyName").value("LODS"));
    }

    @Test
    void should_return_specific_employees_when_get_employees_under_company_given_get_company_employee_request() throws Exception {
        //given
        Employee employeeRequestOne = employeeRepository.save(new Employee(1, "joseph", 22, "male", 1000000));
        Employee employeeRequestTwo = employeeRepository.save(new Employee(2, "maria", 18, "female", 20000));
        Employee employeeRequestDiff = employeeRepository.save(new Employee(3, "jerick", 27, "male", 500));

        Company companyRequest = companyRepository.save(new Company(1, "LODS", Arrays.asList(employeeRequestOne, employeeRequestTwo)));

        //when
        //then
        mockMvc.perform(get("/companies/" + companyRequest.getCompanyId() + "/employeeList"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("joseph"))
                .andExpect(jsonPath("$[0].age").value(22))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(1000000))
                .andExpect(jsonPath("$[1].id").isNumber())
                .andExpect(jsonPath("$[1].name").value("maria"))
                .andExpect(jsonPath("$[1].age").value(18))
                .andExpect(jsonPath("$[1].gender").value("female"))
                .andExpect(jsonPath("$[1].salary").value(20000))
                .andExpect(jsonPath("$[2].id").doesNotExist());
        List<Employee> employees = employeeRepository.findAll();
        Assertions.assertEquals(3, employees.size());
    }

//    @Test
//    void should_update_company_when_put_specific_company_given_put_company_request_and_details() throws Exception {
//        //given
//        Employee employeeRequest = employeeRepository.save(new Employee(1, "joseph", 22, "male", 1000000));
//
//        Company companyRequest = companyRepository.save(new Company(1, "LODS", Collections.singletonList(employeeRequest)));
//        String updatedCompanyAsJson = "{\n" +
//                "  \"companyName\": \"maria\"\n" +
//                "}";
//
//        //when
//        //then
//        mockMvc.perform(put("/companies/" + companyRequest.getCompanyId())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(updatedCompanyAsJson))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].companyId").isNumber())
//                .andExpect(jsonPath("$[0].companyName").value("LODS"));
//    }

    @Test
    void should_delete_company_when_delete_request_given_delete_company_request() throws Exception {
        //given
        Employee employeeRequest = employeeRepository.save(new Employee(1, "joseph", 22, "male", 1000000));

        Company companyRequest = companyRepository.save(new Company(1, "LODS", Collections.singletonList(employeeRequest)));

        //when
        //then
        mockMvc.perform(delete("/companies/" + companyRequest.getCompanyId()))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$[0].companyId").doesNotExist())
                .andExpect(jsonPath("$[0].companyName").doesNotExist());
    }

    @Test
    void should_return_page_1_and_2_for_companies_when_pagination_given_page_size_1_page_size_2() throws Exception {
        //given
        Employee maleEmployeeOne = new Employee(1, "joseph", 22, "male", 1000000);
        Employee femaleEmployeeOne = new Employee(2, "maria", 19, "female", 200000);
        Employee maleEmployeeTwo = new Employee(3, "jerick", 25, "male", 500);
        employeeRepository.save(maleEmployeeOne);
        employeeRepository.save(femaleEmployeeOne);
        employeeRepository.save(maleEmployeeTwo);

        Company companyOne = new Company(1, "LODS", Collections.singletonList(maleEmployeeOne));
        Company companyTwo = new Company(2, "ITS", Collections.singletonList(femaleEmployeeOne));
        Company companyThree = new Company(3, "ADDS", Collections.singletonList(maleEmployeeTwo));
        companyRepository.save(companyOne);
        companyRepository.save(companyTwo);
        companyRepository.save(companyThree);

        // when
        // then
        mockMvc.perform(get("/companies?page=0&pageSize=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].companyId").isNumber())
                .andExpect(jsonPath("$[0].companyName").value("LODS"))
                .andExpect(jsonPath("$[1].companyId").isNumber())
                .andExpect(jsonPath("$[1].companyName").value("ITS"))
                .andExpect(jsonPath("$[2].id").doesNotExist());

        List<Company> companies = companyRepository.findAll();
        Assertions.assertEquals(3, companies.size());
    }

}

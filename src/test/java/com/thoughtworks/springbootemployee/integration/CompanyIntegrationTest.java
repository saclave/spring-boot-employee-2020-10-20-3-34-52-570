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

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
}

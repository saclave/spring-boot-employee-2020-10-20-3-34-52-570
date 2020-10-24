package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

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
        companyRepository.deleteAll();
    }

    @Test
    void should_return_list_of_companies_when_get_all_companies_given_get_request() throws Exception {
        //given
        Company company = new Company("LODS", Collections.emptyList());

        //when
        companyRepository.save(company);

        //then
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].companyName").value("LODS"))
                .andExpect(jsonPath("$[0].employeeList").isEmpty());
    }

    @Test
    void should_return_company_when_get_specific_company_given_get_company_request() throws Exception {
        //given
        Company company = new Company("LODS", Collections.emptyList());

        //when
        Company createdCompany = companyRepository.save(company);

        //then
        mockMvc.perform(get("/companies/{id}", createdCompany.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.companyName").value("LODS"))
                .andExpect(jsonPath("$.employeeList").isEmpty());
    }

    @Test
    void should_return_specific_employees_when_get_employees_under_company_given_get_company_employee_request() throws Exception {
        String companyJson = "{\n" +
                "    \"companyName\": \"LODS\",\n" +
                "    \"employeeList\": [\n" +
                "        {\n" +
                "            \"name\": \"prince\",\n" +
                "            \"age\": 22,\n" +
                "            \"gender\": \"male\",\n" +
                "            \"salary\": 250000\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"vea\",\n" +
                "            \"age\": 21,\n" +
                "            \"gender\": \"female\",\n" +
                "            \"salary\": 250000\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        //when then
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.companyName").value("LODS"))
                .andExpect(jsonPath("$.employeeList").isNotEmpty())
                .andExpect(jsonPath("$.employeeList[0].id").isNumber())
                .andExpect(jsonPath("$.employeeList[0].name").value("prince"))
                .andExpect(jsonPath("$.employeeList[0].age").value(22))
                .andExpect(jsonPath("$.employeeList[0].gender").value("male"))
                .andExpect(jsonPath("$.employeeList[0].salary").value(250000))
                .andExpect(jsonPath("$.employeeList[1].id").isNumber())
                .andExpect(jsonPath("$.employeeList[1].name").value("vea"))
                .andExpect(jsonPath("$.employeeList[1].age").value(21))
                .andExpect(jsonPath("$.employeeList[1].gender").value("female"))
                .andExpect(jsonPath("$.employeeList[1].salary").value(250000));
    }

//    @Test
//    void should_delete_company_when_delete_request_given_delete_company_request() throws Exception {
//        //given
//        Employee employeeRequest = employeeRepository.save(new Employee(7, "joseph", 22, "male", 1000000));
//
//        Company companyRequest = companyRepository.save(new Company(7, "LODS", Collections.singletonList(employeeRequest)));
//
//        //when
//        //then
//        mockMvc.perform(delete("/companies/" + companyRequest.getCompanyId()))
//                .andExpect(status().isMethodNotAllowed())
//                .andExpect(jsonPath("$[0].companyId").doesNotExist())
//                .andExpect(jsonPath("$[0].companyName").doesNotExist());
//    }
//
//    @Test
//    void should_return_page_1_and_2_for_companies_when_pagination_given_page_size_1_page_size_2() throws Exception {
//        //given
//        Employee maleEmployeeOne = employeeRepository.save(new Employee(8, "joseph", 22, "male", 1000000));
//        Employee femaleEmployeeOne = employeeRepository.save(new Employee(9, "maria", 19, "female", 200000));
//        Employee maleEmployeeTwo = employeeRepository.save(new Employee(10, "jerick", 25, "male", 500));
//
//        Company companyOne = new Company(8, "LODS", Collections.singletonList(maleEmployeeOne));
//        Company companyTwo = new Company(9, "ITS", Collections.singletonList(femaleEmployeeOne));
//        Company companyThree = new Company(10, "ADDS", Collections.singletonList(maleEmployeeTwo));
//        companyRepository.save(companyOne);
//        companyRepository.save(companyTwo);
//        companyRepository.save(companyThree);
//
//        // when
//        // then
//        mockMvc.perform(get("/companies?page=0&pageSize=2"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].companyId").isNumber())
//                .andExpect(jsonPath("$[0].companyName").value("LODS"))
//                .andExpect(jsonPath("$[1].companyId").isNumber())
//                .andExpect(jsonPath("$[1].companyName").value("ITS"))
//                .andExpect(jsonPath("$[2].id").doesNotExist());
//
//        List<Company> companies = companyRepository.findAll();
//        Assertions.assertEquals(3, companies.size());
//    }

}

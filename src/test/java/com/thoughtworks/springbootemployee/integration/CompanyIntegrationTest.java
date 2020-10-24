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
    private Company company;
    private Company createdCompany;

    @AfterEach
    void tearDown() {
        companyRepository.deleteAll();
    }

    @Test
    void should_return_list_of_companies_when_get_all_companies_given_get_request() throws Exception {
        //given
        company = new Company("LODS", Collections.emptyList());

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
        company = new Company("LODS", Collections.emptyList());

        //when
        createdCompany = companyRepository.save(company);

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

        //then
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

    @Test
    void should_return_page_1_and_2_for_companies_when_pagination_given_page_size_1_page_size_2() throws Exception {
        //given
        Company company1 = new Company("LODS", Collections.emptyList());
        Company company2 = new Company("ITS", Collections.emptyList());
        Company company3 = new Company("ORS", Collections.emptyList());

        companyRepository.save(company1);
        companyRepository.save(company2);
        companyRepository.save(company3);

       //then
        mockMvc.perform(get("/companies?page=0&pageSize=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].companyName").value("LODS"))
                .andExpect(jsonPath("$[0].employeeList").isEmpty())
                .andExpect(jsonPath("$[1].id").isNumber())
                .andExpect(jsonPath("$[1].companyName").value("ITS"))
                .andExpect(jsonPath("$[1].employeeList").isEmpty());
    }

    @Test
    void should_get_given_updated_company_name_when_update_company_id() throws Exception {
        //given
        company = new Company("OOCL", Collections.emptyList());
        createdCompany = companyRepository.save(company);

        String companyUpdateJson = "{\"companyName\": \"LODS\"}";

        //then
        mockMvc.perform(put("/companies/{companyId}", createdCompany.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyUpdateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.companyName").value("LODS"))
                .andExpect(jsonPath("$.employeeList").isEmpty());
    }

}

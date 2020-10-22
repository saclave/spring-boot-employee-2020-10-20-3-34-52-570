package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepositoryLegacy;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeIntegrationTest {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        EmployeeCleanup.tearDown(employeeRepository);
    }

    @Test
    void should_return_list_of_employees_when_get_all_employees_given_get_request() throws Exception {
        //given
        Employee employee = new Employee(1, "joseph", 22, "male", 1000000);
        employeeRepository.save(employee);

        //when
        //then
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("joseph"))
                .andExpect(jsonPath("$[0].age").value(22))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(1000000));
    }

    @Test
    void should_create_employee_when_create_given_employee_request() throws Exception {
        // given
        String employeeAsJson = "{\n" +
                "  \"name\": \"joseph\",\n" +
                "  \"age\": 22,\n" +
                "  \"gender\": \"male\",\n" +
                "  \"salary\": 1000000\n" +
                "}";

        // when
        // then
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeAsJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("joseph"))
                .andExpect(jsonPath("$.age").value(22))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(1000000));

        List<Employee> employees = employeeRepository.findAll();
        Assertions.assertEquals(1, employees.size());
    }

    @Test
    void should_return_employee_when_get_specific_employee_given_get_employee_request() throws Exception {
        //given
        Employee employee = employeeRepository.save(new Employee(1, "joseph", 22, "male", 1000000));

        //when
        //then
        mockMvc.perform(get("/employees/" + employee.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("joseph"))
                .andExpect(jsonPath("$.age").value(22))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(1000000));
    }

    @Test
    void should_update_employee_when_put_specific_employee_given_put_employee_request_and_details() throws Exception {
        //given
        Employee employee = employeeRepository.save(new Employee(1, "joseph", 22, "male", 1000000));
        String updatedEmployeeAsJson = "{\n" +
                "  \"name\": \"maria\",\n" +
                "  \"age\": 19,\n" +
                "  \"gender\": \"female\",\n" +
                "  \"salary\": 200000\n" +
                "}";

        //when
        //then
        mockMvc.perform(put("/employees/" + employee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedEmployeeAsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("maria"))
                .andExpect(jsonPath("$.age").value(19))
                .andExpect(jsonPath("$.gender").value("female"))
                .andExpect(jsonPath("$.salary").value(200000));
    }

    @Test
    void should_delete_employee_when_delete_request_given_delete_employee_request() throws Exception {
        //given
        Employee employee = employeeRepository.save(new Employee(1, "joseph", 22, "male", 1000000));

        //when
        //then
        mockMvc.perform(delete("/employees/" + employee.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").doesNotExist())
                .andExpect(jsonPath("$[0].name").doesNotExist())
                .andExpect(jsonPath("$[0].age").doesNotExist())
                .andExpect(jsonPath("$[0].gender").doesNotExist())
                .andExpect(jsonPath("$[0].salary").doesNotExist());
    }

    @Test
    void should_return_list_of_specific_employees_when_get_employees_given_get_request_for_specific_gender() throws Exception {
        //given
        Employee maleEmployeeOne = new Employee(1, "joseph", 22, "male", 1000000);
        Employee femaleEmployeeOne = new Employee(2, "maria", 19, "female", 200000);
        Employee maleEmployeeTwo = new Employee(3, "jerick", 25, "male", 500);
        employeeRepository.save(maleEmployeeOne);
        employeeRepository.save(femaleEmployeeOne);
        employeeRepository.save(maleEmployeeTwo);

        //when
        //then
        mockMvc.perform(get("/employees?gender=male"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("joseph"))
                .andExpect(jsonPath("$[0].age").value(22))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(1000000))
                .andExpect(jsonPath("$[1].id").isNumber())
                .andExpect(jsonPath("$[1].name").value("jerick"))
                .andExpect(jsonPath("$[1].age").value(25))
                .andExpect(jsonPath("$[1].gender").value("male"))
                .andExpect(jsonPath("$[1].salary").value(500))
                .andExpect(jsonPath("$[2].id").doesNotExist());

        List<Employee> employees = employeeRepository.findAll();
        Assertions.assertEquals(3, employees.size());
    }

    @Test
    void should_return_page_1_and_2_for_employees_when_pagination_given_page_size_1_page_size_2() throws Exception {
        //given
        Employee maleEmployeeOne = new Employee(1, "joseph", 22, "male", 1000000);
        Employee femaleEmployeeOne = new Employee(2, "maria", 19, "female", 200000);
        Employee maleEmployeeTwo = new Employee(3, "jerick", 25, "male", 500);
        employeeRepository.save(maleEmployeeOne);
        employeeRepository.save(femaleEmployeeOne);
        employeeRepository.save(maleEmployeeTwo);

        // when
        // then
        mockMvc.perform(get("/employees?page=0&pageSize=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("joseph"))
                .andExpect(jsonPath("$[0].age").value(22))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(1000000))
                .andExpect(jsonPath("$[1].id").isNumber())
                .andExpect(jsonPath("$[1].name").value("maria"))
                .andExpect(jsonPath("$[1].age").value(19))
                .andExpect(jsonPath("$[1].gender").value("female"))
                .andExpect(jsonPath("$[1].salary").value(200000))
                .andExpect(jsonPath("$[2].id").doesNotExist());

        List<Employee> employees = employeeRepository.findAll();
        Assertions.assertEquals(3, employees.size());
    }

}

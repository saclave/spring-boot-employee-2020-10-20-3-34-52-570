package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EmployeeServiceTest {

    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeService(employeeRepository);
    }

    @Test
    void should_return_list_of_employees_when_get_all_employees_given_get_request() {
        //given

        //when
        when(employeeRepository.findAllEmployees()).thenReturn(Collections.singletonList(new Employee()));
        List<Employee> actual = employeeService.getAllEmployees();

        //then
        assertEquals(1, actual.size());
    }

    @Test
    void should_return_created_employee_when_given_an_employee_request() {
        //given
        //when
        Employee employeeRequest = new Employee(1, "Dlo", 23, "Male", 37000000);
        when(employeeRepository.saveEmployee(employeeRequest)).thenReturn(employeeRequest);
        Employee actual = employeeService.create(employeeRequest);

        //then
        assertEquals(1, actual.getId());
    }

    @Test
    void should_return_updated_employee_info_when_given_an_update_request() {
        //given
        Employee employeeRequest = new Employee(1, "Dlo", 23, "Male", 37000000);

        //when
        when(employeeRepository.saveEmployee(employeeRequest)).thenReturn(employeeRequest);
        employeeRequest.setName("Vea");
        employeeRequest.setAge(23);
        employeeRequest.setGender("Female");
        employeeRequest.setSalary(25000);
        when(employeeRepository.updateEmployee(employeeRequest)).thenReturn(employeeRequest);
        Employee actual = employeeService.update(employeeRequest);

        //then
        assertEquals(1, actual.getId());
        assertEquals("Vea", actual.getName());
        assertEquals(23, actual.getAge());
        assertEquals("Female", actual.getGender());
        assertEquals(25000, actual.getSalary());
    }

    @Test
    void should_return_employee_when_get_given_a_employee_id() {
        //given
        Employee vea = new Employee(1, "Vea", 22, "Female", 1000000);
        Employee joseph = new Employee(2, "Joseph", 21, "Male", 1000000);

        //when
        when(employeeRepository.saveEmployee(vea)).thenReturn(vea);
        when(employeeRepository.saveEmployee(joseph)).thenReturn(joseph);
        when(employeeRepository.findEmployeeById(1)).thenReturn(vea);
        Employee actual = employeeService.getEmployee(1);

        //then
        assertSame(vea, actual);
    }
}
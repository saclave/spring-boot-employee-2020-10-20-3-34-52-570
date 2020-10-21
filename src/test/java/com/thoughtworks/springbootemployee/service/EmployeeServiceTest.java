package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EmployeeServiceTest {
    @Test
    void should_return_list_of_employees_when_get_all_employees_given_get_request() {
        //given
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        //when
        when(employeeRepository.findAllEmployees()).thenReturn(Collections.singletonList(new Employee()));

        //then
        List<Employee> actual = employeeService.getAllEmployees();
        assertEquals(1, actual.size());
    }
}
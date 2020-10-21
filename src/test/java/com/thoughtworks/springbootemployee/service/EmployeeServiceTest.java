package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        List<Employee> actual = employeeService.getAllEmployees();

        //then
        assertEquals(1, actual.size());
    }

    @Test
    void should_return_created_employee_when_given_an_employee_request() {
        //given
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        //when
        Employee employeeRequest = new Employee(1, "Dlo", 23, "Male", 37000000);
        when(employeeRepository.saveEmployee(employeeRequest)).thenReturn(employeeRequest);
        Employee actual = employeeService.create(employeeRequest);

        //then
        assertEquals(1, actual.getId());
    }

    @Test
    void should_return_updated_employee_name_when_given_an_update_request() {
        //given
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee employeeRequest = new Employee(1, "Dlo", 23, "Male", 37000000);

        //when
        when(employeeRepository.saveEmployee(employeeRequest)).thenReturn(employeeRequest);
        employeeRequest.setName("Joseph");
        when(employeeRepository.updateEmployee(employeeRequest)).thenReturn(employeeRequest);
        Employee actual = employeeService.update(employeeRequest);

        //then
        assertEquals("Joseph", actual.getName());
    }
}
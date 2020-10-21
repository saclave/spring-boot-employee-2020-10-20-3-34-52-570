package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

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
        Employee employee = new Employee(1, "Dlo", 21, "Male", 37000000);
        Employee employeeUpdate = new Employee(1, "Vea", 23, "Female", 25000);

        //when
        when(employeeRepository.findAllEmployees()).thenReturn(Collections.singletonList(employee));
        when(employeeRepository.updateEmployee(employeeUpdate)).thenReturn(employeeUpdate);
        when(employeeRepository.findEmployeeById(employeeUpdate.getId())).thenReturn(employeeUpdate);
        Employee actual = employeeService.update(employeeUpdate.getId(), employeeUpdate);

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
        Employee firstEmployee = new Employee(1, "Vea", 22, "Female", 1000000);
        Employee secondEmployee = new Employee(2, "Joseph", 21, "Male", 1000000);

        //when
        when(employeeRepository.findAllEmployees()).thenReturn(asList(firstEmployee, secondEmployee));
        when(employeeRepository.findEmployeeById(firstEmployee.getId())).thenReturn(firstEmployee);
        Employee actual = employeeService.getEmployee(firstEmployee.getId());

        //then
        assertSame(firstEmployee, actual);
    }

    @Test
    void should_remove_employee_when_delete_given_employee_id() {
        //given
        Employee employee = new Employee(1, "Vea", 22, "Female", 1000000);

        //when
        employeeService.deleteEmployee(employee.getId());

        //then
        verify(employeeRepository, times(1)).deleteEmployee(1);
    }

    @Test
    void should_return_male_employees_when_filtered_by_gender_given_male() {
        //given
        Employee firstEmployee = new Employee(1, "Vea", 22, "Female", 1000000);
        Employee secondEmployee = new Employee(2, "Joseph", 21, "Male", 1000000);

        //when
        when(employeeRepository.findAllEmployees()).thenReturn(asList(firstEmployee, secondEmployee));
        when(employeeRepository.findEmployeesByGender("Male")).thenReturn(Collections.singletonList(secondEmployee));
        List<Employee> actual = employeeService.getEmployeesByGender("Male");

        //then
        assertEquals(1, actual.size());
    }

    @Test
    void should_return_page_1_and_2_for_employees_when_pagination_given_page_size_1_page_size_2() {
        //given
        Employee firstEmployee = new Employee(1, "Vea", 22, "Female", 1000000);
        Employee secondEmployee = new Employee(2, "Joseph", 21, "Male", 1000000);
        Employee thirdEmployee = new Employee(3, "Dlo", 23, "Male", 37000000);

        //when
        when(employeeRepository.findAllEmployees()).thenReturn(asList(firstEmployee, secondEmployee, thirdEmployee));
        when(employeeRepository.findEmployeesByPagination(2L, 3L)).thenReturn(asList(secondEmployee, thirdEmployee));

        List<Employee> actual = employeeService.getPaginatedEmployee(2L, 3L);
        assertEquals(2, actual.size());
    }
}
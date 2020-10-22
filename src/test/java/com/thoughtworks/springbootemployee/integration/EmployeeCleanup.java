package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.repository.EmployeeRepository;

public class EmployeeCleanup {

    public static void tearDown(EmployeeRepository employeeRepository) {
        employeeRepository.deleteAll();
    }

}

package com.thoughtworks.springbootemployee.model;

import java.util.List;

public class Company {
    private String companyName;
    private int numOfEmployees;

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    private List<Employee> employeeList;
}

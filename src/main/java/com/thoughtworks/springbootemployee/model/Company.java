package com.thoughtworks.springbootemployee.model;

import java.util.List;

public class Company {
    private Integer companyId;

    public Company(Integer companyId, String companyName, Integer numOfEmployees, List<Employee> employeeList) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.numOfEmployees = numOfEmployees;
        this.employeeList = employeeList;
    }

    public Company(){}

    private String companyName;
    private int numOfEmployees;
    private List<Employee> employeeList;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getNumOfEmployees() {
        return numOfEmployees;
    }

    public void setNumOfEmployees(int numOfEmployees) {
        this.numOfEmployees = numOfEmployees;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
}

package com.thoughtworks.springbootemployee.dto;

import com.thoughtworks.springbootemployee.model.Employee;

import java.util.List;

public class CompanyResponse {
    private Integer id;
    private String companyName;
    private Integer numOfEmployees;
    private List<Employee> employeeList;

    public CompanyResponse(String companyName, List<Employee> employeeList) {
        this.companyName = companyName;
        this.employeeList = employeeList;
        numOfEmployees = this.employeeList.size();
    }

    public CompanyResponse() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getNumOfEmployees() {
        return numOfEmployees;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
}

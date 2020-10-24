package com.thoughtworks.springbootemployee.model;

import java.util.List;

public class CompanyResponse {
    private Integer id;
    private String companyName;
    private List<Employee> employeeList;

    public CompanyResponse(String companyName, List<Employee> employeeList) {
        this.companyName = companyName;
        this.employeeList = employeeList;
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
        return employeeList.size();
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

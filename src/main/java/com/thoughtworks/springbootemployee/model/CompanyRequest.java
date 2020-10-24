package com.thoughtworks.springbootemployee.model;

import javax.persistence.*;
import java.util.List;

public class CompanyRequest {
    private String companyName;
    private List<Employee> employeeList;

    public CompanyRequest() {
    }

    public CompanyRequest(String companyName, List<Employee> employeeList) {
        this.companyName = companyName;
        this.employeeList = employeeList;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setEmployeeList(List<Employee> employees) {
        this.employeeList = employees;
    }

    public List<Employee> getEmployeeList() { return employeeList; }

    public int getNumOfEmployees() {
        return employeeList.size();
    }

}

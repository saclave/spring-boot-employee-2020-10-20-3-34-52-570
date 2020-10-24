package com.thoughtworks.springbootemployee.dto;

import com.thoughtworks.springbootemployee.model.Employee;

import javax.persistence.*;
import java.util.List;

public class CompanyResponse {
    private Integer companyId;
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

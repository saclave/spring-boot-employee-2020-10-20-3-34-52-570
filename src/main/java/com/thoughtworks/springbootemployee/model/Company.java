package com.thoughtworks.springbootemployee.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Company {
    @Id
    private Integer companyId;
    private String companyName;
    @OneToMany(
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @JoinColumn(name = "companyId")
    private List<Employee> employeeList;

    public Company(Integer companyId, String companyName, List<Employee> employeeList) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.employeeList = employeeList;
    }

    public Company() {
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

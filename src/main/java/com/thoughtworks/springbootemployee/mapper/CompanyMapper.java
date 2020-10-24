package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.CompanyRequest;
import com.thoughtworks.springbootemployee.model.CompanyResponse;

public class CompanyMapper {

    private Company company;

    public CompanyResponse toResponse(Company company){
        CompanyResponse response = new CompanyResponse();

        response.setNumOfEmployees(company.getNumOfEmployees());
        response.setCompanyId(company.getCompanyId());
        response.setCompanyName(company.getCompanyName());
        response.setEmployeeList(company.getEmployeeList());

        return response;
    }

    public Company toEntity(CompanyRequest request){
        if(request == null) {
           company = new Company();
        }
        company.setCompanyName(request.getCompanyName());

        return  company;
    }

}

package com.thoughtworks.springbootemployee.model;

import javax.persistence.*;
import java.util.List;

public class CompanyRequest {
    private String companyName;

    public CompanyRequest(String companyName) {
        this.companyName = companyName;
    }

    public CompanyRequest() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}

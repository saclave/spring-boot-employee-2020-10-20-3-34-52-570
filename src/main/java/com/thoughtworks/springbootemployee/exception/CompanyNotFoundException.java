package com.thoughtworks.springbootemployee.exception;

public class CompanyNotFoundException extends RuntimeException{
    public static final String COMPANY_NOT_FOUND = "Company Not Found";

    public CompanyNotFoundException(String message) {
        super(message);
    }
}

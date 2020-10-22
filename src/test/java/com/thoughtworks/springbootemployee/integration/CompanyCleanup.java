package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.repository.CompanyRepository;

public class CompanyCleanup {
    public static void tearDown(CompanyRepository companyRepository) {
        companyRepository.deleteAll();
    }

}

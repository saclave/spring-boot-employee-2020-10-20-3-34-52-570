package com.thoughtworks.springbootemployee.integration;

import org.springframework.data.jpa.repository.JpaRepository;

public class CompanyCleanup {
    public static void tearDown(JpaRepository companyRepository) {
        companyRepository.deleteAll();
    }

}

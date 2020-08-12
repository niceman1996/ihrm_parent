package com.ihrm.company.service;

import com.ihrm.domain.company.Company;

import java.util.List;

public interface ICompanyService {
    public void addCompany(Company company);
    public void deleteCompanyById(String id);
    public void  updateCompany(Company company);
    public Company findCompanyById(String id);
    public List<Company> findAllCompany();
}

package com.ihrm.company.service;

import com.ihrm.domain.company.Department;

import java.util.List;

public interface IDepartmentService {
    void addDepartment(Department department);
    void deleteDepartment(String id);
    void updateDepartment(Department department);
    List<Department> findAllDepartment(String companyId);
    Department findDepartmentById(String id);
}

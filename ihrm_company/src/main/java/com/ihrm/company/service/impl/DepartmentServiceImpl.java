package com.ihrm.company.service.impl;

import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.company.dao.DepartmentDao;
import com.ihrm.company.service.IDepartmentService;
import com.ihrm.domain.company.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


import java.util.List;

@Service
public class DepartmentServiceImpl extends BaseService implements IDepartmentService {
    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private IdWorker idWorker;
    public void addDepartment(Department department){
        department.setId(idWorker.nextId()+"");
        departmentDao.save(department);
    }
    public void deleteDepartment(String id){
        departmentDao.deleteById(id);
    }
    public void updateDepartment(Department department){
        Department tempDepartment = departmentDao.findById(department.getId()).get();
        if (!ObjectUtils.isEmpty(tempDepartment)){
            tempDepartment.setName(department.getName());
            tempDepartment.setCode(department.getCode());
            tempDepartment.setIntroduce(department.getIntroduce());
            tempDepartment.setManager(department.getManager());
        }
        departmentDao.save(tempDepartment);
    }
    public List<Department> findAllDepartment(String companyId){
        BaseService<Department> baseService = new BaseService();
        Specification spec = baseService.getSpec(companyId);
        return  departmentDao.findAll(spec);

    }
    public Department findDepartmentById(String id){
        return  departmentDao.findById(id).get();
    }
}

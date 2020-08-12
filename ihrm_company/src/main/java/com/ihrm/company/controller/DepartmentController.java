package com.ihrm.company.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.company.service.ICompanyService;
import com.ihrm.company.service.IDepartmentService;
import com.ihrm.company.service.impl.CompanyServiceImpl;
import com.ihrm.company.service.impl.DepartmentServiceImpl;
import com.ihrm.domain.company.Company;
import com.ihrm.domain.company.Department;
import com.ihrm.domain.company.response.DeptListResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@CrossOrigin
@RequestMapping("/company")
public class DepartmentController extends BaseController {
    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private ICompanyService companyService;

    Logger logger = LoggerFactory.getLogger(DepartmentController.class);
    @RequestMapping(value = "/department",method = POST)
    public Result addDepartment(@RequestBody Department department){
        department.setCompanyId(companyId);
        departmentService.addDepartment(department);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/department/{departmentId}",method = DELETE)
    public Result deleteDepartment(@PathVariable("departmentId") String departmentId){
        departmentService.deleteDepartment(departmentId);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/department/{departmentId}",method = PUT)
    public Result updateDepartment(@PathVariable("departmentId") String departmentId,@RequestBody Department department){
        department.setId(departmentId);
        departmentService.updateDepartment(department);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/department",method = GET)
    public Result findAllDepartment(){
        logger.info("company开始");
        logger.info("companyService为:"+ companyService);
        Company company = companyService.findCompanyById(companyId);
        logger.info("company:"+company.toString());

        List<Department> departments = departmentService.findAllDepartment(companyId);
        DeptListResult deptListResult = new DeptListResult(company,departments);
        return new Result(ResultCode.SUCCESS,deptListResult);
    }

    @RequestMapping(value = "/department/{departmentId}",method = GET)
    public Result findDepartmentById(@PathVariable("departmentId")String departmentId){
        Department department = departmentService.findDepartmentById(departmentId);
        return new Result(ResultCode.SUCCESS,department);
    }
}

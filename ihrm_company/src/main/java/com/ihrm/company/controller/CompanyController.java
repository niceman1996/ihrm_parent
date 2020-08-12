package com.ihrm.company.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.company.service.ICompanyService;
import com.ihrm.company.service.impl.CompanyServiceImpl;
import com.ihrm.domain.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private ICompanyService companyService;

    @RequestMapping(value = "", method = POST)
    public Result addCompany(@RequestBody Company company){
        companyService.addCompany(company);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/{companyId}",method = DELETE)
    public Result deleteCompany(@PathVariable(value = "companyId") String id) {
        companyService.deleteCompanyById(id);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/{companyId}",method = PUT)
    public Result updateCompany(@PathVariable(value = "companyId") String id,@RequestBody Company company){
        company.setId(id);
        companyService.updateCompany(company);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/{companyId}",method = GET)
    public Result findCompanyById(@PathVariable(value = "companyId") String id){
        Company company = companyService.findCompanyById(id);
        Result result = new Result(ResultCode.SUCCESS);
        result.setMessage("String");
        result.setCode(0);
        result.setData(company);
        return result;
    }

    @RequestMapping(value = "",method = GET)
    public Result findAllCompany(){
        List<Company> companies = companyService.findAllCompany();
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(companies);
        return result;
    }

}

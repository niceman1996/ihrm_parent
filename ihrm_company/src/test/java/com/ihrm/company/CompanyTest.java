package com.ihrm.company;

import com.ihrm.company.dao.CompanyDao;
import com.ihrm.company.service.impl.CompanyServiceImpl;
import com.ihrm.domain.company.Company;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CompanyTest {
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private CompanyServiceImpl companyServiceImpl;
    Logger logger = LoggerFactory.getLogger(CompanyTest.class);

    @Test
    public void testJpa(){
        Company company = companyDao.findById("1").get();
        logger.info(company.toString());
    }

    @Test
    public void testCrud(){
        Company company = new Company();
        company.setName("煌腾");
        logger.info("添加的公司为"+company);
        logger.info(companyServiceImpl.toString());
        logger.info("增开始");
        companyServiceImpl.addCompany(company);
        logger.info("增结束");

        logger.info("删开始");
        companyServiceImpl.deleteCompanyById(company.getId());
        logger.info("删结束");

        logger.info("改开始");
        company.setName("辉煌");
        companyServiceImpl.updateCompany(company);
        logger.info("改结束");

        logger.info("查id开始");
        logger.info("查询到的公司为:"+ companyServiceImpl.findCompanyById("1288312670314844160").toString());
        logger.info("查id结束");

        logger.info("查所有开始");
        logger.info("查询的所有公司有:"+ companyServiceImpl.findAllCompany());
        logger.info("查所有结束");
    }
}

package com.ihrm.company.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/sys")
@RestController
@CrossOrigin
public class LoginController {
    @RequestMapping("login")
    public Result login(){
        Result result = new Result(ResultCode.SUCCESS);
        result.setCode(10000);
        result.setMessage("登录成功");
        result.setData("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOjIsImlhdCI6MTUzNTY4NjY0NSwiZXhwIjoxNTM2ODk2MjQ1fQ.bGV4qjcZcNYDBJjihIsSbSEQyPa5SkVcUYuA0WfgDds");
        return result;
    }
}

package com.ihrm.system.controller;

import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.common.utils.JwtUtils;
import com.ihrm.domain.system.User;
import com.ihrm.domain.system.response.ProfileResult;
import com.ihrm.domain.system.response.UserResult;
import com.ihrm.system.service.IRoleService;
import com.ihrm.system.service.IUserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController

@RequestMapping("/sys")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private JwtUtils jwtUtils;

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public Result addUser(@RequestBody User user){
        userService.addUser(user);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/user/{userId}",method = RequestMethod.DELETE)
    public Result deleteUser(@PathVariable("userId") String userId){
        userService.deleteUser(userId);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/user/{userId}",method = RequestMethod.PUT)
    public Result updateUser(@PathVariable("userId") String userId,@RequestBody User user){
        userService.updateUser(userId,user);
        return new Result(ResultCode.SUCCESS);
    }
    @RequestMapping(value = "/user/{userId}",method = RequestMethod.GET)
    public Result findUserById(@PathVariable("userId")String userId){
        User user = userService.findUserById(userId);
        UserResult userResult = new UserResult(user);
        return new Result(ResultCode.SUCCESS,userResult);
    }

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public Result findAllUser(Integer page,Integer size,@RequestParam Map map){
        Page<User> allUser = userService.findAllUser(page, size, map);
        PageResult pageResult = new PageResult(allUser.getTotalElements(),allUser.getContent());
        return new Result(ResultCode.SUCCESS,pageResult);
    }

    @RequestMapping(value = "/user/assignRoles",method = RequestMethod.PUT)
    public Result assignRoles(@RequestBody Map map){
        String userId = (String) map.get("id");
        List<String> roleIds = (List<String>) map.get("roleIds");
        userService.assignRoles(userId,roleIds);
        return Result.SUCCESS();
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(@RequestBody Map<String,String> loginMap){
        String mobile = loginMap.get("mobile");
        String password = loginMap.get("password");
        User user = userService.login(mobile);
        if (user==null || !password.equals(user.getPassword())){
            return new Result(ResultCode.MOBILEORPASSWORDERROR);
        }else {
            Map<String,Object> map = new HashMap<>();
            map.put("companyId",user.getCompanyId());
            map.put("companyName",user.getCompanyName());
            String token = jwtUtils.createJwt(user.getId(), user.getUsername(), map);
            return new Result(ResultCode.SUCCESS,token);
        }
    }

    @RequestMapping(value = "/profile",method = RequestMethod.POST)
    public Result profile(HttpServletRequest request) throws CommonException {
        String header = request.getHeader("Authorization");
        if (StringUtils.isEmpty(header)){
            throw new CommonException(ResultCode.UNAUTHENTICATED);
        }
        String token = header.replace("Bearer ", "");
        Claims claims = jwtUtils.parseJwt(token);
        String id = claims.getId();
        User user = userService.findUserById(id);
        return new Result(ResultCode.SUCCESS,new ProfileResult(user));
    }
}

package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.system.Role;
import com.ihrm.domain.system.response.RoleResult;
import com.ihrm.system.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys")
public class RoleController extends BaseController {
    @Autowired
    private IRoleService roleService;

//    @Autowired
//    private IPermissionService permissionService;

    @RequestMapping(value = "/role",method = RequestMethod.POST)
    public Result addRole(@RequestBody Role role){
        role.setCompanyId(companyId);
        roleService.addRole(role);
        return Result.SUCCESS();
    }

    @RequestMapping(value = "/role/{roleId}",method = RequestMethod.DELETE)
    public Result deleteRole(@PathVariable(name = "roleId")String roleId)throws  Exception{
        roleService.deleteRole(roleId);
        return Result.SUCCESS();
    }

    @RequestMapping(value = "/role/{roleId}",method = RequestMethod.PUT)
    public Result updateRole(@PathVariable("roleId")String roleId,@RequestBody Role role){
        roleService.updateRole(role);
        return Result.SUCCESS();
    }

    @RequestMapping(value = "/role/{roleId}",method = RequestMethod.GET)
    public Result findRoleById(@PathVariable("roleId")String roleId){
        Role role = roleService.findRoleById(roleId);
        RoleResult roleResult = new RoleResult(role);
        return new Result(ResultCode.SUCCESS,roleResult);
    }

    @RequestMapping(value = "/role",method = RequestMethod.GET)
    public Result findAllRoleByPage(@RequestParam("page")Integer page,@RequestParam("pagesize")Integer size){
        Page<Role> roles = roleService.findAllRole(companyId, page, size);
        PageResult<Role> rolePageResult = new PageResult<>(roles.getTotalElements(),roles.getContent());
        return new Result(ResultCode.SUCCESS,rolePageResult);
    }

    @RequestMapping(value="/role/list" ,method=RequestMethod.GET)
    public Result findAllRole() throws Exception {
        List<Role> roleList = roleService.findAllRole(companyId);
        return new Result(ResultCode.SUCCESS,roleList);
    }

    @RequestMapping(value = "/role/assignPerm",method = RequestMethod.PUT)
    public Result assignPermission(@RequestBody Map<String,Object> map){
        String roleId = (String) map.get("id");
        List<String> permissionIds = (List<String>) map.get("permIds");
        roleService.assignPermission(roleId,permissionIds);
        return Result.SUCCESS();
    }
}

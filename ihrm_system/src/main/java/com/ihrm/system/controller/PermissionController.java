package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.domain.system.Permission;
import com.ihrm.system.service.IPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys")
public class PermissionController extends BaseController {
    @Autowired
    private IPermissionService permissionService;
    Logger logger = LoggerFactory.getLogger(PermissionController.class);

    /**
     * 增加权限
     * @param map   权限及其子表统一
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/permission",method = RequestMethod.POST)
    public Result addPermission(@RequestBody Map<String,Object> map) throws Exception {
        logger.info(map.toString());
        permissionService.addPermission(map);
        return Result.SUCCESS();
    }

    @RequestMapping(value = "/permission/{permissionId}",method = RequestMethod.DELETE)
    public Result deletePermission(@PathVariable(value = "permissionId")String permissionId)throws  Exception{
        logger.info(permissionId);
        permissionService.deletePermission(permissionId);
        return Result.SUCCESS();
    }

    @RequestMapping(value = "/permission/{permissionId}",method = RequestMethod.PUT)
    public Result updatePermission(@PathVariable("permissionId")String permissionId,@RequestBody Map<String,Object> map) throws Exception {
        map.put("id",permissionId);
        permissionService.updatePermission(map);
        return Result.SUCCESS();
    }

    @RequestMapping(value = "/permission/{permissionId}",method = RequestMethod.GET)
    public Result findPermissionById(@PathVariable("permissionId")String permissionId) throws CommonException {
        Map<String, Object> permission = permissionService.findPermissionById(permissionId);
        return new Result(ResultCode.SUCCESS,permission);
    }

    @RequestMapping(value = "/permission",method = RequestMethod.GET)
    public Result findAllPermission(@RequestParam() Map map){
        List<Permission> allPermission = permissionService.findAllPermission(map);
        return new Result(ResultCode.SUCCESS,allPermission);
    }
}

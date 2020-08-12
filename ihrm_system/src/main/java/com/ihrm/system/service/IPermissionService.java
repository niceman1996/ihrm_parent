package com.ihrm.system.service;

import com.ihrm.common.exception.CommonException;
import com.ihrm.domain.system.Permission;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;


public interface IPermissionService {
    void addPermission(Map<String,Object> map) throws Exception;
    void deletePermission(String permissionId) throws CommonException;
    void updatePermission(Map<String,Object> map) throws Exception;
    Map<String,Object> findPermissionById(String permissionId) throws CommonException;
    List<Permission> findAllPermission(Map<String,Object> map);
}

package com.ihrm.system.service.impl;

import com.ihrm.common.entity.PageResult;
import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.common.utils.PermissionConstants;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.Role;
import com.ihrm.system.dao.PermissionDao;
import com.ihrm.system.dao.RoleDao;
import com.ihrm.system.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleServiceImpl extends BaseService<Role> implements IRoleService{
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private PermissionDao permissionDao;
    @Override
    public void addRole(Role role) {
        role.setId(idWorker.nextId()+"");
        roleDao.save(role);
    }

    @Override
    public void deleteRole(String roleId) {
        roleDao.deleteById(roleId);
    }

    @Override
    public void updateRole(Role role) {
        Role tempRole = roleDao.findById(role.getId()).get();
        if (!Objects.isNull(tempRole)){
            tempRole.setCompanyId(role.getCompanyId());
            tempRole.setDescription(role.getDescription());
            tempRole.setName(role.getName());
            tempRole.setPermissions(role.getPermissions());
            tempRole.setUsers(role.getUsers());
        }
        roleDao.save(tempRole);
    }

    @Override
    public Role findRoleById(String roleId) {
        Role role = roleDao.findById(roleId).get();
        return role;
    }

    @Override
    public Page<Role> findAllRole(String companyId, Integer page, Integer size) {
        return roleDao.findAll(getSpec(companyId), PageRequest.of(page-1,size));
    }

    @Override
    public List<Role> findAllRole(String companyId) {
        return roleDao.findAll(getSpec(companyId));
    }

    @Override
    public void assignPermission(String roleId, List<String> permissionIds) {
        Role role = roleDao.findById(roleId).get();
        Set<Permission> permissions = new HashSet<>();
        for (String id:permissionIds){
            Permission permission = permissionDao.findById(id).get();
            List<Permission> apiList = permissionDao.findByTypeAndPid(PermissionConstants.PY_API, permission.getId());
            permissions.addAll(apiList);
            permissions.add(permission);
        }
        role.setPermissions(permissions);
        roleDao.save(role);
    }
}

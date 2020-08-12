package com.ihrm.system.service;

import com.ihrm.domain.system.Role;
import org.springframework.data.domain.Page;

import java.util.List;


public interface IRoleService {
    void addRole(Role role);
    void deleteRole(String roleId);
    void updateRole(Role role);
    Role findRoleById(String roleId);
    Page<Role> findAllRole(String companyId, Integer page, Integer size);
    List<Role> findAllRole(String companyId);
    void assignPermission(String roleId, List<String> permissionIds);
}

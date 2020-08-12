package com.ihrm.domain.system.response;

import com.ihrm.common.utils.PermissionConstants;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.Role;
import com.ihrm.domain.system.User;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class ProfileResult{
    private String mobile;
    private String username;
    private String company;
    private Map<String,Object> roles = new HashMap<>();

    public ProfileResult(User user){
        this.mobile = user.getMobile();
        this.username = user.getUsername();
        this.company = user.getCompanyName();

        Set<Role> roles = user.getRoles();

        Set<String> menus = new HashSet<>();
        Set<String> points = new HashSet<>();
        Set<String> apis = new HashSet<>();


        for (Role role : roles){
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission: permissions){
                String code = permission.getCode();
                if (permission.getType() == 1){
                    menus.add(code);
                }else if (permission.getType() == 2){
                    menus.add(code);
                }else {
                    apis.add(code);
                }
            }
            this.roles.put("menus",menus);
            this.roles.put("points",points);
            this.roles.put("apis",apis);
        }
    }
}
package com.ihrm.system.service.impl;

import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.system.Role;
import com.ihrm.domain.system.User;
import com.ihrm.system.dao.RoleDao;
import com.ihrm.system.dao.UserDao;
import com.ihrm.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IdWorker idWorker;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    public void addUser(User user) {
        user.setId(idWorker.nextId()+"");
        user.setPassword("123456");
        user.setEnableState(1);




//        correctionTime: "2020-08-09T16:00:00.000Z"
//        departmentId: "1175311213774962688"
//        departmentName: "行政部"
//        formOfEmployment: "1"
//        mobile: "13123456789"
//        timeOfEntry: "2020-08-04"
//        username: "wy"
//        workNumber: "11"
//                "id": "string",
//                "password": "string",
//                "role": "string",
//                "enableState": "string",
//                "formOfManagement": "string",
//                "workingCity": "string",
//                "correctionTime": "string",
//                "inServiceStatus": "string",
//                "companyId": "string",
//                "companyName": "string",

        userDao.save(user);
    }

    @Override
    public void deleteUser(String userId) {
        userDao.deleteById(userId);
    }

    @Override
    public void updateUser(String userId,User user) {
        User tempUser = userDao.findById(userId).get();
        if (!Objects.isNull(tempUser)){
            tempUser.setUsername(user.getUsername());
            tempUser.setPassword(user.getPassword());
            tempUser.setDepartmentId(user.getDepartmentId());
            tempUser.setDepartmentName(user.getDepartmentName());
        }
        userDao.save(tempUser);
    }

    @Override
    public User findUserById(String userId) {
        return userDao.findById(userId).get();
    }

    @Override
    public Page<User> findAllUser(Integer page,Integer size,Map<String,Object> map) {
        Specification<User> userSpec = (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(map.get("departmentId"))) {
                list.add(criteriaBuilder.equal(root.get("departmentId").as(String.class), map.get("departmentId")));
            }
            if (!StringUtils.isEmpty(map.get("companyId"))) {
                list.add(criteriaBuilder.equal(root.get("companyId").as(String.class), map.get("companyId")));
            }
            if (StringUtils.isEmpty(map.get("hasDept"))){
                if("0".equals(map.get("hasDept"))){
                    list.add(criteriaBuilder.isNull(root.get("departmentId")));
                }else {
                    list.add(criteriaBuilder.isNotNull(root.get("departmentId")));
                }
            }
            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        };
        Page<User> users = userDao.findAll(userSpec, PageRequest.of(page-1,size));//从0开始,页数,内容
        return users;
    }

    @Override
    public void assignRoles(String userId,List<String> roleIds) {
        User tempUser = userDao.findById(userId).get();
        Set<Role> roles = new HashSet<>();
        for (String roleId:roleIds){
            Role role = roleDao.findById(roleId).get();
            roles.add(role);
        }
        tempUser.setRoles(roles);
        userDao.save(tempUser);
    }

    @Override
    public User login(String mobile) {
        User user = userDao.findByMobile(mobile);
        return user;
    }
}

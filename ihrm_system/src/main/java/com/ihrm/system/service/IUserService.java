package com.ihrm.system.service;

import com.ihrm.domain.system.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface IUserService {
    void addUser(User user);
    void deleteUser(String userId);
    void updateUser(String userID,User user);
    User findUserById(String userId);
    Page<User> findAllUser(Integer page,Integer size,Map<String,Object> map);
    void assignRoles(String id, List<String> roIds);
    User login(String mobile);
}

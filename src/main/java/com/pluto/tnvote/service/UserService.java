package com.pluto.tnvote.service;

import com.pluto.tnvote.pojo.User;

import java.util.List;
import java.util.Set;

public interface UserService  {

    List<User> query(User user);

    int delete(Integer id);

    int deleteBatch(String ids);

    int create(User user);

    int update(User user);

    User detail(Integer id);

    int count(User user);

    User login(String account, String password);

//    void saveUser(User user);

//
    User fingUserByUsername(String account);
//
    Set<String> findRolesByUserName(String account);

    Set<String> findPermissionsByUserName(String account);
}

package com.pluto.tnvote.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.google.common.base.Splitter;
import com.pluto.tnvote.mapper.UserMapper;
import com.pluto.tnvote.pojo.User;
import com.pluto.tnvote.service.UserService;
import com.pluto.tnvote.utils.BeanMapUtils;
import com.pluto.tnvote.utils.MapParameter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class UserServiceImp  implements UserService {

    /**
     *
     */



    @Autowired
    private UserMapper userMapper;


    @Override
    public User fingUserByUsername(String userName) {
        Wrapper<User> wrapper = new EntityWrapper<User>();
        wrapper.eq("account",userName);
        List<User> userList = userMapper.selectList(wrapper);
        if (userList != null&&userList.size()>0){
            return userList.get(0);
        }

        return null;
    }

    @Override
    public Set<String> findRolesByUserName(String account) {
        return userMapper.findRolesByUserName(account);
    }

    @Override
    public Set<String> findPermissionsByUserName(String account) {
        return userMapper.findPermissionsByUserName(account);
    }


//    public void saveUser(User user){
//        String password = user.getPassword();
//        String salt = RandomStringUtils.randomNumeric(6,8);
//        user.setPrivateSalt(salt);
//        Md5Hash md5Hash = new Md5Hash(password,salt);
//        user.setPassword(md5Hash.toString());
//        user.setUserStatus("1");
//        userMapper.insert(user);
//    }

    @Override
    public List<User> query(User user) {
		/*
		 * if(user != null && user.getPage() != null){
		 * PageHelper.startPage(user.getPage(),user.getLimit()); }
		 */
        Map<String, Object> map = MapParameter.getInstance().put(BeanMapUtils.beanToMap(user)).getMap();
        return userMapper.query(map);
    }

    @Override
    public int delete(Integer id) {
        return userMapper.deleteU(MapParameter.getInstance().addId(id).getMap());
    }



    @Override
    public int deleteBatch(String ids) {
        int flag = 0;
        List<String> list = Splitter.on(",").splitToList(ids);
        for (String s : list) {
            userMapper.deleteU(MapParameter.getInstance().addId(Integer.parseInt(s)).getMap());
            flag++;
        }
        return flag;
    }

    @Override
    public int create(User user) {
        // todo 60在这里需要往userrole表插入数据
        return userMapper.create(user);
    }

    @Override
    public int update(User user) {
        Map<String, Object> map = MapParameter.getInstance().put(BeanMapUtils.beanToMapForUpdate(user)).addId(user.getId()).getMap();
        return userMapper.updateU(map);
    }

    @Override
    public User detail(Integer id) {
        return userMapper.detail(MapParameter.getInstance().addId(id).getMap());
    }

    @Override
    public int count(User user) {
        Map<String, Object> map = MapParameter.getInstance().put(BeanMapUtils.beanToMap(user)).getMap();
        return userMapper.count(map);
    }


    @Override
    public User login(String account, String password) {
        Map<String, Object> map = MapParameter.getInstance().add("account",account).add("password",password).getMap();
        return userMapper.detail(map);

    }




}
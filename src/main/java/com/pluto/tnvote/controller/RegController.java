package com.pluto.tnvote.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pluto.tnvote.pojo.User;
import com.pluto.tnvote.pojo.UserRole;
import com.pluto.tnvote.service.UserRoleService;
import com.pluto.tnvote.service.UserService;
import com.pluto.tnvote.utils.MD5Utils;
import com.pluto.tnvote.utils.MapControl;
import com.pluto.tnvote.utils.SystemInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class RegController {

    @Autowired
    private UserService userService;

    @Autowired
    UserRoleService userRoleService;


    @GetMapping("/toreg")
    public String reg(){
        return "pages/reg";
    }

    @PostMapping("/reg")
    @ResponseBody
    public Map<String,Object> create(@RequestBody String userS){

        JSONObject jo = JSONObject.parseObject(userS);
        User param = new User();
        param.setAccount(jo.getString("account"));

        List<User> list = userService.query(param);
        if(list.size()>0){
            return MapControl.getInstance().error("注册失败，账号已存在").getMap();
        }else{
            String role_id = jo.getString("role_id");
            int role =0;
            if("role_part".equals(role_id)){
                role = 1;
            }else{
                role = 2;
            }
            jo.remove("role_id");
            User user = JSON.toJavaObject(jo,User.class);
            user.setPassword(MD5Utils.getMD5(user.getPassword()));
            int result = userService.create(user);
            if(result<=0){
                return MapControl.getInstance().error().getMap();
            }
            User newuser = new User();
            newuser.setAccount(user.getAccount());
            List<User> lists = userService.query(newuser);
            if(lists.size()==1) {
                UserRole userRole = new UserRole();
                userRole.setId(lists.get(0).getId());
                userRole.setUserId(lists.get(0).getId());
                userRole.setRoleId(role);
                userRoleService.create(userRole);
            }
            return MapControl.getInstance().success().getMap();
        }
    }

}

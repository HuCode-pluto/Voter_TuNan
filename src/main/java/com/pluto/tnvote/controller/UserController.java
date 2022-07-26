package com.pluto.tnvote.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pluto.tnvote.pojo.User;
import com.pluto.tnvote.pojo.UserRole;
import com.pluto.tnvote.service.UserRoleService;
import com.pluto.tnvote.service.UserService;
import com.pluto.tnvote.utils.MD5Utils;
import com.pluto.tnvote.utils.MapControl;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hctn
 * @since 2021-04-19
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    UserRoleService userRoleService;

    @GetMapping("/userlist")
    public String v_list(HttpServletRequest req){
        User user = (User) req.getSession().getAttribute("user");
        System.err.println(user);
        req.setAttribute("user", user);
        int userid = user.getId();
        int list =  userRoleService.query(userid);
        if(1 == list || 2 == list){
//            MapControl.getInstance().success().getMap();
            return "pages/admin/userlist";
        }else{
            return "pages/admin/list";
        }

    }
    @PostMapping("/create")
    @ResponseBody
    public Map<String,Object> create(@RequestBody User user){
        user.setPassword(MD5Utils.getMD5(user.getPassword()));
        int result = userService.create(user);
        if(result<=0){
            //失败的情况下
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
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

    @PostMapping("/delete")
    @ResponseBody
    public Map<String,Object> delete(String ids){
        int result = userService.deleteBatch(ids);
        if(result<=0){
            //失败的情况下
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> update(@RequestBody User user){
        int result = userService.update(user);
        if(result<=0){
            //失败的情况下
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }


    @PostMapping("/query")
    @ResponseBody
    public Map<String,Object> query(@RequestBody User user, ModelMap modelMap){
        System.out.println(user.getPage());
        System.out.println(user.getLimit());
        System.out.println(user.getAccount());
        List<User> list = userService.query(user);
        Integer count = userService.count(user);
        return MapControl.getInstance().page(list,count).getMap();
    }


    @GetMapping("/detail")
    public String detail(Integer id,ModelMap modelMap){
        User user = userService.detail(id);
        modelMap.addAttribute("user",user);
        return "pages/admin/update";
    }

}

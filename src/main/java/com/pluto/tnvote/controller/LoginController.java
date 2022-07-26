package com.pluto.tnvote.controller;

import com.google.common.base.Strings;
import com.pluto.tnvote.pojo.User;
import com.pluto.tnvote.service.UserService;
import com.pluto.tnvote.utils.JwtUtil;
import com.pluto.tnvote.utils.MD5Utils;
import com.pluto.tnvote.utils.MapControl;
import com.pluto.tnvote.utils.SessionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    com.pluto.tnvote.utils.JwtUtil jwtUtil ;
    /*
    前端发起请求 http://localhost:8086/login/tologin
    返回页面  login  程序会在启动时加载templates下的后缀为。html的文件 ，找到login.html的文件，返回过去
    */
    @GetMapping("/tologin")
    public String v_login(){
        return "login";
    }

    /*
    前端发起请求 http://localhost:8086/login/login
    返回页面一段json文件
    */
    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> login(User userMap, HttpServletRequest request, HttpServletResponse res) throws ServletException, IOException {
       //String account = map.get("account")+"";
       // String password = map.get("password")+"";
        String account = userMap.getAccount();
        System.err.println(account);
        String password = userMap.getPassword();
        if(Strings.isNullOrEmpty(account) || Strings.isNullOrEmpty(password)){
            return MapControl.getInstance().error("用户名或密码不能为空").getMap();
        }
        User user = userService.login(account, MD5Utils.getMD5(password));

        if(user != null){
            SessionUtils.setUser(request,user);
            res.sendRedirect("/index1");

            //设置token
            Map<String ,Object> map = new HashMap<>();
            map.put("name",user.getName());
            map.put("password",user.getPassword());
            String token = jwtUtil.getJwtToken(map,new Date());
            request.getSession().setAttribute("token",token);
            return MapControl.getInstance().success().getMap();
        }else{
            return MapControl.getInstance().error("用户名或密码错误").getMap();
        }
    }

    @GetMapping("/pwd")
    public String pwd(){
        return "pages/pwd";
    }
    @PostMapping("/pwd")
    @ResponseBody
    public Map<String,Object> pwd(Integer id,String type,String sourcePwd,String newPwd){
        User user = userService.detail(id);
        if(user.getPassword().equals(MD5Utils.getMD5(sourcePwd))){
            User entity = new User();
            entity.setId(id);
            entity.setPassword(MD5Utils.getMD5(newPwd));
            int update = userService.update(entity);
            if(update>0){
                return MapControl.getInstance().success().getMap();
            }else{
                return MapControl.getInstance().error().getMap();
            }
        }else{
            return MapControl.getInstance().error("原密码错误").getMap();
        }
    }
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
		session.invalidate();
		return "login";
		
	}
}

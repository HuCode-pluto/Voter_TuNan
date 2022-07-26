package com.pluto.tnvote.controller;

import com.pluto.tnvote.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RequestHtmlController {
    @GetMapping("/user/create")
    public String v_create(){
        return "pages/admin/add";
    }

    @GetMapping("/user/list")
    public String list(){
        return  "pages/admin/list";
    }

    @GetMapping("/user/info")
    public String info(HttpServletRequest req){
        User user = (User) req.getSession().getAttribute("user");
        System.err.println(user);
        req.setAttribute("user", user);

        return  "pages/info";
    }

    @GetMapping("/user/pwd")
    public String pwd(){
        return  "pages/pwd";
    }

}

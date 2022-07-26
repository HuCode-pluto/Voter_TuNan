package com.pluto.tnvote.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pluto.tnvote.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class IndexController {

    @Value("classpath:static/api/init.json")
    private Resource resource;

    @GetMapping("/index")
    public String index() {
        return "login";
    }

    @GetMapping("/info")
    public String info(){
        return "info";
    }

    @GetMapping("/menu")
    @ResponseBody
    public void menu(HttpServletResponse response) {
        try {
            File file = resource.getFile();
            FileInputStream inputStream = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(inputStream,"utf-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            String str;
            StringBuffer sb = new StringBuffer();
            while ((str = bufferedReader.readLine()) !=null){
                sb.append(str);
            }
            bufferedReader.close();
            isr.close();
            inputStream.close();
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/index1")
    public String index1(HttpServletRequest req) {
    	User user = (User) req.getSession().getAttribute("user");
    	System.err.println(user);
    	req.setAttribute("user", user);
        return "pages/index";
    }
}

package com.pluto.tnvote.config;

import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

public class CheckFilter implements HandlerInterceptor {

   @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("==================系统做Token和IP检查==================");
        // String token = request.getHeader("Token");
        HttpSession session =  request.getSession();
        String token = (String) session.getAttribute("token");
        String sessionID = session.getId();

        Date sesID = (Date)session.getAttribute(sessionID);
        if(sesID == null){
            session.setAttribute("sessionID",new Date());
        }else{
            Date last = new Date();
            if(last.getTime() - sesID.getTime() <=2000){
                session.wait(2000);
                session.setAttribute("sessionID",last);
            }
        }
        if(token == null){
            response.sendRedirect("/login/tologin");
            return false;
        }
        return true;
    }
}

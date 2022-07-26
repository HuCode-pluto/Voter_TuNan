/*
package com.pluto.tnvote.config;



import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //放行逻辑
        HandlerMethod method = (HandlerMethod) handler;
        DisableAuth auth = method.getMethod().getAnnotation(DisableAuth.class);
        if (isDisableAuth(auth)) {
            return super.preHandle(request, response, handler);
        }
        //获取token
        String accessToken = getAuthToken(request);
        if (StringUtils.isBlank(accessToken)) {
            setResponse(request, response, MessageKey.ACCESS_TOKEN_IS_NULL_CODE,"Error: token is null");
            return false;
        }

        // 3.查询token是否正确
        User account = userService.findAccessTokenIsValid(accessToken);
        if (account == null) {
            setResponse(request, response, MessageKey.ACCESS_TOKEN_IS_INVALID_CODE,"Error: token is invalid");
            return false;
        }

        // 将userId写入到request请求中
        request.setAttribute(Constants.REQUEST_ATTR_USER_ID, account.getId());
        request.setAttribute("UserToken", accessToken);
        request.setAttribute("UserId", account.getId());

        return true;
    }

    private static boolean isDisableAuth(DisableAuth auth) {
        return auth != null;
    }

    */
/**
     * 获取http请求头部或者参数中的token值
     *
     * @param request
     *            http请求传递的值
     * @return 返回token
     *//*

    private String getAuthToken(HttpServletRequest request) {
        String token = request.getHeader("accessToken");

        if (token == null) {
            token = request.getParameter("accessToken");
        }
        return token;
    }
}
*/

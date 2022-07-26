package com.pluto.tnvote.shiroconfig;

//和数据库交互

import com.pluto.tnvote.pojo.User;
import com.pluto.tnvote.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
//获取权限信息
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //获取用户名
        User user = (User)principals.getPrimaryPrincipal();//principals是认证时所构造的
        String account = user.getAccount();
        //从数据库查询用户权限和角色
        Set<String> roles = userService.findRolesByUserName(account);
        if (roles != null && roles.size()>0){
            simpleAuthorizationInfo.addRoles(roles);
        }
        Set<String> permissions = userService.findPermissionsByUserName(account);
        if (permissions != null && permissions.size()>0){
            simpleAuthorizationInfo.addStringPermissions(permissions);
        }

        return simpleAuthorizationInfo;
    }

    //获取认证信息的方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String userName = ((UsernamePasswordToken)token).getUsername();
        //token是封装好的用户提交的用户名密码
        //获取用户
        User user = userService.fingUserByUsername(userName);
        if (user == null ){
            return null;
        }else {
            SimpleByteSource bsSalt = new SimpleByteSource(user.getPrivateSalt());
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user,user.getPassword(),bsSalt,getName());
            return authenticationInfo;
        }

    }
}

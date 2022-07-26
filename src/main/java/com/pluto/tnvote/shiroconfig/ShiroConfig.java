package com.pluto.tnvote.shiroconfig;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //shiroFite
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/Login");


        Map filterChainMap = new LinkedHashMap<String,String>();
        filterChainMap.put("/login","anon"); //登录请求 放行

//        filterChainMap.put("/admin/list","perms[role_admin]"); //select认证用户登录
//        filterChainMap.put("/survey/list","perms[role_admin]");
//        filterChainMap.put("/survey/partlist","perms[role_part]");
//        filterChainMap.put("/survey/viewlist","perms[role_viewer]");
////        //用户对用户进行删、改、查时
//        filterChainMap.put("/user/delete","perms[role_admin]");
//        filterChainMap.put("/user/query","perms[role_admin]");
//        filterChainMap.put("/user/list","perms[role_admin]");
//        //查看投票问卷结果
//        filterChainMap.put("/check/query","perms[role_viewer]");
        //




//        filterChainMap.put("page/logout","logout");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
        return shiroFilterFactoryBean;
    }


//    安全管理器

    @Bean
    public SecurityManager getSecurityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
       // securityManager.setSessionManager(securityManager);
        securityManager.setRealm(myRealm());
        return securityManager;
    }



//    realm
    @Bean
    public Realm myRealm(){
        MyRealm myRealm = new MyRealm();
        //告诉Reaml密码匹配方式
        myRealm.setCredentialsMatcher(credentialsMatcher());
        myRealm.setAuthorizationCacheName("perms");
        myRealm.setAuthorizationCachingEnabled(true);

        myRealm.setAuthenticationCachingEnabled(false);
        //设置缓存管理器
        myRealm.setCacheManager(cacheManager());
        return myRealm;
    }


    @Bean
    public CredentialsMatcher credentialsMatcher(){
        HashedCredentialsMatcher hasheMatcher = new HashedCredentialsMatcher();
        hasheMatcher.setHashAlgorithmName("md5");
        return hasheMatcher;

    }


    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }


//*



    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());

        //设置会话过期时间
        sessionManager.setGlobalSessionTimeout(3*60*1000); //默认半小时
        sessionManager.setDeleteInvalidSessions(true); //默认自定调用SessionDAO的delete方法删除会话
        //设置会话定时检查
        //        sessionManager.setSessionValidationInterval(180000); //默认一小时
        //        sessionManager.setSessionValidationSchedulerEnabled(true);
        return sessionManager;
    }
    @Bean
    public SessionDAO redisSessionDAO(){
        ShiroRedisSessionDao redisDAO = new ShiroRedisSessionDao();
        return redisDAO;
    }

    //缓存管理
    @Bean
    public CacheManager cacheManager() {
        MyRedisCacheManager cacheManager = new MyRedisCacheManager();
        return cacheManager;
    }
}

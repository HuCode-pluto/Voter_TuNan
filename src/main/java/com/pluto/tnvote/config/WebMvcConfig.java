package com.pluto.tnvote.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {

        String[] addPathPatterns = {//拦截所有请求
                "/survey/**","/question/**","/user/**"
        };
        String[] excludePatterns = {//不拦截sayHi这个请求 【http://localhost:8080/sayHi】
                "/static/**","/file/**","/login/tologin"
        };

        registry.addInterceptor(new CheckFilter()).addPathPatterns(addPathPatterns).excludePathPatterns(excludePatterns);
        super.addInterceptors(registry);
    }

    @Override

    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //静态资源放行
      /*  registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/templates/")
                .addResourceLocations("classpath:/public/");*/
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/file/**").addResourceLocations("file:E:/uploadFiles/");
    }

      //

}
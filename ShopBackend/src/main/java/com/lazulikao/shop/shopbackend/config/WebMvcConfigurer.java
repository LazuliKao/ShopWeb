package com.lazulikao.shop.shopbackend.config;
import com.lazulikao.shop.shopbackend.interceptor.AuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
@Configuration
public class WebMvcConfigurer implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {

    private final AuthorizationInterceptor myInterceptor;
    public WebMvcConfigurer(AuthorizationInterceptor myInterceptor) {
        this.myInterceptor = myInterceptor;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/identity/**");
    }
}

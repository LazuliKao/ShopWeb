package com.lazulikao.shop.shopbackend.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//@Configuration
//public class CrosConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
////        WebMvcConfigurer.super.addCorsMappings(registry);
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD")
//                .allowCredentials(true)
//                .maxAge(3600)
//                .allowedHeaders("*");
//    }
//}
@Configuration
public class CrosConfig implements WebMvcConfigurer {
    //允许指定的跨域访问
    //不然axios请求会被拦截
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4000", "http://192.168.16.233:4000")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}


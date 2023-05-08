package com.lazulikao.shop.shopbackend.interceptor;
import com.lazulikao.shop.shopbackend.components.TokenManager;
import com.lazulikao.shop.shopbackend.controller.ShopController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenManager tokenManager;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            String token = authorizationHeader; // 获取Token
            // 进行Token验证和鉴权
            //todo 验证token
//            var user = tokenManager.getUserNameFromToken(token);
//            if (user.isPresent()) {
//                return true;
//            } else {
//                response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 返回未经授权的错误响应
//                return false;
//            }
            return true;
        } else {
//            System.out.println("AuthorizationInterceptor.preHandle: response.getStatus() = " + response.getStatus());
//            response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 返回未经授权的错误响应
//            return false;
            return true;
        }
    }
}


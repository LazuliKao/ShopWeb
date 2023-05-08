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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod()))
            return true;
        String token = request.getHeader("Authorization");
        if (token != null) {
            // 进行Token验证和鉴权
            //todo 验证token
            if (tokenManager.verifyToken(token)) {
                return true;
            }
        }
        System.out.println("AuthorizationInterceptor.preHandle: response.getStatus() = " + response.getStatus());
        response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 返回未经授权的错误响应
        return false;
    }
}


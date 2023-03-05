package com.lazulikao.shop.shopbackend.controller;
import com.lazulikao.shop.shopbackend.components.TokenManager;
import com.lazulikao.shop.shopbackend.entity.User;
import com.lazulikao.shop.shopbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/identity")
public class InentityController {
    private final UserService userService;
    @Autowired
    public InentityController(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    private TokenManager tokenManager;
    @PostMapping("/login")
    @ResponseBody
    public LoginResult login(@RequestBody LoginRequest request) {
        System.out.println("try login : " + request.user);
        if (request.user == null || request.passwordMd5 == null) {
            return new LoginResult(false, "用户名或密码无效", "");
        }
        //find user in database
        var instance = userService.tryFindUser(request.user, request.passwordMd5);
        if (instance.isEmpty()) {
            return new LoginResult(false, "用户名或密码错误", "");
        }
        //生成 token
        var tk = tokenManager.generateToken(request.user);
        return new LoginResult(true, "登录成功" + request.user, tk);
    }
    public record LoginRequest(String user, String passwordMd5) {}
    public record LoginResult(boolean success, String message, String token) {}
    @PostMapping("/register")
    @ResponseBody
    public RegisterResult register(String user, String PasswordMd5) {
        if (userService.existsUser(user)) {
            return new RegisterResult(false, "用户名已存在");
        }
        User instance = new User();
        instance.setUser(user);
        instance.setPasswordMd5(PasswordMd5);
        userService.saveUser(instance);
        return new RegisterResult(true, "注册成功" + user);
    }
    private record RegisterResult(boolean success, String message) {}
}

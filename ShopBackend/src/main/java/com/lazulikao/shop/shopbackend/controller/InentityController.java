package com.lazulikao.shop.shopbackend.controller;
import com.lazulikao.shop.shopbackend.entity.User;
import com.lazulikao.shop.shopbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/identity")
public class InentityController {
    private final UserService userService;
    @Autowired
    public InentityController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/login")
    @ResponseBody
    public LoginResult login(String user, String password) {
        System.out.println("try login : " + user);
        //find user in database
        var instance = userService.tryFindUser(user, password);
        if (instance.isEmpty()) {
            return new LoginResult(false, "用户名或密码错误");
        }
        return new LoginResult(true, "登录成功" + user);
    }
    private record LoginResult(boolean success, String message) {}
    @GetMapping("/register")
    @ResponseBody
    public RegisterResult register(String user, String email, String PasswordMd5) {
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

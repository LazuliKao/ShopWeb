package com.lazulikao.shop.shopbackend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InentityController {
    @RequestMapping(value = "/identity/login")
    public String login() {
        return "success";
    }
}

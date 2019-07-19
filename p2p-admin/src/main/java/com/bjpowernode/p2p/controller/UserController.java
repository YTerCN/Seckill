package com.bjpowernode.p2p.controller;

import com.bjpowernode.p2p.model.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @RequestMapping("/")
    public String index(){
        return "login";
    }


    @RequestMapping("/login")
    public String login(UserInfo userInfo){
        System.out.println(userInfo.getUsername());
        System.out.println(userInfo.getPassword());
        return "index";
    }
}

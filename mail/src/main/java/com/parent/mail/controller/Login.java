package com.parent.mail.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mail")
public class Login {
    @ResponseBody
    @RequestMapping("/login")
    public String doLogin(){
        return "123";
    }

}

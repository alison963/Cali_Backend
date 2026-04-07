package calisthenics.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class AuthController {

    @RequestMapping("/")
    public String register() {
        return "register works";
    }

    @RequestMapping("/test")
    public String login() {
        return "login works";
    }
}
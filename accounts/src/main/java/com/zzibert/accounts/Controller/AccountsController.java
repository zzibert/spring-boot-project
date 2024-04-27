package com.zzibert.accounts.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsController {
    @GetMapping("sayHello")
    public String sayHello() {
        return "ayo World";
    }
}

package com.company.app.ws.ui.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/noroc")
public class HomeController {

    @GetMapping
    public String homePage(){
        return "Welcome to home page";
    }

}

package com.sheldon.springoauth2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: Sheldon
 * @Date: 2018/09/24 11:09
 */

@RestController
public class IndexController {

    @GetMapping("/sayHello")
    private String sayHello(){
        System.out.println("Hello World");
        return "Hello World";
    }
}

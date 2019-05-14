package com.mwx.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class siteController {


    @GetMapping(value = {"","/","/index"})
    public String index(){
        return "index";
    }


    @GetMapping("/douban")
    public String douban(){
        return "douban";
    }

    @GetMapping("/maoyan")
    public String maoyan(){
        return "maoyan";
    }


    @GetMapping("/integrate")
    public String integrate(){
        return "integrate";
    }



}

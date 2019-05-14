package com.mwx.springboot.controller;


import com.mwx.springboot.entity.PageBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/maoyan")
public class MaoyanController {

    @RequestMapping("/findmaoyanDataByConPage")
    public PageBean findByConPage(
            @RequestParam(value = "pageCode", required = false) int pageCode,
            @RequestParam(value = "pageSize", required = false) int pageSize){
        System.out.println("按页查询: "+ pageCode+" "+pageSize);

        return null;
    }

}

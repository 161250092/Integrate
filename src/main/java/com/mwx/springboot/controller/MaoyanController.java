package com.mwx.springboot.controller;


import com.mwx.springboot.entity.PageBean;
import com.mwx.springboot.entity.maoyan.Film;
import com.mwx.springboot.service.MaoyanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/maoyan")
public class MaoyanController {

    @Autowired
    private MaoyanService maoyanService;

    @RequestMapping("/findmaoyanDataByConPage")
    public PageBean findByConPage(
            @RequestParam(value = "pageCode", required = false) int pageCode,
            @RequestParam(value = "pageSize", required = false) int pageSize){
      //  System.out.println("按页查询: "+ pageCode+" "+pageSize);
        return maoyanService.findMaoYanDataByConPage(pageCode,pageSize);
    }

    @RequestMapping("/findMaoYanDataByName")
    public Film findMaoYanDataByName(@RequestParam(value = "searchInfo", required = false) String searchInfo ){
        System.out.println(searchInfo);
        return maoyanService.findMaoYanDataByName(searchInfo);
    }

    @RequestMapping("/searchDouBanMovies")
    public List<Film> searchDouBanMovies(@RequestParam(value = "searchInfo", required = false) String searchInfo){
        return maoyanService.searchMaoYanFilm(searchInfo);
    }

}

package com.mwx.springboot.controller;

import com.mwx.springboot.entity.PageBean;
import com.mwx.springboot.entity.integrate.IntegratedFilm;
import com.mwx.springboot.entity.maoyan.Film;
import com.mwx.springboot.service.IntegratedFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * demo
 */
@RestController
@RequestMapping("/integrate")
public class IntegrateController {

    @Autowired
    IntegratedFilmService integratedFilmService;

    @RequestMapping("/findIntegratedFilmsByConPage")
    public PageBean findIntegratedFilmsByConPage(
            @RequestParam(value = "pageCode", required = false) int pageCode,
            @RequestParam(value = "pageSize", required = false) int pageSize){
        //  System.out.println("按页查询: "+ pageCode+" "+pageSize);
        return integratedFilmService.findIntegratedFilmsByConPage(pageCode,pageSize);
    }

    @RequestMapping("/findIntegratedFilmByName")
    public IntegratedFilm findIntegratedFilmByName(@RequestParam(value = "searchInfo", required = false) String searchInfo ){
        System.out.println(searchInfo);
        return integratedFilmService.findIntegratedFilmByName(searchInfo);
    }

    @RequestMapping("/searchIntegratedFilm")
    public List<IntegratedFilm> searchDouBanMovies(@RequestParam(value = "searchInfo", required = false) String searchInfo){
        return integratedFilmService.searchIntegratedFilm(searchInfo);
    }





}

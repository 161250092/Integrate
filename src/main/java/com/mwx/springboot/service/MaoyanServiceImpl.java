package com.mwx.springboot.service;

import com.mwx.springboot.dao.MaoyanDataService;
import com.mwx.springboot.entity.PageBean;
import com.mwx.springboot.entity.maoyan.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaoyanServiceImpl implements MaoyanService{

    @Autowired
    private MaoyanDataService maoyanDataService;

    @Override
    public PageBean findMaoYanDataByConPage(int pageCode, int pageSize) {
        return maoyanDataService.findMaoYanDataByConPage(pageCode,pageSize);
    }

    @Override
    public Film findMaoYanDataByName(String name) {
        return null;
    }

    @Override
    public Film searchMaoYanFilm(String searchInfo) {
        return null;
    }
}

package com.mwx.springboot.service;

import com.mwx.springboot.entity.PageBean;
import com.mwx.springboot.entity.moyan.Film;
import org.springframework.stereotype.Service;

@Service
public class MaoyanServiceImpl implements MaoyanService{


    @Override
    public PageBean findMaoYanDataByConPage(int pageCode, int pageSize) {
        return null;
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

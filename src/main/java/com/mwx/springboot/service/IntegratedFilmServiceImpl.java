package com.mwx.springboot.service;

import com.mwx.springboot.entity.PageBean;
import com.mwx.springboot.entity.integrate.IntegratedFilm;
import org.springframework.stereotype.Service;

@Service
public class IntegratedFilmServiceImpl implements  IntegratedFilmService{


    @Override
    public PageBean findIntegratedFilmsByConPage(int pageCode, int pageSize) {
        return null;
    }

    @Override
    public IntegratedFilm findIntegratedFilmByName(String name) {
        return null;
    }

    @Override
    public IntegratedFilm searchIntegratedFilm(String searchInfo) {
        return null;
    }
}

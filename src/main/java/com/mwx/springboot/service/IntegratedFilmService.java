package com.mwx.springboot.service;

import com.mwx.springboot.entity.PageBean;
import com.mwx.springboot.entity.integrate.IntegratedFilm;

import java.util.List;

public interface IntegratedFilmService {

    /**
     *
     * @param pageCode 当前页
     * @param pageSize 行数
     * @return
     */
    public PageBean findIntegratedFilmsByConPage(int pageCode, int pageSize);

    //直接查询
    public IntegratedFilm findIntegratedFilmByName(String name);

    //模糊查询
    public List<IntegratedFilm> searchIntegratedFilm(String searchInfo);

}

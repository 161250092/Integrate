package com.mwx.springboot.service;

import com.mwx.springboot.entity.PageBean;
import com.mwx.springboot.entity.maoyan.Film;

import java.util.List;

public interface MaoyanService {

    /**
     *
     * @param pageCode 当前页
     * @param pageSize 行数
     * @return
     */
    public PageBean findMaoYanDataByConPage(int pageCode, int pageSize);

    public Film findMaoYanDataByName(String name);

    public List<Film> searchMaoYanFilm(String searchInfo);
}

package com.mwx.springboot.dao;

import com.mwx.springboot.entity.PageBean;

public interface DoubanDataService {

    public PageBean findDouBanDataByConPage(int pageCode, int pageSize);
    public String findDouBanDataByConPageXML(int pageCode, int pageSize);
    public String findDouBanDataByMovieName(String name);
    public String searchDouBanMovies(String name);//模糊查询

}

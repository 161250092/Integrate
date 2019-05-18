package com.mwx.springboot.service;

import com.mwx.springboot.entity.douban.Movie;
import com.mwx.springboot.entity.PageBean;

import java.util.List;

public interface DoubanService {

    /**
     *
     * @param pageCode 当前页
     * @param pageSize 行数
     * @return
     */
    public PageBean findDouBanDataByConPage(int pageCode,int pageSize);

    //直接查询
    public Movie findDouBanDataByMovieName(String name);

    //模糊查询
    public List<Movie> searchDouBanMovies(String searchInfo);

}

package com.mwx.springboot.service;

import com.mwx.springboot.dao.DoubanDataService;
import com.mwx.springboot.entity.PageBean;
import com.mwx.springboot.entity.douban.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoubanServiceImpl implements  DoubanService{
    @Autowired
    DoubanDataService doubanDataService;

    @Override
    public PageBean findDouBanDataByConPage(int pageCode, int pageSize) {
        return doubanDataService.findDouBanDataByConPage(pageCode,pageSize);
    }

    @Override
    public Movie findDouBanDataByMovieName(String name) {
        return null;
    }

    @Override
    public Movie searchDouBanMovies(String searchInfo) {
        return null;
    }
}

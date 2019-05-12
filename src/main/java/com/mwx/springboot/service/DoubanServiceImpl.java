package com.mwx.springboot.service;

import com.mwx.springboot.dao.DoubanDataService;
import com.mwx.springboot.entity.PageBean;
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
}

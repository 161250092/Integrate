package com.mwx.springboot.service;

import com.mwx.springboot.entity.PageBean;

public interface DoubanService {

    /**
     *
     * @param pageCode 当前页
     * @param pageSize 行数
     * @return
     */
    public PageBean findDouBanDataByConPage(int pageCode,int pageSize);




}

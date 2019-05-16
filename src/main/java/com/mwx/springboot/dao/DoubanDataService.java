package com.mwx.springboot.dao;

import com.mwx.springboot.entity.PageBean;

public interface DoubanDataService {

    /**
     *
     * @param pageCode 当前页
     * @param pageSize 行数
     * @return
     */

    public PageBean findDouBanDataByConPage(int pageCode, int pageSize);



}

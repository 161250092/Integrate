package com.mwx.springboot.dao;

import com.mwx.springboot.entity.PageBean;

public interface DoubanDataService {

    public PageBean findDouBanDataByConPage(int pageCode, int pageSize);


}

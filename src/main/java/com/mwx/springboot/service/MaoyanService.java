package com.mwx.springboot.service;

import com.mwx.springboot.entity.PageBean;

public interface MaoyanService {

    /**
     *
     * @param pageCode 当前页
     * @param pageSize 行数
     * @return
     */
    public PageBean findMaoYanDataByConPage(int pageCode, int pageSize);

}

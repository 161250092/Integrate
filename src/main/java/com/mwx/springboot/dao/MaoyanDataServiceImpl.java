package com.mwx.springboot.dao;

import com.mwx.springboot.entity.douban.Movie;
import com.mwx.springboot.entity.PageBean;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaoyanDataServiceImpl implements MaoyanDataService{
    private Connection conn;

    private String db = "Integrate";

    @Override
    public PageBean findMaoYanDataByConPage(int pageCode, int pageSize) {

        int startIndex = (pageCode-1)*pageSize+1;
        int endIndex = pageCode*pageSize;

        PreparedStatement stmt;
        String sql;
        conn = new MySQLConnector().getConnection(db);
        List<Movie> movies = new ArrayList<>();


        return new PageBean(0,movies);
    }


    private void addHotComments(Movie movie){

    }


}

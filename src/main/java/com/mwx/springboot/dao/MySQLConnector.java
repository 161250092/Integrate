package com.mwx.springboot.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConnector {


    public Connection getConnection(String DataBaseName) {

        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost:3306/";

        // 数据库的用户名与密码
        final String USER = "root";
        final String PASS = "123456";

        DB_URL = DB_URL + DataBaseName + "?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=false";

        Connection conn = null;
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}


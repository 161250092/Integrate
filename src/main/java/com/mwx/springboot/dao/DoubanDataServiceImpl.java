package com.mwx.springboot.dao;


import com.mwx.springboot.entity.douban.Hotcomment;
import com.mwx.springboot.entity.douban.Movie;
import com.mwx.springboot.entity.PageBean;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import org.xml.sax.SAXException;


@Service
public class DoubanDataServiceImpl implements DoubanDataService{


    private Connection conn;

    private String db = "Integrate";

    @Override
    public PageBean findDouBanDataByConPage(int pageCode, int pageSize) {
        int startIndex = (pageCode-1)*pageSize+1;
        int endIndex = pageCode*pageSize;

        PreparedStatement stmt;
        String sql;
        conn = new MySQLConnector().getConnection(db);
        List<Movie> movies = new ArrayList<>();
        try {

            sql = "select * from movie where id between ? and ?";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1,startIndex);
            stmt.setInt(2,endIndex);

            ResultSet set = stmt.executeQuery();
            while(set.next()){
                int id = set.getInt("id");
                String title = set.getString("title");
                String directors = set.getString("directors");
                String rate = set.getString("rate");
                String casts = set.getString("casts");
                String type = set.getString("type");
                String nation = set.getString("nation");
                String language = set.getString("language");
                String date = set.getString("date");
                String time = set.getString("time");
                String peopleNumber = set.getString("peopleNumber");
                String introduction = set.getString("introduction");
                Movie movie = new Movie(id,title,directors,rate,casts,type,nation,language,date,time,peopleNumber,introduction);
                movies.add(movie);

            }
            set.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(Movie movie:movies){
            addHotComments(movie);
        }

        return new PageBean(checkDataNum(),movies);
    }


    private int checkDataNum(){
        PreparedStatement stmt;
        String sql;
        conn = new MySQLConnector().getConnection(db);
        int totalSize = 0;
        try{
            sql = "select count(*) as totalNum from movie";

            stmt = conn.prepareStatement(sql);


            ResultSet set = stmt.executeQuery();
            while(set.next()){
                totalSize = set.getInt("totalNum");
            }
            stmt.close();
            conn.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return totalSize;
    }


    private void addHotComments(Movie movie){
        PreparedStatement stmt;
        String sql;
        conn = new MySQLConnector().getConnection(db);

        List<Hotcomment> hotcomments = new ArrayList<>();

        try{
            sql = "select * from hotcomments where movie_id = ?";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1,movie.getId());

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int hotcomments_id = rs.getInt("hotcomments_id");
                int movie_id = rs.getInt("movie_id");
                String hotCommentAuthor = rs.getString("hotCommentAuthor");
                String hotCommentDate = rs.getString("hotCommentDate");
                String hotCommentContent = rs.getString("hotCommentContent");
                Hotcomment hotcomment = new Hotcomment(hotcomments_id,movie_id,hotCommentAuthor,hotCommentDate,hotCommentContent);
                hotcomments.add(hotcomment);
            }
            stmt.close();
            conn.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        movie.setHotcommentList(hotcomments);

    }


    private String buildXMLPageBean(PageBean pageBean) {
            // 创建Document
            Document document = DocumentHelper.createDocument();

            // 添加根节点
            Element root = document.addElement("root");

            // 在根节点下添加第一个子节点

            for(Movie movie:(List<Movie>)pageBean.getRows()){
                Element movieXML= root.addElement("movie");
                movieXML.addElement("id").addText(movie.getId()+"");
                movieXML.addElement("title").addText(movie.getTitle());
                movieXML.addElement("directors").addText(movie.getDirectors());
                movieXML.addElement("rate").addText(movie.getRate());
                movieXML.addElement("casts").addText(movie.getCasts());
                movieXML.addElement("type").addText(movie.getType());
                movieXML.addElement("nation").addText(movie.getNation().replace("\n", "").replace(" ", ""));
                movieXML.addElement("language").addText(movie.getLanguage().replace("\n", "").replace(" ", ""));
                movieXML.addElement("date").addText(movie.getDate());
                movieXML.addElement("time").addText(movie.getTime());
                movieXML.addElement("peopleNumber").addText(movie.getPeopleNumber());
                movieXML.addElement("introduction").addText(movie.getIntroduction());
                Element hotCommentsXML = movieXML.addElement("hotComments");
                for (Hotcomment hotcomment:movie.getHotcommentList()){
                    Element hotCommentXML=hotCommentsXML.addElement("hotComment");
                    hotCommentXML.addElement("hotCommentAuthor").addText(hotcomment.getHotCommentAuthor());
                    hotCommentXML.addElement("hotCommentDate").addText(hotcomment.getHotCommentDate());
                    hotCommentXML.addElement("hotCommentContent").addText(hotcomment.getHotCommentContent());
                }
            }
            String result = document.asXML();
            System.out.println(result);

        String fileName ="douban/"+getNowTime()+".xml";

        try {
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter( new FileOutputStream(new File(fileName)), format);
            writer.write(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
            return fileName;
    }

    @Override
    public String findDouBanDataByMovieName(String name) {
        PreparedStatement stmt;
        String sql;
        conn = new MySQLConnector().getConnection(db);

        try{
            sql = "select * from movie where title = ?";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1,name);

            ResultSet set = stmt.executeQuery();
            Movie movie=null;
            while(set.next()){
                int id = set.getInt("id");
                String title = set.getString("title");
                String directors = set.getString("directors");
                String rate = set.getString("rate");
                String casts = set.getString("casts");
                String type = set.getString("type");
                String nation = set.getString("nation");
                String language = set.getString("language");
                String date = set.getString("date");
                String time = set.getString("time");
                String peopleNumber = set.getString("peopleNumber");
                String introduction = set.getString("introduction");
                movie = new Movie(id,title,directors,rate,casts,type,nation,language,date,time,peopleNumber,introduction);
                addHotComments(movie);
            }
            stmt.close();
            conn.close();

            if (movie!=null){
                Document document = DocumentHelper.createDocument();

                // 添加根节点
                Element root = document.addElement("root");
                Element movieXML= root.addElement("movie");
                movieXML.addElement("id").addText(movie.getId()+"");
                movieXML.addElement("title").addText(movie.getTitle());
                movieXML.addElement("directors").addText(movie.getDirectors());
                movieXML.addElement("rate").addText(movie.getRate());
                movieXML.addElement("casts").addText(movie.getCasts());
                movieXML.addElement("type").addText(movie.getType());
                movieXML.addElement("nation").addText(movie.getNation().replace("\n", "").replace(" ", ""));
                movieXML.addElement("language").addText(movie.getLanguage().replace("\n", "").replace(" ", ""));
                movieXML.addElement("date").addText(movie.getDate());
                movieXML.addElement("time").addText(movie.getTime());
                movieXML.addElement("peopleNumber").addText(movie.getPeopleNumber());
                movieXML.addElement("introduction").addText(movie.getIntroduction());
                Element hotCommentsXML = movieXML.addElement("hotComments");
                for (Hotcomment hotcomment:movie.getHotcommentList()){
                    Element hotCommentXML=hotCommentsXML.addElement("hotComment");
                    hotCommentXML.addElement("hotCommentAuthor").addText(hotcomment.getHotCommentAuthor());
                    hotCommentXML.addElement("hotCommentDate").addText(hotcomment.getHotCommentDate());
                    hotCommentXML.addElement("hotCommentContent").addText(hotcomment.getHotCommentContent());
                }
                System.out.println(document.asXML());

                String fileName ="douban/"+getNowTime()+".xml";

                try {
                    OutputFormat format = OutputFormat.createPrettyPrint();
                    XMLWriter writer = new XMLWriter( new FileOutputStream(new File(fileName)), format);
                    writer.write(document);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return fileName;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public String searchDouBanMovies(String name) {
        PreparedStatement stmt;
        String sql;
        conn = new MySQLConnector().getConnection(db);
        List<Movie> movies = new ArrayList<>();
        try {

            sql = "select * from movie where title LIKE BINARY ?";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1,"%"+name+"%");

            ResultSet set = stmt.executeQuery();
            while(set.next()){
                int id = set.getInt("id");
                String title = set.getString("title");
                String directors = set.getString("directors");
                String rate = set.getString("rate");
                String casts = set.getString("casts");
                String type = set.getString("type");
                String nation = set.getString("nation");
                String language = set.getString("language");
                String date = set.getString("date");
                String time = set.getString("time");
                String peopleNumber = set.getString("peopleNumber");
                String introduction = set.getString("introduction");
                Movie movie = new Movie(id,title,directors,rate,casts,type,nation,language,date,time,peopleNumber,introduction);
                addHotComments(movie);
                movies.add(movie);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Document document = DocumentHelper.createDocument();

        // 添加根节点
        Element root = document.addElement("root");

        for(Movie movie:movies){
            Element movieXML= root.addElement("movie");
            movieXML.addElement("id").addText(movie.getId()+"");
            movieXML.addElement("title").addText(movie.getTitle());
            movieXML.addElement("directors").addText(movie.getDirectors());
            movieXML.addElement("rate").addText(movie.getRate());
            movieXML.addElement("casts").addText(movie.getCasts());
            movieXML.addElement("type").addText(movie.getType());
            movieXML.addElement("nation").addText(movie.getNation().replace("\n", "").replace(" ", ""));
            movieXML.addElement("language").addText(movie.getLanguage().replace("\n", "").replace(" ", ""));
            movieXML.addElement("date").addText(movie.getDate());
            movieXML.addElement("time").addText(movie.getTime());
            movieXML.addElement("peopleNumber").addText(movie.getPeopleNumber());
            movieXML.addElement("introduction").addText(movie.getIntroduction());
            Element hotCommentsXML = movieXML.addElement("hotComments");
            for (Hotcomment hotcomment:movie.getHotcommentList()){
                Element hotCommentXML=hotCommentsXML.addElement("hotComment");
                hotCommentXML.addElement("hotCommentAuthor").addText(hotcomment.getHotCommentAuthor());
                hotCommentXML.addElement("hotCommentDate").addText(hotcomment.getHotCommentDate());
                hotCommentXML.addElement("hotCommentContent").addText(hotcomment.getHotCommentContent());
            }
        }

        if(movies.size()==0){
            return null;
        }

        String fileName ="douban/"+getNowTime()+".xml";

        try {
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter( new FileOutputStream(new File(fileName)), format);
            writer.write(document);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = document.asXML();
        System.out.println(result);
        return fileName;
    }

    @Override
    public String findDouBanDataByConPageXML(int pageCode, int pageSize) {
        return buildXMLPageBean(findDouBanDataByConPage(pageCode, pageSize));
    }

    public static void main(String[] args){
        DoubanDataServiceImpl doubanDataService=new DoubanDataServiceImpl();
        doubanDataService.findDouBanDataByMovieName("钢铁侠");

    }


    public String getNowTime(){
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");//可以方便地修改日期格式
        String hehe = dateFormat.format( now );
        return hehe;
    }
}





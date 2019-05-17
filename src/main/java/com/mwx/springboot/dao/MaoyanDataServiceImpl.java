package com.mwx.springboot.dao;

import com.mwx.springboot.entity.PageBean;
import com.mwx.springboot.entity.maoyan.Film;
import com.mwx.springboot.entity.maoyan.FilmComment;
import org.dom4j.DocumentHelper;
import org.springframework.stereotype.Service;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.Document;

import java.sql.*;
import java.io.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MaoyanDataServiceImpl implements MaoyanDataService{
    private Connection conn;

    private String db = "maoYanFilm";

    @Override
    public PageBean findMaoYanDataByConPage(int pageCode, int pageSize) {

        int startIndex = (pageCode - 1) * pageSize + 1;
        int endIndex = pageCode * pageSize;

        conn = new MySQLConnector().getConnection(db);
        List<Film> films = new ArrayList<>();

        try {
            String infoSql = "select * from `filminfo` where id between ? and ?";

            String descriptionSql = "select d.filmName, d.description from `filmdescription` d " +
                    "where id between ? and ?";

            String commentSql = "select c.id, c.filmName, c.comment from `filminfo` i, " +
                    "`filmcomment` c " +
                    "where i.id between ? and ? and i.name = c.filmName";

            PreparedStatement infoStmt = conn.prepareStatement(infoSql);
            PreparedStatement descriptionStmt = conn.prepareStatement(descriptionSql);
            PreparedStatement commentStmt = conn.prepareStatement(commentSql);

            infoStmt.setInt(1, startIndex - 1);
            infoStmt.setInt(2, endIndex - 1);

            descriptionStmt.setInt(1, startIndex - 1);
            descriptionStmt.setInt(2, endIndex - 1);

            commentStmt.setInt(1, startIndex - 1);
            commentStmt.setInt(2, endIndex - 1);

            ResultSet infoRs = infoStmt.executeQuery();
            ResultSet descriptionRs = descriptionStmt.executeQuery();
            ResultSet commentRs = commentStmt.executeQuery();

            films = getFilmData(infoRs, descriptionRs, commentRs);

            infoRs.close();
            infoStmt.close();
            descriptionRs.close();
            descriptionStmt.close();
            commentRs.close();
            commentStmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new PageBean(1980, films);
    }

    @Override
    public String findMaoYanDataByConPageXML(int pageCode, int pageSize) {
        return buildXMLPageBean(findMaoYanDataByConPage(pageCode, pageSize));
    }

    @Override
    public String findMaoYanDataByMovieName(String name) {
        String res = "";
        conn = new MySQLConnector().getConnection(db);

        try {
            String infoSql = "select * from `filminfo` where `name` = ?";

            String descriptionSql = "select d.filmName, d.description from `filmdescription` d " +
                    "where `filmName` = ?";

            String commentSql = "select c.id, c.filmName, c.comment from `filmcomment` c " +
                    "where c.filmName = ?";

            res = searchFilms(name, infoSql, descriptionSql, commentSql);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public String searchMaoYanMovies(String name) {
        String res = "";
        conn = new MySQLConnector().getConnection(db);

        try {
            String infoSql = "select * from `filminfo` where `name` like ?";

            String descriptionSql = "select d.filmName, d.description from `filmdescription` d " +
                    "where `filmName` like ?";

            String commentSql = "select c.id, c.filmName, c.comment from `filmcomment` c " +
                    "where c.filmName like ?";

           res = searchFilms(name, infoSql, descriptionSql, commentSql);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    private String buildXMLPageBean(PageBean pageBean) {
        return writeXML((List<Film>)pageBean.getRows());
    }

    private ArrayList<Film> getFilmData(ResultSet infoRs,
                                        ResultSet descriptionRs, ResultSet commentRs){
        ArrayList<Film> res = new ArrayList<>();
        try{
            ArrayList<FilmComment> comments = new ArrayList<>();
            while(commentRs.next()){
                comments.add(new FilmComment(commentRs.getInt("id"),
                        commentRs.getString("filmName"),
                        commentRs.getString("comment")));
            }

            while(infoRs.next()){
                int id = infoRs.getInt("id");
                String name = infoRs.getString("name");
                String director = infoRs.getString("director");
                String type = infoRs.getString("type");
                String filmingLocation = infoRs.getString("filmingLocation");
                String duration = infoRs.getString("duration");
                String releasedTime = infoRs.getString("releasedTime");
                String score = infoRs.getString("score");
                String description = "";
                if(descriptionRs.next()) {
                    if(descriptionRs.getString("filmName").equals(name))
                        description = descriptionRs.getString("description");
                }
                ArrayList<FilmComment> fc = new ArrayList<>();
                for(FilmComment one : comments) {
                    if(one.getFilmName().equals(name))
                        fc.add(one);
                }
                Film film = new Film(id, name, director, type, filmingLocation, duration, releasedTime,
                        score, description, fc);
                res.add(film);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    private String writeXML(List<Film> films){
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement("maoYanFilm");

        for(Film one : films){
            Element filmInfo = root.addElement("filmInfo");
            filmInfo.addAttribute("id", one.getId() + "");
            filmInfo.addElement("name").addText(one.getName());
            filmInfo.addElement("director").addText(one.getDirector());
            filmInfo.addElement("type").addText(one.getType());
            filmInfo.addElement("filmingLocation").addText(one.getFilmingLocation());
            filmInfo.addElement("duration").addText(one.getDuration());
            filmInfo.addElement("releasedTime").addText(one.getReleasedTime());
            filmInfo.addElement("score").addText(one.getScore());

            Element filmDescription = root.addElement("filmDescription");
            filmDescription.addElement("description").addText(one.getDescription());

            Element filmComments = root.addElement("filmComments");
            for(FilmComment oneFilmComment : one.getFilmCommentList()){
                filmComments.addElement("hotComment").addText(oneFilmComment.getComment());
            }
        }

        String fileName ="maoYan/" + new Date().getTime() + ".xml";

        try {
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter( new FileOutputStream(new File(fileName)), format);
            writer.write(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    private String searchFilms(String name,
                               String infoSql, String descriptionSql, String commentSql){
        String res = "";
        conn = new MySQLConnector().getConnection(db);
        try{
            PreparedStatement infoStmt = conn.prepareStatement(infoSql);
            PreparedStatement descriptionStmt = conn.prepareStatement(descriptionSql);
            PreparedStatement commentStmt = conn.prepareStatement(commentSql);

            infoStmt.setString(1, name);

            descriptionStmt.setString(1, name);

            commentStmt.setString(1, name);

            ResultSet infoRs = infoStmt.executeQuery();
            ResultSet descriptionRs = descriptionStmt.executeQuery();
            ResultSet commentRs = commentStmt.executeQuery();

            ArrayList<Film> selectedFilm = getFilmData(infoRs, descriptionRs, commentRs);
            res = writeXML(selectedFilm);

            infoRs.close();
            infoStmt.close();
            descriptionRs.close();
            descriptionStmt.close();
            commentRs.close();
            commentStmt.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

}

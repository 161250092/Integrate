package com.mwx.springboot.dao;

import com.mwx.springboot.entity.Hotcomment;
import com.mwx.springboot.entity.Movie;
import com.mwx.springboot.entity.PageBean;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

}

package com.mwx.springboot.service;

import com.mwx.springboot.dao.MaoyanDataService;
import com.mwx.springboot.dao.MaoyanDataServiceImpl;
import com.mwx.springboot.entity.PageBean;
import com.mwx.springboot.entity.maoyan.Film;
import com.mwx.springboot.entity.maoyan.FilmComment;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class MaoyanServiceImpl implements MaoyanService{
    @Autowired
    MaoyanDataService maoyanDataService;

    @Override
    public PageBean findMaoYanDataByConPage(int pageCode, int pageSize) {
        return maoyanDataService.findMaoYanDataByConPage(pageCode,pageSize);
    }

    @Override
    public Film findMaoYanDataByName(String name) {
        //新xml文件的名称
       String src = new MaoyanDataServiceImpl().findMaoYanDataByMovieName(name);
//        src = "maoYan/" + src;
        //String src = "maoYan/1558108339178.xml";
        //复制数据到xml
        String dest = "xslts/maoYanFilm.xml";
        String xslt = "xslts/xsltMaoYan.xsl";
        new MaoyanServiceImpl().copyToXml(src, dest, xslt);

        return new MaoyanServiceImpl().getMovie(src);
    }

    @Override
    public List<Film> searchMaoYanFilm(String searchInfo) {
        //新xml文件的名称
        String src = maoyanDataService.searchMaoYanMovies(searchInfo);
//        src = "maoYan/" + src;
        //复制数据到xml
        String dest = "xslts/maoYanFilm.xml";
        String xslt = "xslts/xsltMaoYan.xsl";
        new MaoyanServiceImpl().copyToXml(src, dest, xslt);

        return new MaoyanServiceImpl().getMovies(src);
    }

    //转换豆瓣的数据到xml中
    private void copyToXml(String src, String dest, String xslt){
        File src2 = new File(src);
        File dest2 = new File(dest);
        File xslt2 = new File (xslt);

        Source srcSource = new StreamSource(src2);
        Result destResult = new StreamResult(dest2);
        Source xsltSource = new StreamSource(xslt2);

        try{
            TransformerFactory transFact = TransformerFactory.newInstance();
            Transformer trans = transFact.newTransformer(xsltSource);
            trans.transform(srcSource,destResult);
        }catch(TransformerConfigurationException e){
            e.printStackTrace();
        }catch(TransformerFactoryConfigurationError e){
            e.printStackTrace();
        }catch(TransformerException e){
            e.printStackTrace();
        }
    }

    //获得movie的返回值
    private Film getMovie(String src){
        Film film = new Film();
        try {
            //创建SAXReader对象
            SAXReader reader = new SAXReader();
            //读取文件 转换成Document
            Document document = null;
            document = reader.read(new File(src));
            //获取根节点元素对象
            Element root = document.getRootElement();

            //同时迭代当前节点下面的所有子节点
            List<Element> maoYanFilms = root.elements();
            //filmInfo
            film.setName(maoYanFilms.get(0).element("filmInfo").elementText("name"));
            film.setDirector(maoYanFilms.get(0).element("filmInfo").elementText("director"));
            film.setType(maoYanFilms.get(0).element("filmInfo").elementText("type"));
            film.setFilmingLocation(maoYanFilms.get(0).element("filmInfo").elementText("filmingLocation"));
            film.setDuration(maoYanFilms.get(0).element("filmInfo").elementText("duration"));
            film.setReleasedTime(maoYanFilms.get(0).element("filmInfo").elementText("releasedTime"));
            film.setScore(maoYanFilms.get(0).element("filmInfo").elementText("score"));
            //filmDescription
            film.setDescription(maoYanFilms.get(0).element("filmDescription").elementText("description"));
            //filmComments
            List<FilmComment> coms = new ArrayList<>();
            List<Element> elements = maoYanFilms.get(0).element("filmComments").elements();
            for(Element e:elements){
                FilmComment com = new FilmComment();
                com.setId(Integer.parseInt(maoYanFilms.get(0).element("filmInfo").attributeValue("id")));
                com.setComment(e.getText());
                com.setFilmName(maoYanFilms.get(0).element("filmInfo").elementText("name"));
                coms.add(com);
            }
            film.setFilmCommentList(coms);

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return film;
    }

    //获得movies的返回值
    private List<Film> getMovies(String src){
        List<Film> films = new ArrayList<>();
        try {
            //创建SAXReader对象
            SAXReader reader = new SAXReader();
            //读取文件 转换成Document
            Document document = null;
            document = reader.read(new File(src));
            //获取根节点元素对象
            Element root = document.getRootElement();

            //同时迭代当前节点下面的所有子节点
            List<Element> maoYanFilms = root.elements();
            for(int i = 0; i < maoYanFilms.size(); i++){
                Film film = new Film();
                //filmInfo
                film.setName(maoYanFilms.get(0).element("filmInfo").elementText("name"));
                film.setDirector(maoYanFilms.get(0).element("filmInfo").elementText("director"));
                film.setType(maoYanFilms.get(0).element("filmInfo").elementText("type"));
                film.setFilmingLocation(maoYanFilms.get(0).element("filmInfo").elementText("filmingLocation"));
                film.setDuration(maoYanFilms.get(0).element("filmInfo").elementText("duration"));
                film.setReleasedTime(maoYanFilms.get(0).element("filmInfo").elementText("releasedTime"));
                film.setScore(maoYanFilms.get(0).element("filmInfo").elementText("score"));
                //filmDescription
                film.setDescription(maoYanFilms.get(0).element("filmDescription").elementText("description"));
                //filmComments
                List<FilmComment> coms = new ArrayList<>();
                List<Element> elements = maoYanFilms.get(0).element("filmComments").elements();
                for(Element e:elements){
                    FilmComment com = new FilmComment();
                    com.setId(Integer.parseInt(maoYanFilms.get(0).element("filmInfo").attributeValue("id")));
                    com.setComment(e.getText());
                    com.setFilmName(maoYanFilms.get(0).element("filmInfo").elementText("name"));
                    coms.add(com);
                }
                film.setFilmCommentList(coms);

                films.add(film);
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return films;
    }
}

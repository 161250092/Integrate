package com.mwx.springboot.service;

import com.mwx.springboot.entity.PageBean;
import com.mwx.springboot.entity.integrate.Comment;
import com.mwx.springboot.entity.integrate.IntegratedFilm;
import com.mwx.springboot.test.Xslt;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class IntegratedFilmServiceImpl implements  IntegratedFilmService{


    @Override
    public PageBean findIntegratedFilmsByConPage(int pageCode, int pageSize) {
        return null;
    }

    @Override
    public IntegratedFilm findIntegratedFilmByName(String name) {
        //生成自己的两个xml文件
        new DoubanServiceImpl().findDouBanDataByMovieName(name);
        new MaoyanServiceImpl().findMaoYanDataByName(name);

        //解析两个xml后，生成convertedResult.xml
        //新xml文件的名称
        String src = "xslts/douBanMovies.xml";
        //复制数据到xml
        String dest = "xslts/convertedResult.xml";
        String xslt = "xslts/convert.xsl";
        new IntegratedFilmServiceImpl().copyToXml(src, dest, xslt);

        return new IntegratedFilmServiceImpl().getIntegratedFilm("xslts/convertedResult.xml");
    }

    @Override
    public List<IntegratedFilm> searchIntegratedFilm(String searchInfo) {
        //生成自己的两个xml文件
        new DoubanServiceImpl().searchDouBanMovies(searchInfo);
        new MaoyanServiceImpl().searchMaoYanFilm(searchInfo);

        //解析两个xml后，生成convertedResult.xml
        //新xml文件的名称
        String src = "xslts/douBanMovies.xml";
        //复制数据到xml
        String dest = "xslts/convertedResult.xml";
        String xslt = "xslts/convert.xsl";
        new IntegratedFilmServiceImpl().copyToXml(src, dest, xslt);

        return new IntegratedFilmServiceImpl().getIntegratedFilms("xslts/convertedResult.xml");
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

    //获得IntegratedFilm的返回值
    private IntegratedFilm getIntegratedFilm(String src){
        IntegratedFilm ret = new IntegratedFilm();
        try {
            //创建SAXReader对象
            SAXReader reader = new SAXReader();
            //读取文件 转换成Document
            Document document = null;
            document = reader.read(new File(src));
            //获取根节点元素对象
            Element root = document.getRootElement();

            //同时迭代当前节点下面的所有子节点
            List<Element> movies = root.elements();
            ret.setTitle(movies.get(0).elementText("title"));
            ret.setDirectors(movies.get(0).elementText("directors"));
            ret.setRate(movies.get(0).elementText("rate"));
            ret.setCasts(movies.get(0).elementText("casts"));
            ret.setType(movies.get(0).elementText("type"));
            ret.setNation(movies.get(0).elementText("nation"));
            ret.setLanguage(movies.get(0).elementText("language"));
            ret.setDate(movies.get(0).elementText("date"));
            ret.setTime(movies.get(0).elementText("time"));
            ret.setPeopleNumber(movies.get(0).elementText("peopleNumber"));
            ret.setIntroduction(movies.get(0).elementText("introduction"));

            List<Comment> coms = new ArrayList<>();
            List<Element> hotComments = movies.get(0).element("hotComments").elements();
            for(Element e : hotComments){
                Comment h = new Comment();
                h.setMovie_name(movies.get(0).elementText("title"));
                h.setComment(e.elementText("hotComment"));
                coms.add(h);
            }
            ret.setCommentList(coms);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return ret;
    }

    //获得IntegratedFilms的返回值
    private List<IntegratedFilm> getIntegratedFilms(String src){
        List<IntegratedFilm> rets = new ArrayList<>();
        try {
            //创建SAXReader对象
            SAXReader reader = new SAXReader();
            //读取文件 转换成Document
            Document document = null;
            document = reader.read(new File(src));
            //获取根节点元素对象
            Element root = document.getRootElement();

            //同时迭代当前节点下面的所有子节点
            List<Element> movies = root.elements();
            for(int i = 0; i < movies.size(); i++){
                IntegratedFilm ret = new IntegratedFilm();
                ret.setTitle(movies.get(i).elementText("title"));
                ret.setDirectors(movies.get(i).elementText("directors"));
                ret.setRate(movies.get(i).elementText("rate"));
                ret.setCasts(movies.get(i).elementText("casts"));
                ret.setType(movies.get(i).elementText("type"));
                ret.setNation(movies.get(i).elementText("nation"));
                ret.setLanguage(movies.get(i).elementText("language"));
                ret.setDate(movies.get(i).elementText("date"));
                ret.setTime(movies.get(i).elementText("time"));
                ret.setPeopleNumber(movies.get(i).elementText("peopleNumber"));
                ret.setIntroduction(movies.get(i).elementText("introduction"));

                List<Comment> coms = new ArrayList<>();
                List<Element> hotComments = movies.get(i).element("hotComments").elements();
                for(Element e : hotComments){
                    Comment h = new Comment();
                    h.setMovie_name(movies.get(0).elementText("title"));
                    h.setComment(e.elementText("hotComment"));
                    coms.add(h);
                }
                ret.setCommentList(coms);

                rets.add(ret);
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return rets;
    }
}

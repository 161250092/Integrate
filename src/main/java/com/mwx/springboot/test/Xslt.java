package com.mwx.springboot.test;

import com.mwx.springboot.entity.douban.Movie;
import com.mwx.springboot.entity.maoyan.Film;
import com.mwx.springboot.service.DoubanServiceImpl;
import com.mwx.springboot.service.MaoyanServiceImpl;

import java.io.File;
import java.io.StringWriter;

import javax.xml.transform.Source;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class Xslt{
	public static void main(String argv[]){
		/*
		//新xml文件的名称
		String src = "xslts/douBanMovies.xml";
		//复制数据到xml
		String dest = "xslts/convertedResult.xml";
		String xslt = "xslts/convert.xsl";
		new Xslt().copyToXml(src, dest, xslt);
		*/

		Movie m = new DoubanServiceImpl().findDouBanDataByMovieName("20190517133453301.xml");
		System.out.println(m.getCasts());
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
}
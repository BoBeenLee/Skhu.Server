package com.skhu.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skhu.model.Calendar;
import com.skhu.model.Category;
import com.skhu.model.Food;
import com.skhu.model.SkhuArticle;
import com.skhu.util.BeanUtils;
import com.skhu.util.DateUtils;
import com.skhu.util.NetUtils;

@Service("documentService")
public class DocumentService {
	public static final String SKHU_URL = "http://www.skhu.ac.kr";
	@Autowired
	private HttpConnection httpConnection;
	
	public Document getDocument(String url){
		Connection con;
		Document doc = null;
		
//		System.out.println("http = "  + httpConnection);
		try {
			con = httpConnection.connect(url);
			doc = con.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	public  void setSkhuArticle(SkhuArticle article) {
//		System.out.println(article.url);
		Document doc = getDocument(article.url);
		if(doc == null)
			return ;
		
		Elements tHead = doc.select("#cont thead"); 
		Elements tBody = doc.select("#cont tbody"); 
		Elements trs = tBody.get(0).getElementsByTag("tr");
		
		// head
		// title
		article.subject = tHead.get(0).select("td span:eq(0)").html();
		// body
		Elements td1 = tBody.get(0).select("tr:eq(0) td"); 
		// created
		article.created = DateUtils.stringToDate(td1.get(0).select("span").html(), DateUtils.dateForm2); 
		// writer
		article.name = td1.get(1).select("span").html();
		// content
//		article.content = tBody.get(0).select("tr:eq(2) td:eq(0) span").html().getBytes(); // .getElementsByTag("tr").get(2).getElementsByTag("td").get(0).getElementsByTag("span").html()).getBytes();
//		BeanUtils.printBean(article);
	}
	
	// 여기서 href 찾고 각각의 url에서 정보를 추출
	public  List<SkhuArticle> listSkhuArticle(Category category, int pageNum) {
		// http://www.skhu.ac.kr/board/boardread.aspx?idx=22311&curpage=1&bsid=10004&searchBun=51
		Document doc = getDocument(SKHU_URL + "/board" + "/boardlist.aspx?curpage=" + pageNum + "&bsid=" + category.cateId);
		if(doc == null)
			return null;
		ArrayList<SkhuArticle> articles = new ArrayList<SkhuArticle>();
		
		Elements trs = doc.select("#cont tbody tr"); 
		addNormalSkhu(trs, articles, category, "board");
		
		Elements lis = doc.select("#cont .board_list2 li");
		addSpecialSkhu(lis, articles, category);
		
//		System.out.println(SKHU_URL + "/boardlist.aspx?curpage=" + pageNum + "&bsid=" + category.cateId);
		return articles;
	}
	
	// 여기서 href 찾고 각각의 url에서 정보를 추출
	public  List<SkhuArticle> listQnA(Category category, int pageNum) {
		// http://www.skhu.ac.kr/board/boardread.aspx?idx=22311&curpage=1&bsid=10004&searchBun=51
		Document doc = getDocument(SKHU_URL + "/board" + "/boardlist.aspx?curpage=" + pageNum + "&bsid=10016&searchBun=" + category.cateId);
		if(doc == null)
			return null;
		ArrayList<SkhuArticle> articles = new ArrayList<SkhuArticle>();
		
		Elements trs = doc.select("#cont tbody tr"); 
		addQnA(trs, articles, category, "board");
		
		return articles;
	}
	
	public  boolean addQnA(Elements trs, ArrayList<SkhuArticle> articles, Category category, String type){
		for(int i=0, length=trs.size(); i<length; i++){
			SkhuArticle article = new SkhuArticle();
			Element tr = trs.get(i);
			article.brdNo = tr.select("td:eq(0) span").get(0).html();
			article.url = SKHU_URL + "/" + type + "/" + tr.select("td:eq(1) a").get(0).attr("href");
			article.subject = tr.select("td:eq(1) a").get(0).html();
			article.name = tr.select("td:eq(2)").html();
			article.created = DateUtils.stringToDate(tr.select("td:eq(3)").html(), DateUtils.dateForm2);
			article.commentCnt = Integer.parseInt(tr.select("td:eq(4)").html());
			String state = tr.select("td:eq(5) img").attr("alt");
			if(state.equals("확인중"))
				article.replyState = 1;
			else if(state.equals("답변완료"))
				article.replyState = 2;
			else if(state.equals("유선통보"))
				article.replyState = 3;
			else if(state.equals("이메일통보"))
				article.replyState = 4;
			article.cateId = category.cateId;
			article.cateNo = category.cateNo;
			article.cateNm = category.name;
			
			articles.add(article);
//			BeanUtils.printBean(article);
		}
		return (trs.size() == 0)? false : true;
	}
	
	public  boolean addNormalSkhu(Elements trs, ArrayList<SkhuArticle> articles, Category category, String type){
		for(int i=0, length=trs.size(); i<length; i++){
			SkhuArticle article = new SkhuArticle();
			Element tr = trs.get(i);
			article.brdNo = tr.select("td:eq(0) span").get(0).html();
			article.url = SKHU_URL + "/" + type + "/" + tr.select("td:eq(1) a").get(0).attr("href");
			article.cateId = category.cateId;
			article.cateNo = category.cateNo;
			article.cateNm = category.name;
			articles.add(article);
//			BeanUtils.printBean(article);
		}
		return (trs.size() == 0)? false : true;
	}
	
	public  boolean addSpecialSkhu(Elements lis, ArrayList<SkhuArticle> articles, Category category){
		for(int i=0, length=lis.size(); i<length; i++){
			SkhuArticle article = new SkhuArticle();
			Element li = lis.get(i);
			article.url = SKHU_URL + "/board/" + li.select("p a").get(0).attr("href");
			
			try {
				article.brdNo = NetUtils.splitQuery(new URL(article.url)).get("idx").get(0);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			article.cateId = category.cateId;
			article.cateNo = category.cateNo;
			article.cateNm = category.name;
			articles.add(article);
//			BeanUtils.printBean(article);
		}
		return (lis.size() == 0)? false : true;
	}
	
	public  List<Food> getFoodMenu(){
		String url = "http://www.skhu.ac.kr/uni_zelkova/uni_zelkova_4_3_list.aspx";
		Document doc = getDocument(url);
		if(doc == null)
			return null;
		
		ArrayList<SkhuArticle> articles = new ArrayList<SkhuArticle>();
		Category category = new Category();
		
		Elements trs = doc.select("#cont tbody tr"); 
		addNormalSkhu(trs, articles, category, "uni_zelkova");
		return getFoods(articles.get(0));
	}
	
	public  List<Food> getFoods(SkhuArticle article){
		ArrayList<Food> foods = new ArrayList<Food>();
		Document doc = getDocument(article.url);
		if(doc == null)
			return null;
		Elements cont = doc.select("#cont .box_menu"); 

		Elements dates = cont.select(".item_th th:eq(1) ~ th");
		Elements lunch = cont.select("tbody tr:eq(0) td");
		Elements specialLunch = cont.select("tbody tr:eq(2) td");
		Elements dinner = cont.select("tbody tr:eq(6) td");
		
		for(int i=0; dates != null && i < dates.size(); i++){
			if(!dates.get(i).html().equals("")){
				Food food = new Food();
				food.date = dates.get(i).html();
				food.lunch = lunch.get(i).html().replaceAll("<br />", ",");
				food.specialLunch = specialLunch.get(i).html().replaceAll("<br />", ",");
				food.dinner = dinner.get(i).html().replaceAll("<br />", ",");
//				System.out.println(dates.get(i).html());
//				System.out.println("중식 : " + lunch.get(i).html().replaceAll("<br />", ","));
//				System.out.println("특선 : " + specialLunch.get(i).html().replaceAll("<br />", ","));
//				System.out.println("석식 : " + dinner.get(i).html().replaceAll("<br />", ","));
				foods.add(food);
			}
		}
		return foods;
	}

	public List<Calendar> getCalendarMenu(int year, int month) {
		String url = "http://skhu.ac.kr/calendar/calendar_list_1.aspx?strYear=" + year + "&strMonth=" + month;
		Document doc = getDocument(url);
		if(doc == null)
			return null;
		
		Elements trs = doc.select(".calendar .info table tbody tr:eq(0) ~ tr");
		
		ArrayList<Calendar> calendars = new ArrayList<Calendar>();
		
		for(int i=0; trs != null && i < trs.size(); i++){
			Calendar cal = new Calendar();
			
			String days = trs.get(i).select("td.day").html();
			String txt = trs.get(i).select("td.txt").html();
			
			Timestamp stime = DateUtils.stringToDate(year + "-" + days.split("~")[0].trim(), DateUtils.dateForm2);
			Timestamp ltime = null;
			
			if(days.split("~").length > 1)
				ltime = DateUtils.stringToDate(year + "-" + days.split("~")[1].trim(), DateUtils.dateForm2);
			cal.sDate = cal.lDate = stime; 
			if(ltime != null)
			cal.lDate = ltime;
			cal.content = txt;
			calendars.add(cal);
			
//			System.out.println("S : " + stime + " , L : " + ltime + " txt : " + txt);
		}
		return calendars;
	}
}

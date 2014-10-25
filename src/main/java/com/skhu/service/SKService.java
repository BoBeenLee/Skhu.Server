package com.skhu.service;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skhu.mapper.CateMapper;
import com.skhu.mapper.SkhuBrdMapper;
import com.skhu.model.APICode;
import com.skhu.model.Calendar;
import com.skhu.model.Category;
import com.skhu.model.DBType;
import com.skhu.model.FacebookForm;
import com.skhu.model.Food;
import com.skhu.model.SkhuArticle;
import com.skhu.model.code.SK0001;
import com.skhu.model.code.SK0001.SK0001Article;
import com.skhu.model.code.SK0002;
import com.skhu.model.code.SK0002.SK0002Article;
import com.skhu.model.code.SK0004;
import com.skhu.model.code.SK0004.SK0004Category;
import com.skhu.model.code.SK0005;
import com.skhu.model.code.SK0005.SK0005Food;
import com.skhu.model.code.SK0006;
import com.skhu.model.code.SK0006.SK0006Calendar;
import com.skhu.util.DateUtils;
import com.skhu.util.JacksonUtils;
import com.skhu.util.NetUtils;

@Service("skService")
public class SKService {
	@Autowired
	CateMapper cateMapper;
	@Autowired
	SkhuBrdMapper skhuBrdMapper;
	@Autowired
	DocumentService documentService;
	@Autowired
	FacebookService facebookService;
	
	
	public APICode resSK0001(APICode reqCode) {
		HashMap<String, String> hashMap = (HashMap<String, String>) reqCode.tranData;
		SK0001 sk = JacksonUtils
				.<SK0001> hashMapToObject(hashMap, SK0001.class);

		List<Integer> cateNo = sk.cateNo;
		int orderType = sk.orderType;
		int pageNum = sk.reqPoCnt;
		int reqPoNo = sk.reqPoNo;
		int dbType = sk.dbType;

		List<SkhuArticle> articles = new ArrayList<SkhuArticle>();
		if (dbType == DBType.SKHU && cateNo.size() > 0)
			articles = skhuBrdMapper.getSkhuBrds(cateNo, reqPoNo, pageNum);
		else if (dbType == DBType.QNA && cateNo.size() > 0)
			articles = skhuBrdMapper.getQnABrds(cateNo, reqPoNo, pageNum);
		ArrayList<SK0001Article> skArticles = new ArrayList<SK0001.SK0001Article>();

		for (int i = 0; articles != null && i < articles.size(); i++) {
			SK0001Article skArticle = new SK0001Article();
			if (articles.get(i).attach != null)
				skArticle.brdAttachUrls = Arrays.<String> asList(articles
						.get(i).attach.split(","));

			skArticle.brdCate = articles.get(i).cateNo;
			skArticle.brdCateNm = articles.get(i).cateNm;
			skArticle.brdCommentCnt = articles.get(i).commentCnt;
			skArticle.brdContent = articles.get(i).content;
			skArticle.brdCreated = DateUtils.dateToString(
					articles.get(i).created, DateUtils.dateForm2);
			skArticle.brdId = articles.get(i).id;
			if (articles.get(i).img != null)
				skArticle.brdImgUrls = Arrays
						.<String> asList(articles.get(i).img.split(","));
			skArticle.brdNm = articles.get(i).name;
			skArticle.brdNo = articles.get(i).brdNo;
			skArticle.brdSubject = articles.get(i).subject;
			skArticle.brdUrl = articles.get(i).url;
			skArticle.replyState = articles.get(i).replyState;
			skArticles.add(skArticle);
		}
		sk.res = skArticles;
		sk.resCnt = skArticles.size();
		sk.resDate = DateUtils.currentTime(DateUtils.dateForm1);
		sk.resLastNo = sk.reqPoNo + sk.resCnt;
		APICode resCode = this.<SK0001> processCommonResponse(reqCode, sk);
		return resCode;
	}

	public APICode resSK0002(APICode reqCode) {
		HashMap<String, String> hashMap = (HashMap<String, String>) reqCode.tranData;
		SK0002 sk = JacksonUtils
				.<SK0002> hashMapToObject(hashMap, SK0002.class);

		List<SK0002Article> articles = new ArrayList<SK0002Article>();
		boolean isEmpty = false;
		boolean isStart = true;
		int pageNum = 15;
		FacebookForm fk = null;

		Category category = cateMapper.getCategory(sk.cateNo);

		while (category != null && articles.size() < sk.reqPoCnt && !isEmpty) {
			if (isStart) {
				fk = JacksonUtils.<FacebookForm> jsonToObject(facebookService
						.graphToJson(category.cateId, sk.reqPoNo, pageNum),
						FacebookForm.class);
				isStart = false;
			} else
				fk = JacksonUtils.<FacebookForm> jsonToObject(
						facebookService.graphToJson(fk.paging.next),
						FacebookForm.class);

			// System.out.println(fk.data.length);
			if (fk.data != null && fk.data.length > 0) {
				for (int i = 0; i < fk.data.length
						&& articles.size() < sk.reqPoCnt; i++) {
					if ((fk.data[i].message != null || fk.data[i].picture != null)
							&& fk.data[i].link != null) {
						SK0002Article article = new SK0002Article();

						System.out.println("facebook : " + fk.data[i].from.name
								+ " - " + fk.data[i].id + " - "
								+ fk.data[i].link + " - "
								+ fk.data[i].updatedTime);
						article.brdId = fk.data[i].id;
						article.brdNm = fk.data[i].from.name;
						if (fk.data[i].message != null)
							article.brdContent = fk.data[i].message.getBytes();
						article.brdImgUrl = fk.data[i].picture;
						article.brdUrl = FacebookService.FACEBOOK_ORIGIN_URL
								+ "/" + fk.data[i].id.split("_")[0] + "/posts/"
								+ fk.data[i].id.split("_")[1];
						article.brdCreated = fk.data[i].updatedTime;
						// fk.paging.
						if (fk.paging.next != null) {
							String nextUrl = fk.paging.next;
							try {
								sk.resLastNo = NetUtils
										.splitQuery(new URL(nextUrl))
										.get("until").get(0);
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							} catch (MalformedURLException e) {
								e.printStackTrace();
							}
						}
						articles.add(article);
					}
				}
			} else
				isEmpty = true;
		}
		sk.resCnt = articles.size();
		sk.res = articles;
		sk.resDate = DateUtils.currentTime(DateUtils.dateForm1);

		APICode resCode = this.<SK0002> processCommonResponse(reqCode, sk);
		return resCode;
	}

	public APICode resSK0004(APICode reqCode) {
		HashMap<String, String> hashMap = (HashMap<String, String>) reqCode.tranData;
		SK0004 sk = JacksonUtils
				.<SK0004> hashMapToObject(hashMap, SK0004.class);

		int type = sk.type;
		int dbType = sk.dbType;
		int depth = sk.depth;
		int parentNo = sk.parentCateNo;
		List<Category> categories = null;

		if (SK0004.PARENT == type)
			categories = cateMapper.getCategoriesByParentNo(parentNo, dbType);
		else if (SK0004.DEPTH == type)
			categories = cateMapper.getCategoriesByDepth(depth, dbType);

		ArrayList<SK0004Category> skCategories = new ArrayList<SK0004Category>();

		for (int i = 0; categories != null && i < categories.size(); i++) {
			SK0004Category skCategory = new SK0004Category();
			skCategory.cateNo = categories.get(i).cateNo;
			skCategory.cateId = categories.get(i).cateId;
			skCategory.name = categories.get(i).name;
			skCategory.dbType = categories.get(i).dbType;
			skCategory.depth = categories.get(i).depth;
			skCategory.parentCateNo = categories.get(i).parentCateNo;

			skCategories.add(skCategory);
		}

		sk.resCnt = skCategories.size();
		sk.res = skCategories;
		sk.resDate = DateUtils.currentTime(DateUtils.dateForm1);
		APICode resCode = this.<SK0004> processCommonResponse(reqCode, sk);
		return resCode;
	}

	public APICode resSK0005(APICode reqCode) {
		SK0005 sk = new SK0005();
		
		List<Food> foods = documentService.getFoodMenu();

		ArrayList<SK0005Food> skFoods = new ArrayList<SK0005.SK0005Food>();

		if (foods != null) {
			for (Food food : foods) {
				SK0005Food skFood = new SK0005Food();
				skFood.menuDate = food.date;
				skFood.lunch = food.lunch;
				skFood.specialLunch = food.specialLunch;
				skFood.dinner = food.dinner;
				skFoods.add(skFood);
			}
		}
		sk.res = skFoods;
		sk.resCnt = skFoods.size();
		sk.resDate = DateUtils.currentTime(DateUtils.dateForm2);

		APICode resCode = this.<SK0005> processCommonResponse(reqCode, sk);
		return resCode;
	}

	public APICode resSK0006(APICode reqCode) {
		HashMap<String, String> hashMap = (HashMap<String, String>) reqCode.tranData;
		SK0006 sk = JacksonUtils
				.<SK0006> hashMapToObject(hashMap, SK0006.class);
		
		List<Calendar> calendars = documentService.getCalendarMenu(sk.year,
				sk.month);
		ArrayList<SK0006Calendar> skCalendars = new ArrayList<SK0006.SK0006Calendar>();

		if (calendars != null) {
			for (Calendar cal : calendars) {
				SK0006Calendar skCalendar = new SK0006Calendar();
				skCalendar.sDate = cal.sDate;
				skCalendar.lDate = cal.lDate;
				skCalendar.content = cal.content;
				System.out.println(skCalendar.sDate + " - " + cal.lDate + " = " + cal.content);
				skCalendars.add(skCalendar);
			}
		}
		sk.res = skCalendars;
		sk.resCnt = skCalendars.size();
		sk.resDate = DateUtils.currentTime(DateUtils.dateForm2);

		APICode resCode = this.<SK0006> processCommonResponse(reqCode, sk);
		return resCode;
	}

	public <T> APICode<T> processCommonResponse(APICode reqCode, T sk) {
		// post Response
		APICode<T> resCode = new APICode<T>();
		resCode.tranCd = reqCode.tranCd;
		resCode.tranData = sk;
		return resCode;
	}
}

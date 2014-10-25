package com.skhu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skhu.mapper.AlarmMapper;
import com.skhu.mapper.CateMapper;
import com.skhu.mapper.SkhuBrdMapper;
import com.skhu.model.Alarm;
import com.skhu.model.Category;
import com.skhu.model.DBType;
import com.skhu.model.SkhuArticle;

@Service
public class SkhuBrdService {
	@Autowired
	CateMapper cateMapper;
	@Autowired
	SkhuBrdMapper skhuBrdMapper;
	@Autowired
	AlarmMapper alarmMapper;
	@Autowired
	GCMService gcmService;
	@Autowired
	DocumentService documentService;
	
	public boolean addSkhuBrd(int cateNo, int pageNum){
		Category category = cateMapper.getCategory(cateNo);
		String sBrdNo = null, lBrdNo = null;
		List<Alarm> alarms = null;
		List<SkhuArticle> articles = null;
		
		articles = documentService.listSkhuArticle(category, pageNum);
		if(articles == null || articles.size() < 1)
			return false;
		
		for(int i=0; articles != null && i < articles.size(); i++){
			sBrdNo = articles.get(i).brdNo;
			if(lBrdNo == null){
				int brdNo = Integer.parseInt(sBrdNo);
				lBrdNo = "" + (brdNo + 100);
			}
			skhuBrdMapper.deleteSkhuBrd(sBrdNo, lBrdNo, cateNo);
			
//			System.out.println(articles.get(i).brdNo);
			SkhuArticle article = skhuBrdMapper.getSkhuBrd(articles.get(i).brdNo, articles.get(i).cateNo);
	
			if(article == null){
				documentService.setSkhuArticle(articles.get(i));
				skhuBrdMapper.insertSkhuBrd(articles.get(i));
				
				System.out.println("article : " + article + " - " + articles.get(i).brdNo + " - " + articles.get(i).cateNo);
				alarms = alarmMapper.findFilters(articles.get(i).subject, cateNo);

				if(alarms != null && alarms.size() > 0){
					String[] tokenIds = new String[alarms.size()];
					for(int j=0; j<alarms.size(); j++)
						tokenIds[i] = alarms.get(j).tokenId;
					gcmService.sendMessage(tokenIds, category.name, articles.get(i).subject, articles.get(i).url, DBType.QNA);
				}
			}
			lBrdNo = sBrdNo;			
		}
		return true;
	}
	
	public boolean addQnABrd(int cateNo, int pageNum){
		Category category = cateMapper.getCategory(cateNo);
		String sBrdNo = null, lBrdNo = null;
		List<Alarm> alarms;
		List<SkhuArticle> articles = null;
		articles = documentService.listQnA(category, pageNum);
		if(articles == null || articles.size() < 1)
			return false;
		for(int i=0; articles != null && i < articles.size(); i++){
			sBrdNo = articles.get(i).brdNo;
			if(lBrdNo == null){
				int brdNo = Integer.parseInt(sBrdNo);
				lBrdNo = "" + (brdNo + 100);
			}
			skhuBrdMapper.deleteQnABrd(sBrdNo, lBrdNo, cateNo);
			
//			System.out.println(articles.get(i).brdNo);
			SkhuArticle article = skhuBrdMapper.getQnABrd(articles.get(i).brdNo, cateNo);
//			System.out.println("article : " + article + " - " + articles.get(i).brdNo + " - " + articles.get(i).cateNo);
			if(article == null){
				skhuBrdMapper.insertQnABrd(articles.get(i));
				
				// 게시글 생성 시
				alarms = alarmMapper.findFilters(articles.get(i).subject, cateNo);
			
				if(alarms != null && alarms.size() > 0){
					String[] tokenIds = new String[alarms.size()];
					for(int j=0; j<alarms.size(); j++)
						tokenIds[i] = alarms.get(j).tokenId;
					gcmService.sendMessage(tokenIds, category.name, articles.get(i).subject, articles.get(i).url, DBType.QNA);
				}
			} else if(article.replyState != articles.get(i).replyState) {
				// 게시글 변경 시
				skhuBrdMapper.updateQnABrd(articles.get(i));
				alarms = alarmMapper.findSrcFilters(articles.get(i).brdNo, cateNo);
				
				if(alarms != null && alarms.size() > 0){
					String[] tokenIds = new String[alarms.size()];
					for(int j=0; j<alarms.size(); j++)
						tokenIds[i] = alarms.get(j).tokenId;
					gcmService.sendMessage(tokenIds, category.name, articles.get(i).subject, articles.get(i).url, DBType.QNA);
				}
			}
			lBrdNo = sBrdNo;			
		}
		return articles != null && 0 < articles.size();
	}
}

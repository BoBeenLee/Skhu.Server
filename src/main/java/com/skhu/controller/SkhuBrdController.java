package com.skhu.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skhu.mapper.CateMapper;
import com.skhu.mapper.SkhuBrdMapper;
import com.skhu.model.Category;
import com.skhu.model.DBType;
import com.skhu.model.SkhuArticle;
import com.skhu.service.SkhuBrdService;

@Controller
@RequestMapping("/skhubrd")
public class SkhuBrdController {
	@Autowired
	CateMapper cateMapper;
	@Autowired
	SkhuBrdMapper skhuBrdMapper;
	
	@Autowired
	SkhuBrdService skhuBrdService;
	
	@RequestMapping("/add")
    public @ResponseBody String add(@RequestBody SkhuArticle skhuBrd) {
		try {
			skhuBrdMapper.insertSkhuBrd(skhuBrd);
		} catch(Exception e){
			
		}
		return "OK";
	}	
	@RequestMapping("/modify")
    public @ResponseBody String modify(@RequestBody SkhuArticle skhuBrd) {
		skhuBrdMapper.updateSkhuBrd(skhuBrd);
		return "OK";
	}	
	@RequestMapping("/remove")
    public @ResponseBody String remove(@RequestParam int no) {
//		skhuBrdMapper.deleteSkhuBrd(no);
		return "OK";
	}	
	@RequestMapping("/list")
    public @ResponseBody List<SkhuArticle> list(@RequestParam int cateNo) {
		List<SkhuArticle> skhuBrdgories = null;  // skhuBrdMapper.getSkhuBrds(cateNo);
		return skhuBrdgories;
	}	

	@RequestMapping("/update/skhu")
    public @ResponseBody String updateSkhuBrd() {
		List<Category> categories = cateMapper.getCategoriesByDepth(1, DBType.SKHU);

//		skhuBrdService.addSkhuBrd(10, 1); 
		for(int i=0; categories != null && i<categories.size(); i++){
//			System.out.println(categories.get(i).name);
			boolean isPage = true;
			int pageNum = 1;
			//while(isPage){
//				System.out.println(" isPage : " + isPage);
				isPage = skhuBrdService.addSkhuBrd(categories.get(i).cateNo, pageNum); // categories.get(i).cateNo
//				pageNum += 1;
			//}
		}
		return "OK";
	}
		
	@RequestMapping("/update/qna")
    public @ResponseBody String updateQnABrd() {
		List<Category> categories = cateMapper.getCategoriesByDepth(1, DBType.QNA);

		for(int i=0; categories != null && i<categories.size(); i++){
//			System.out.println(categories.get(i).name);
			boolean isPage = true;
			int pageNum = 1;
//			while(isPage){
//				System.out.println(" isPage : " + isPage);
				isPage = skhuBrdService.addQnABrd(categories.get(i).cateNo, pageNum); // categories.get(i).cateNo
//				pageNum += 1;
//			}
		}
		return "OK";
	}	
	
	/*
	 * 	public void updateSkhuBrd(){
		List<Category> categories = cateMapper.getCategoriesByDepth(1, DBType.SKHU);

		for(int i=0; categories != null && i<categories.size(); i++){
//			System.out.println(categories.get(i).name);
			boolean isPage = true;
			int pageNum = 1;
//			System.out.println(pageNum);
			isPage = skhuBrdService.addSkhuBrd(categories.get(i).cateNo, pageNum);
		}
		
		categories = cateMapper.getCategoriesByDepth(1, DBType.QNA);
		for(int i=0; categories != null && i<categories.size(); i++){
//			System.out.println(categories.get(i).name);
			boolean isPage = true;
			int pageNum = 1;
//			System.out.println(pageNum);
			isPage = skhuBrdService.addQnABrd(categories.get(i).cateNo, pageNum); // categories.get(i).cateNo
		}
	}
	 */
}

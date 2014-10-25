package com.skhu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skhu.mapper.AlarmMapper;
import com.skhu.mapper.CateMapper;
import com.skhu.model.Alarm;
import com.skhu.model.Category;

@Controller
@RequestMapping("/cate")
public class CateController {
	@Autowired
	CateMapper cateMapper;
	
	@RequestMapping("/add")
    public @ResponseBody String add(@RequestBody Category cate) {
		cateMapper.insertCategory(cate);
		return "OK";
	}	
	@RequestMapping("/modify")
    public @ResponseBody String modify(@RequestBody Category cate) {
		cateMapper.updateCategory(cate);
		return "OK";
	}	
	@RequestMapping("/remove")
    public @ResponseBody String remove(@RequestParam int no) {
		cateMapper.deleteCategory(no);
		return "OK";
	}	
	@RequestMapping("/get")
    public @ResponseBody Category get(@RequestParam int cateNo) {
		Category category = cateMapper.getCategory(cateNo);
		return category;
	}	
}

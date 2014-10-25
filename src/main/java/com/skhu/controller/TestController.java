package com.skhu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skhu.mapper.TestMapper;
import com.skhu.model.JavaBean;

@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	TestMapper testMapper;
	
	@RequestMapping("/add")
	public @ResponseBody JavaBean add(){
		JavaBean jb = new JavaBean();
		jb.id = 1;
		jb.content = "test";
		jb.name = "name";
		
		testMapper.insertTest(jb);
		return jb;
	}
	
	@RequestMapping("/remove")
	public @ResponseBody JavaBean remove(){
		int id = 1;
		
		testMapper.deleteTest(id);
		return new JavaBean();
	}
}

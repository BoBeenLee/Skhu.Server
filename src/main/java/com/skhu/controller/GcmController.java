package com.skhu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skhu.mapper.GCMMapper;
import com.skhu.model.Gcm;

@Controller
@RequestMapping("/gcm")
public class GcmController {
	@Autowired
	GCMMapper gcmMapper;
	
	@RequestMapping("/add")
    public @ResponseBody String add(@RequestBody Gcm gcm) {
		gcmMapper.insertGcm(gcm);
		return "OK";
	}	
	@RequestMapping("/modify")
    public @ResponseBody String modify(@RequestBody Gcm gcm) {
		gcmMapper.updateGcm(gcm);
		return "OK";
	}	
	@RequestMapping("/remove")
    public @ResponseBody String remove(@RequestParam int no) {
		gcmMapper.deleteGcm(no);
		return "OK";
	}	
	@RequestMapping("/get")
    public @ResponseBody Gcm get(@RequestParam("ip") String mac) {
		System.out.println("gcm : get");
		Gcm gcm = gcmMapper.getGcm(mac);
		return gcm;
	}	
}

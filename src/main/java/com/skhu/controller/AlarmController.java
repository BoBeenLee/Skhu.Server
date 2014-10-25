package com.skhu.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skhu.mapper.AlarmMapper;
import com.skhu.mapper.SkhuBrdMapper;
import com.skhu.model.Alarm;
import com.skhu.model.SkhuArticle;
import com.skhu.service.GCMService;


@Controller
@RequestMapping("/alarm")
public class AlarmController {
	@Autowired
	GCMService gcmService;
	
	@Autowired
	AlarmMapper alarmMapper;
	@Autowired
	SkhuBrdMapper skhuBrdMapper;
	
	@RequestMapping("/test")
	public void alarm(){
		List<Alarm> alarms = alarmMapper.getAllAlarms();
		List<String> tokenIds = new ArrayList<String>();
		for(int i=0; alarms != null && i < alarms.size(); i++){
			Alarm alarm = alarms.get(i);
			List<SkhuArticle> articles = skhuBrdMapper.getSkhuBrdsByFilter(alarm.cateNo, alarm.filter);
			if(articles.size() > 0)
				tokenIds.add(alarm.tokenId);
		}
//		try {
//			gcmService.sendMessage(tokenIds.toArray(new String[tokenIds.size()]));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	@RequestMapping("/add")
    public @ResponseBody String add(@RequestBody Alarm alarm) {
		alarmMapper.insertAlarm(alarm);
		return "OK";
	}	
	@RequestMapping("/remove")
    public @ResponseBody String remove(@RequestParam int no) {
		alarmMapper.deleteAlarm(no);
		return "OK";
	}	
	@RequestMapping("/list")
    public @ResponseBody List<Alarm> list(@RequestParam("ip") String mac) {
		List<Alarm> alarms = alarmMapper.getAlarms(mac);
		return alarms;
	}	
}

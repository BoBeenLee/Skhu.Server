package com.skhu.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.skhu.model.APICode;
import com.skhu.model.FacebookForm;
import com.skhu.service.FacebookService;
import com.skhu.service.GCMService;
import com.skhu.service.PSService;
import com.skhu.service.SKService;
import com.skhu.util.BeanUtils;
import com.skhu.util.JacksonUtils;


@Controller
public class AppController {
	@Autowired
	SKService skService;
	@Autowired
	PSService psService;
	
	@RequestMapping(value = "/skhu")
	public @ResponseBody APICode mappingCode(@RequestBody APICode reqCode){
		APICode resCode = new APICode();
		String code = reqCode.tranCd;
		
		System.out.println("----------------- " + reqCode.tranCd + " --------------------");
		if(code.equals("SK0001")){
			resCode = skService.resSK0001(reqCode);
		} else if(code.equals("SK0002")){
			resCode = skService.resSK0002(reqCode);
		}  else if(code.equals("SK0004")){
			resCode = skService.resSK0004(reqCode);
		} else if(code.equals("SK0005")){
			resCode = skService.resSK0005(reqCode);
		} else if(code.equals("SK0006")){
			resCode = skService.resSK0006(reqCode);
		} else if(code.equals("PS0001")){
			resCode = psService.resPS0001(reqCode);
		} else if(code.equals("PS0002")){
			resCode = psService.resPS0002(reqCode);
		} else if(code.equals("PS0003")){
			resCode = psService.resPS0003(reqCode);
		} else if(code.equals("PS0004")){
			resCode = psService.resPS0004(reqCode);
		} else if(code.equals("PS0005")){
			resCode = psService.resPS0005(reqCode);
		}
		return resCode;
	}
}

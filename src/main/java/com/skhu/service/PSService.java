package com.skhu.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skhu.mapper.AlarmMapper;
import com.skhu.mapper.GCMMapper;
import com.skhu.model.APICode;
import com.skhu.model.Alarm;
import com.skhu.model.Gcm;
import com.skhu.model.code.PS0001;
import com.skhu.model.code.PS0002;
import com.skhu.model.code.PS0003;
import com.skhu.model.code.PS0003.PS0003Alarm;
import com.skhu.model.code.PS0004;
import com.skhu.model.code.PS0005;
import com.skhu.util.DateUtils;
import com.skhu.util.JacksonUtils;

@Service("psService")
public class PSService {
	@Autowired
	GCMMapper gcmMapper;
	@Autowired
	AlarmMapper alarmMapper;
	
	public APICode resPS0001(APICode reqCode){
		HashMap<String, String> hashMap = (HashMap<String, String>) reqCode.tranData;
		PS0001 ps = JacksonUtils.<PS0001>hashMapToObject(hashMap, PS0001.class);
		
		Gcm gcm = new Gcm();
		gcm.mac = ps.mac;
		gcm.pushYn = ps.pushYn;
		gcm.tokenId = ps.pushTokenId;
		
		try {
			ps.resultYn = (1 == gcmMapper.insertGcm(gcm))? "Y" : "N";
		} catch(Exception e){
			ps.resultYn = "N";
		}
		APICode resCode = this.<PS0001>processCommonResponse(reqCode, ps);
		return resCode;
	}
	
	public APICode resPS0002(APICode reqCode) {
		HashMap<String, String> hashMap = (HashMap<String, String>) reqCode.tranData;
		PS0002 ps = JacksonUtils.<PS0002>hashMapToObject(hashMap, PS0002.class);
		
		String mac = ps.mac;
		
		Gcm gcm = gcmMapper.getGcm(mac);
		ps.pushTokenId = gcm.tokenId;
		ps.pushYn = gcm.pushYn;
		
		APICode resCode = this.<PS0002>processCommonResponse(reqCode, ps);
		return resCode;
	}

	public APICode resPS0003(APICode reqCode) {
		HashMap<String, String> hashMap = (HashMap<String, String>) reqCode.tranData;
		PS0003 ps = JacksonUtils.<PS0003>hashMapToObject(hashMap, PS0003.class);
		
		String mac = ps.mac;
		List<Alarm> alarms = alarmMapper.getAlarms(mac);
		
		ArrayList<PS0003Alarm> psAlarms = new ArrayList<PS0003.PS0003Alarm>();
		
		for(int i=0; alarms != null && i < alarms.size(); i++){
			PS0003Alarm psAlarm = new PS0003Alarm();
			psAlarm.alarmNo = alarms.get(i).alarmNo;
			psAlarm.cateNo = alarms.get(i).cateNo;
			psAlarm.cateNm = alarms.get(i).cateNm;
			psAlarm.filter = alarms.get(i).filter;
			psAlarm.srcNo = alarms.get(i).srcNo;
			
			psAlarms.add(psAlarm);
		}
		
		ps.resCnt = psAlarms.size();
		ps.res = psAlarms;
		ps.resDate = DateUtils.currentTime(DateUtils.dateForm1);
		
		APICode resCode = this.<PS0003>processCommonResponse(reqCode, ps);
		return resCode;
	}

	public APICode resPS0004(APICode reqCode) {
		HashMap<String, String> hashMap = (HashMap<String, String>) reqCode.tranData;
		PS0004 ps = JacksonUtils.<PS0004>hashMapToObject(hashMap, PS0004.class);
		
		Alarm alarm = new Alarm();
		alarm.mac = ps.mac;
		alarm.cateNo = ps.cateNo;
		alarm.srcNo = ps.srcNo;
		try {
			alarm.filter = URLDecoder.decode(ps.filter, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		Alarm curAlarm = null;
		
		curAlarm = alarmMapper.getAlarm(alarm.mac, alarm.srcNo, alarm.cateNo, alarm.filter);
		
		if(curAlarm != null)
			ps.resultYn = "이미 등록되었습니다.";
		else
			ps.resultYn = (1 == alarmMapper.insertAlarm(alarm))? "Y" : "N";
		APICode resCode = this.<PS0004>processCommonResponse(reqCode, ps);
		return resCode;
	}

	public APICode resPS0005(APICode reqCode) {
		HashMap<String, String> hashMap = (HashMap<String, String>) reqCode.tranData;
		PS0005 ps = JacksonUtils.<PS0005>hashMapToObject(hashMap, PS0005.class);

		long no = ps.alarmNo;
		ps.resultYn = (1 == alarmMapper.deleteAlarm(no))? "Y" : "N";
		
		APICode resCode = this.<PS0005>processCommonResponse(reqCode, ps);
		return resCode;
	}
	
	public <T> APICode<T> processCommonResponse(APICode reqCode, T ps){
		// post Response
		APICode<T> resCode = new APICode<T>();
		resCode.tranCd = reqCode.tranCd;
		resCode.tranData = ps;
		return resCode;
	}
}

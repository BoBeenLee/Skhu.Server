package com.skhu.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.skhu.mapper.AlarmMapper;
import com.skhu.mapper.GCMMapper;
import com.skhu.model.Alarm;

/* PROJECT ID = spry-district-693
 * PROJECT NUMBER = 191825913558 191825913558
 * API KEY = AIzaSyBUIZrcluzPp_N0_6QrOSRRUGf_hUsTdkM
 * 
 */

@Service("gcmService")
public class GCMService {
	public void sendMessage(String[] tokenIds, String cateNM, String subject, String url, int dbType) {
		Sender sender = new Sender("AIzaSyDtc1Lroot8QtYdgBA3yTfqiYRPdlQJxeY");
//		String regId = "APA91bFPO7UzexoyF6a4Vo00RNtlWo0AcjKR9RC_rgjXAgq4K04XOzDXYsal5XrmEawh2wfrJZhi1-CBnmEyuPvFvfleQOmedBbtXdJ6uUsukGmW5Ip9fVpBAmG4ism8O0iJ99YNVDb5M_INKREUTHwxazeGV2TYHOLsWjR_FiQKMLfnnyb4gPI";

		Message message = new Message.Builder().addData("cateNM", cateNM).addData("subject", subject).addData("url", url).addData("dbType", "" + dbType)
				.build();
		MulticastResult multiResult = null;
		System.out.println("tokenId : " + tokenIds[0]);
		try {
			multiResult = sender.send(message, Arrays.asList(tokenIds), 5);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (multiResult != null) {
			List<Result> resultList = multiResult.getResults();
			for (Result result : resultList) {
				System.out.println(result.getMessageId());
			}
		}
	}
}
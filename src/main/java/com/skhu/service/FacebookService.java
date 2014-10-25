package com.skhu.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.skhu.util.DateUtils;
import com.skhu.util.NetUtils;

@Service("facebookService")
public class FacebookService {
	public static final String FACEBOOK_URL = "https://graph.facebook.com/v2.1";
	public static final String FACEBOOK_ORIGIN_URL = "https://www.facebook.com";
	public static final String token;

	// 토큰이 공공이라 접근 안되는 페이지도 있음.
	static {
		String tokenUri = "https://graph.facebook.com/oauth/access_token?client_id=154374664667119&&client_secret=2b740792e12c32e9350524395d4a0a06&grant_type=client_credentials";
		String json = null;
		try {
			json = NetUtils.fetch(new URL(tokenUri));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		token = json.split("=")[1];
	}
	
	// 138148036 - 1000 ( 시작날짜 뒤 3개 빼야함 )
	public String graphToJson(String id, String no, int pgNum) {
		String graphUri = FACEBOOK_URL + "/" + id + "/posts?access_token="
				+ token + "&limit=" + pgNum  + "&fields=id,name,picture,message,link,updated_time,from&until=" + no;
//		System.out.println(graphUri);
		return graphToJson(graphUri);
	}
	
	// 138148036 - 1000 ( 시작날짜 뒤 3개 빼야함 )
	public String graphToJson(String graphUri) {
//		System.out.println(graphUri);
		String json = null;
		try {
			json = NetUtils.fetch(new URL(graphUri));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
}

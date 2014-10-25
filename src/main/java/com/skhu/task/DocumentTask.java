package com.skhu.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DocumentTask {
	public static final String URL = "http://localhost:8080/skhu_server/"; // "http://104.131.124.225:8080/skhu_server/";
	private static final String USER_AGENT = "Mozilla/5.0";

	// http://104.131.124.225:8080/skhu_server/update/skhu.app ; // "http://localhost:8080/skhu_server/"; // 
	// http://localhost:8080/skhu_server/skhubrd/update/skhu.app
	// http://104.131.124.225:8080/skhu_server/skhubrd/update/skhu.app
	public void updateSkhuBrd() {
		try {
			sendGet(URL + "skhubrd/update/skhu.app");
			sendGet(URL + "skhubrd/update/qna.app");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// HTTP GET request
	private void sendGet(String url) throws Exception {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		// System.out.println("\nSending 'GET' request to URL : " + url);
		// System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		con.disconnect();
		// print result
		// System.out.println(response.toString());
	}
}

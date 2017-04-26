package com.guods.xhy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSON;

public class Client {

	CloseableHttpClient client = HttpClients.createDefault();
	
	public Client() {
		super();
	}
	
	public Result loginPost(String url, String account, String password) throws ClientProtocolException, IOException {
		HttpPost httpPost = new HttpPost(url);
		String cookies = "";
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("account",account));
		formparams.add(new BasicNameValuePair("password",password));
		UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
		httpPost.setEntity(urlEncodedFormEntity);
		CloseableHttpResponse response = client.execute(httpPost);
		Header[] headers = response.getHeaders("Set-Cookie");
		for (int i = 0; i < headers.length; i++) {
			String cookie = headers[i].getValue().substring(0, headers[i].getValue().indexOf(";") + 1);
			cookies = cookies + cookie + " ";
		}
		String resEntity = EntityUtils.toString(response.getEntity()).toLowerCase();
		Result result = JSON.parseObject(resEntity, Result.class);
		if (result.getSuccess()) {
			result.setCookies(cookies);
			result.setMessage("登录成功！");
		}
		return result;
	}

	public String updatePost(String url, String cookies, String name, String mathType, String textData) throws UnsupportedEncodingException{
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36");
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
		httpPost.addHeader("Accept-Encoding", "gzip, deflate");
		httpPost.addHeader("Cookie", cookies);
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("PrePage", "http://www.yunhuiyuan.cn/Manage/WeChat/SetReplyKeyWord"));
		formparams.add(new BasicNameValuePair("objectGuid", null));
		formparams.add(new BasicNameValuePair("replyType", "1"));
		
		formparams.add(new BasicNameValuePair("name", name));
		formparams.add(new BasicNameValuePair("mathType", mathType)); //1、完整匹配，2、模糊匹配
		formparams.add(new BasicNameValuePair("textData", textData));
		UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
		httpPost.setEntity(urlEncodedFormEntity);
        try {  
            CloseableHttpResponse response = client.execute(httpPost); 
            HttpEntity entity = response.getEntity();  
            if (entity != null) {  
                String result = EntityUtils.toString(entity, "UTF-8");  
                System.out.println(result);
                response.close();  
                return result;  
            } 
        } catch (ClientProtocolException e) { 
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        } 
        return null; 
	}
	
//	public Document dataGet(String url, String cookies){
//		
//		Map<String, String> cookiesMap = new HashMap<String, String>();
//		String[] cookieArray = cookies.split(";");
//		for (String cookie : cookieArray) {
//			String[] cookiekv = cookie.split("=");
//			try {
//				cookiesMap.put(cookiekv[0].trim(), cookiekv[1].trim());
//			} catch (Exception e) {
//			}
//		}
//		try {
//			Document document = Jsoup.connect(url).cookies(cookiesMap).get();
//			return document;
//		} catch (IOException e1) {
//			return null;
//		}
//	}
}

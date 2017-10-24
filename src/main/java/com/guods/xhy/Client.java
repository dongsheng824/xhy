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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
	
	public Result loginPost2(String url, String account, String password) throws ClientProtocolException, IOException {
		HttpPost httpPost = new HttpPost(url);
		String cookies = "";
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("fT08d9e827ffbba2efe4413cb064bbf847un",account));
		formparams.add(new BasicNameValuePair("fT08d9e827ffbba2efe4413cb064bbf847pwd",password));
		UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
		httpPost.setEntity(urlEncodedFormEntity);
		CloseableHttpResponse response = client.execute(httpPost);
		Header[] headers = response.getHeaders("Set-Cookie");
		for (int i = 0; i < headers.length; i++) {
			String cookie = headers[i].getValue().substring(0, headers[i].getValue().indexOf(";") + 1);
			cookies = cookies + cookie + " ";
		}
		String resEntity = EntityUtils.toString(response.getEntity());
		Result result = new Result();
		result.setCookies(cookies);
		System.out.println(resEntity);
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
	
	public String updatePost2(String url, String cookies, String type, String kw, String content, String pptype) throws UnsupportedEncodingException{
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Accept","text/plain, */*; q=0.01");
		httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36");
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
		httpPost.addHeader("Accept-Encoding", "gzip, deflate");
		httpPost.addHeader("Cookie", cookies);
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("type", type));
		formparams.add(new BasicNameValuePair("kw", kw)); 
		formparams.add(new BasicNameValuePair("content", content));
		formparams.add(new BasicNameValuePair("pptyp", pptype));
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
	
	public List<String[]> getContent(String url, String cookies){
		List<String[]> rowDataList = new ArrayList<String[]>();
		String id;
		try {
			Document document = Jsoup.connect(url).header("Cookie", cookies).get();
			if (document == null) {
				return rowDataList;
			}
			Elements tbodys = document.getElementsByTag("tbody");
			if (tbodys == null || tbodys.size() == 0) {
				return rowDataList;
			}
			Elements trs = tbodys.get(0).getElementsByTag("tr");
			for (Element tr : trs) {
				String[] rowData = new String[5];
				Elements tds = tr.getElementsByTag("td");
				id = tds.get(0).getElementsByTag("input").get(0).attr("value");
				rowData[0] = id;
				rowData[1] = "0";
				rowData[2] = tds.get(1).text();
				rowData[3] = tds.get(2).text();
				if (tds.get(3).text().equals("模糊匹配")) {
					rowData[4] = "1";
				}
				rowDataList.add(rowData);
			}
			return rowDataList;
		} catch (IOException e) {
			return null;
		}
	}
}

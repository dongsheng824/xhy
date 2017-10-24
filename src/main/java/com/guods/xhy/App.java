package com.guods.xhy;

import java.io.IOException;

import javax.swing.SwingUtilities;

import org.apache.http.client.ClientProtocolException;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;

public class App 
{
	
	public static void main( String[] args ) throws ClientProtocolException, IOException
    {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainView2();
			}
		});
		
//		String url = "http://www.weixinyunduan.com/admin/baseService/keyword_1.html";
//		String cookies = "ip=115.193.215.234%2C%20220.170.185.662017/10/24rqxhy; __cfduid=d1373a2d489af65379a433ff09dcaab8e1508721371; FENBOT_fingerprint=1508721358458; Hm_lvt_117891647dedd0fc47d164808222f820=1508726517,1508727074,1508727534; PHPSESSID=mf21avq6k79g0r2pgn3210hok1; WXuser=rqxhy; uid=459009; uuid=fb2e13ec05240b8c995175e4639838ac; yunsuo_session_verify=0bfcba3456eabc3dd90ef4648cd06c7b";
//		Client client = new Client();
//		client.getContent(url, cookies);
		
    }
    
//	private static void parsePate(Document document, Excel excel){
//		Elements trs = document.getElementsByClass("alt-row");
//		for (Element tr : trs) {
//			Elements names = tr.getElementsByTag("td");
//			Elements as = tr.getElementsByTag("a");
//			String[] rowData = new String[2];
//			rowData[0] = names.get(0).text();
//			rowData[1] = as.get(0).attr("href");
//			excel.insertRow(rowData);
//		}
//	}
}

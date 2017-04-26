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
				new MainView();
			}
		});
//		String[] columnNames = {"name1", "name2"};
//		Excel excel = new Excel("D:\\updatedata\\", "result.xlsx", "sheet1", columnNames);
//		Client client = new Client();
//		Result loginPost = client.loginPost("http://www.yunhuiyuan.cn/", "dcwlkj", "dfcf182838");
//		Document document = client.dataGet("http://www.yunhuiyuan.cn/Manage/WeChat/SetReplyKeyWord?pageIndex=2", loginPost.getCookies());
//		System.out.println(document);
//		parsePate(document, excel);
//		excel.saveFile();
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

package com.guods.xhy;

import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainView2 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField filePath, fileName;
	private JButton updateButton, exportButton;
	private JTextArea outPut, cookies;
	private JScrollPane scroll, cookieScroll;
	private Client client;
	private Result loginResult;
	
	private static final String LOGIN_URL = "http://www.weixinyunduan.com/login.html?uid=459009&upwd=34294a04d74b311d42006b664f1c6606";
	public static final String UPDATE_URL_PRE = "http://www.weixinyunduan.com/admin/baseService/ajax2-";
	public static final String URL_HTML = ".html";
	public static final String GET_URL_PRE = "http://www.weixinyunduan.com/admin/baseService/keyword_";
	public MainView2() throws HeadlessException {
		super("批量数据更新");
		initElements();
		initLayout();
		//
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 400);
		setResizable(false);
		setVisible(true);
		client = new Client();
	}

	private void initElements() {
		cookies = new JTextArea(5, 20);
		cookies.setLineWrap(true);
		cookieScroll = new JScrollPane(cookies);
		filePath = new JTextField(20);
		filePath.setText("d:\\updatedata\\");
		filePath.setEditable(false);
		fileName = new JTextField(20);
		fileName.setText("content2.xlsx");
		updateButton = new JButton("更新数据");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					public void run() {
						outPut.setText(outPut.getText() + System.lineSeparator() + "开始更新数据");
						outPut.paintImmediately(getBounds());
						updateData(cookies.getText());
					}
				}).start();
			}
		});
		
		exportButton = new JButton("导出数据");
		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					public void run() {
						outPut.setText(outPut.getText() + System.lineSeparator() + "开始导出数据");
						outPut.paintImmediately(getBounds());
						exportData(cookies.getText());
					}
				}).start();
			}
		});
		outPut = new JTextArea(10, 30);
		outPut.setEditable(true);
		scroll = new JScrollPane(outPut);
	}

	
	private void initLayout() {
		Box labelBox, textBox, outPutBox, box, buttonBox;
		setLayout(new FlowLayout());
		Box boxContainer;
		boxContainer = Box.createVerticalBox();
		//添加58
		box = Box.createHorizontalBox();
		labelBox = Box.createVerticalBox();
		labelBox.add(new JLabel("cookies："));
		labelBox.add(Box.createVerticalStrut(30));
		labelBox.add(new JLabel("数据文件夹："));
		labelBox.add(Box.createVerticalStrut(30));
		labelBox.add(new JLabel("数据文件名："));
		textBox = Box.createVerticalBox();
		textBox.add(cookieScroll);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(filePath);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(fileName);
		textBox.add(Box.createVerticalStrut(8));

		buttonBox = Box.createHorizontalBox();
		buttonBox.add(exportButton);
		buttonBox.add(Box.createHorizontalStrut(20));
		buttonBox.add(updateButton);
		
		outPutBox = Box.createVerticalBox();
		outPutBox.add(buttonBox);
		outPutBox.add(Box.createVerticalStrut(8));
		outPutBox.add(scroll);
		box.add(labelBox);
		box.add(Box.createHorizontalStrut(8));
		box.add(textBox);
		box.add(Box.createHorizontalStrut(8));
		box.add(outPutBox);
		//添加栏目
		boxContainer.add(box);
		//
		add(boxContainer);
	}
	
	private void updateData(String cookies){
		if (cookies == null || "".equals(cookies)) {
			outPut.setText(outPut.getText() + System.lineSeparator() + "cookie不能为空!");
			outPut.paintImmediately(getBounds());
			return;
		}
		Excel excel = new Excel(filePath.getText(), fileName.getText());
		for (int i = 1; i < excel.size() + 1; i++) {
			String updateResult;
			try {
				if (excel.readUnit(i, 0) == null || "".equals(excel.readUnit(i, 0))) {
					break;
				}
				updateResult = client.updatePost2(UPDATE_URL_PRE + excel.readUnit(i, 0) + URL_HTML, 
						cookies, excel.readUnit(i, 1), excel.readUnit(i, 2), excel.readUnit(i, 3), excel.readUnit(i, 4));
				if (updateResult == null || !updateResult.equals("1")) {
					outPut.setText(outPut.getText() + System.lineSeparator() + excel.readUnit(i, 2) + ":fail");
					outPut.paintImmediately(getBounds());
				}else {
					outPut.setText(outPut.getText() + System.lineSeparator() + excel.readUnit(i, 2) + ":success");
					outPut.paintImmediately(getBounds());
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		outPut.setText(outPut.getText() + System.lineSeparator() + "更新完成！");
		outPut.paintImmediately(getBounds());
	}
	
	private void exportData(String cookies){
		String url;
		List<String[]> contentList;
		if (cookies == null || "".equals(cookies)) {
			outPut.setText(outPut.getText() + System.lineSeparator() + "cookie不能为空!");
			outPut.paintImmediately(getBounds());
			return;
		}
		String[] columnNames = {"id", "type", "kw", "content", "pptyp"};
		Excel excel = new Excel(filePath.getText(), fileName.getText(), "sheet1", columnNames);
		for (int i = 1; i < 500; i++) {
			url = GET_URL_PRE + i + URL_HTML;
			contentList = client.getContent(url, cookies);
			if (contentList == null || contentList.size() == 0) {
				break;
			}
			for (String[] rowData : contentList) {
				excel.insertRow(rowData);
			}
			outPut.setText(outPut.getText() + System.lineSeparator() + "第" + i + "页导出完成");
			outPut.paintImmediately(getBounds());
		}
		String saveResult = excel.saveFile();
		outPut.setText(outPut.getText() + System.lineSeparator() + saveResult);
		outPut.setText(outPut.getText() + System.lineSeparator() + "导出完成。如果文档为空请重填cookie再导!");
		outPut.paintImmediately(getBounds());
	}
}

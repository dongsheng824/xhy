package com.guods.xhy;

import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.http.client.ClientProtocolException;

public class MainView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField account, filePath, fileName;
	private JPasswordField password;
	private JButton loginButton, updateButton;
	private JTextArea outPut;
	private JScrollPane scroll;
	private Client client;
	private Result loginResult;
	
	private static final String LOGIN_URL = "http://www.yunhuiyuan.cn/";
	public static final String UPDATE_URL = "http://www.yunhuiyuan.cn/Manage/WeChat/EditKeyWord/";
	
	public MainView() throws HeadlessException {
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
		account = new JTextField(20);
		password = new JPasswordField(20);
		filePath = new JTextField(20);
		filePath.setText("d:\\updatedata\\");
		filePath.setEditable(false);
		fileName = new JTextField(20);
		fileName.setText("content.xlsx");
		updateButton = new JButton("更新数据");
		updateButton.setEnabled(false);
		loginButton = new JButton("登录系统");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					public void run() {
						loginButton.setText("登录中...");
						loginButton.setEnabled(false);
						loginButton.paintImmediately(getBounds());
						outPut.setText("登录中...");
						outPut.paintImmediately(getBounds());
						
						loginResult = login();
						outPut.setText(outPut.getText() + System.lineSeparator() + loginResult.getMessage());
						outPut.paintImmediately(getBounds());
						if (loginResult.getSuccess()) {
							updateButton.setEnabled(true);
						}
						loginButton.setText("重新登录");
						loginButton.setEnabled(true);
						loginButton.paintImmediately(getBounds());
					}
				}).start();
			}
		});
		
		
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					
					public void run() {
						loginButton.setText("更新中...");
						loginButton.setEnabled(false);
						loginButton.paintImmediately(getBounds());
						outPut.setText(outPut.getText() + System.lineSeparator() + "开始更新数据");
						outPut.paintImmediately(getBounds());
						updateData();
						loginButton.setText("重新更新");
						loginButton.setEnabled(true);
						loginButton.paintImmediately(getBounds());
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
		labelBox.add(new JLabel("用户名："));
		labelBox.add(Box.createVerticalStrut(30));
		labelBox.add(new JLabel("密码："));
		labelBox.add(Box.createVerticalStrut(30));
		labelBox.add(new JLabel("数据文件夹："));
		labelBox.add(Box.createVerticalStrut(30));
		labelBox.add(new JLabel("数据文件名："));
		textBox = Box.createVerticalBox();
		textBox.add(account);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(password);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(filePath);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(fileName);
		textBox.add(Box.createVerticalStrut(8));

		buttonBox = Box.createHorizontalBox();
		buttonBox.add(loginButton);
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

	private Result login(){
		Result loginResult = null;
		try {
			loginResult = client.loginPost(LOGIN_URL, account.getText(), password.getText());
		} catch (ClientProtocolException e) {
			return new Result(false, "登录失败！");
		} catch (IOException e) {
			return new Result(false, "登录失败！");
		}
		return loginResult;
	}
	
	private void updateData(){
		if (loginResult == null || !loginResult.getSuccess()) {
			outPut.setText(outPut.getText() + System.lineSeparator() + "未登录或登录失败，请先登录再更新数据");
			outPut.paintImmediately(getBounds());
		}
		if (loginResult.getSuccess()) {
    		Excel excel = new Excel(filePath.getText(), fileName.getText());
    		for (int i = 1; i < excel.size() + 1; i++) {
    			String updateResult;
				try {
					updateResult = client.updatePost(UPDATE_URL + excel.readUnit(i, 0), loginResult.getCookies(), excel.readUnit(i, 1), excel.readUnit(i, 2), excel.readUnit(i, 3));
					if (updateResult == null || !updateResult.contains("Object moved")) {
						outPut.setText(outPut.getText() + System.lineSeparator() + excel.readUnit(i, 1) + ":fail");
						outPut.paintImmediately(getBounds());
					}else {
						outPut.setText(outPut.getText() + System.lineSeparator() + excel.readUnit(i, 1) + ":success");
						outPut.paintImmediately(getBounds());
					}
					System.out.println(updateResult);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
    		}
		}
	}
}

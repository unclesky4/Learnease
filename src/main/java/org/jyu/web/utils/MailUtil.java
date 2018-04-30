package org.jyu.web.utils;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {
	
	//
	static final String mailValidateAddress = "localhost";
	
	public static String MailValidateAddress() {
		return mailValidateAddress;
	}

	/**
	 * 发送邮件
	 * @param to    收件人邮箱
	 * @param code   激活码
	 */
	public static void sendMail(String to, String code){
		//1.创建连接对象，连接到邮箱服务器
		Properties properties = new Properties();
		// 开启debug调试  
		properties.setProperty("mail.debug", "true");
		// 发送服务器需要身份验证  
		properties.setProperty("mail.smtp.auth", "true");  
		// 设置邮件服务器主机名  
		properties.setProperty("mail.host", "smtp.126.com");
		// 发送邮件协议名称  
		properties.setProperty("mail.transport.protocol", "smtp");
		Session session = Session.getInstance(properties);
		
		try {
			//2.创建邮件对象
			Message message = new MimeMessage(session);
			//发件人
			message.setFrom(new InternetAddress("huang_zhi_biao@126.com"));
			//主题
			message.setSubject("激活邮件");
			//正文
			message.setContent("<h1>请点击激活链接：<h2><a href='http://"+mailValidateAddress+":8080/validate_html?code="+code+
					"'>http://localhost:8080/validate_html?code="+code+"<a><h2><h1>","text/html;charset=UTF-8");
			//3.发送邮件
			Transport transport = session.getTransport();
			transport.connect("huang_zhi_biao","jyu601");
			//设置收件人并发送邮件
			transport.sendMessage(message, new Address[] {new InternetAddress(to)});
			transport.close();
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送重置密码邮箱
	 * @param email
	 * @param code
	 */
	public static void sendResetPwdEmail(String email, String code){
		//1.创建连接对象，连接到邮箱服务器
		Properties properties = new Properties();
		// 开启debug调试  
		properties.setProperty("mail.debug", "true"); 
		// 发送服务器需要身份验证  
		properties.setProperty("mail.smtp.auth", "true");  
		// 设置邮件服务器主机名  
		properties.setProperty("mail.host", "smtp.126.com");
		// 发送邮件协议名称  
		properties.setProperty("mail.transport.protocol", "smtp");
		Session session = Session.getInstance(properties);
		
		try {
			//2.创建邮件对象
			Message message = new MimeMessage(session);
			//发件人
			message.setFrom(new InternetAddress("huang_zhi_biao@126.com"));
			//主题
			message.setSubject("重置密码");
			//正文
			message.setContent("<h1>请点击重置密码链接：<h2><a href='http://"+mailValidateAddress+":8080/reset_pwd_html?code="+code+
					"'>http://localhost:8080/reset_pwd_html?email="+email+"&code="+code+"<a><h2><h1>","text/html;charset=UTF-8");
			//3.发送邮件
			Transport transport = session.getTransport();
			transport.connect("huang_zhi_biao","jyu601");
			//设置收件人并发送邮件
			transport.sendMessage(message, new Address[] {new InternetAddress(email)});
			transport.close();
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}

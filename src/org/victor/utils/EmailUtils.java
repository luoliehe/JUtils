package org.victor.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

/**
 * 邮件发送工具
 * @author victor.luo
 */
public class EmailUtils {

	/** 邮箱host */
	private final static String HOST_NAME = "smtp.163.com";

	/** 邮箱账号 */
	private final static String USER_NAME = "test@163.com";

	/** 邮箱密码 */
	private final static String PASSWORD = "pw";

	private final static String UTF8 = "UTF-8";

	/**
	 * 发送文本邮件
	 * 
	 * @param subject 主题
	 * @param context 文本内容
	 * @param emails 邮箱列表
	 * @throws EmailException
	 */
	public static void sendText(String subject, String context, String... emails) throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName(HOST_NAME);
		email.setSmtpPort(465);
		email.setAuthentication(USER_NAME, PASSWORD);
		email.setSSLOnConnect(true);
		email.setFrom(USER_NAME, USER_NAME);
		email.setSubject(subject);
		email.setMsg(context);
		for (String to : emails) {
			email.addTo(to);
		}
		email.send();
	}

	/**
	 * 发送html文本邮件
	 * 
	 * @param subject 主题
	 * @param html html文本
	 * @param emails 邮箱列表
	 * @throws EmailException
	 */
	public static void sendHtml(String subject, String html, String... emails) throws EmailException {
		HtmlEmail email = new HtmlEmail();
		email.setCharset(UTF8);
		email.setHostName(HOST_NAME);
		for (String to : emails) {
			email.addTo(to, to);
		}
		email.setFrom(USER_NAME, USER_NAME);
		email.setSubject(subject);
		email.setAuthentication(USER_NAME, PASSWORD);
		email.setMsg("this is message.");
		email.setHtmlMsg(html);
		email.setTextMsg("Your email client does not support HTML messages.");
		email.send();
	}

	public static void main(String[] args) throws EmailException, IOException {
		sendText("text主题", "文本内容", "1522959562@qq.com", "luoliehe@pconline.com.cn");
		sendHtml("html主题", FileUtils.readFileToString(new File("test/a.html")), "1522959562@qq.com", "luoliehe@pconline.com.cn");
		System.out.println("ok");
	}
}

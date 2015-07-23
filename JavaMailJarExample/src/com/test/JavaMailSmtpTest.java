package com.test;

import com.mail.entity.MailInfo;
import com.mail.sender.MailSender;



public class JavaMailSmtpTest {

	public static void main(String[] args) {
		MailSender ms = new MailSender();
		MailInfo mi = new MailInfo();
		mi.setUsername("2223108045@qq.com");
		mi.setPassword("xxxxxx"); 
		mi.setFrom("2223108045@qq.com");
		String[] to = {"2223108045@qq.com"};
		mi.setTo(to);
		mi.setContent("it's hard");
		try {
			ms.sendMail(mi);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

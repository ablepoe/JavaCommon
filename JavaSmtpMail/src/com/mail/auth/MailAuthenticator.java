package com.mail.auth;

import javax.mail.PasswordAuthentication;

public class MailAuthenticator extends javax.mail.Authenticator{

	private String userName;
	private String password;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public MailAuthenticator(){
		
	}
	public MailAuthenticator(String username,String password){
		this.userName = username;
		this.password = password;
	}
	public PasswordAuthentication getPasswordAuthentication(){
		return new PasswordAuthentication(userName, password);
	}
 
}

package com.mail.entity;

import java.util.Date;

public class MailInfo {
	
	private String encoding;
	private String host = "smtp.qq.com";
	private String port = "25";
	private String auth = "true";
	private String from;
	private String[] to;
	private String[] cc;
	private String[] bcc;
	private String username;
	private String password;
	private String subject;
	private String content;
	private String contentType = "text/html;charset=utf-8";
	private String[] imageFilePath;
	private String[] cid;
	private Date date = new Date();
	private String[] attatchFilePath;
	
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String[] getTo() {
		return to;
	}
	public void setTo(String[] to) {
		this.to = to;
	}
	public String[] getCc() {
		return cc;
	}
	public void setCc(String[] cc) {
		this.cc = cc;
	}
	public String[] getBcc() {
		return bcc;
	}
	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String[] getImageFilePath() {
		return imageFilePath;
	}
	public void setImageFilePath(String[] imageFilePath) {
		this.imageFilePath = imageFilePath;
	}
	public String[] getCid() {
		return cid;
	}
	public void setCid(String[] cid) {
		this.cid = cid;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String[] getAttatchFilePath() {
		return attatchFilePath;
	}
	public void setAttatchFilePath(String[] attatchFilePath) {
		this.attatchFilePath = attatchFilePath;
	}
	
}

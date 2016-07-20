package com.HttpsUrlRequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.SocketFactory;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;

import com.alibaba.fastjson.JSON;

import sun.applet.Main;

public class HttpSecureProtocolSocketFactory implements ProtocolSocketFactory{

	private SSLContext sslContext;
	
	private SSLContext getSSLContext(){
		if(null == this.sslContext){
			this.sslContext = createSSLContext();
		}
		return sslContext;
	}
	
	private SSLContext createSSLContext(){
		SSLContext sslctx = null;
		try {
			sslctx = SSLContext.getInstance("SSL");
			KeyManager[] km = null;
			TrustManager[] tm = new TrustManager[] { new TrustAnyManager() };
			sslctx.init(km, tm, new java.security.SecureRandom());	
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
		return sslctx;
	}
	
	private static class TrustAnyManager implements X509TrustManager{
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			
		}
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			
		}
		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}
	
	public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
		return getSSLContext().getSocketFactory().createSocket(host, port);
	}

	public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort)
			throws IOException, UnknownHostException {
		return getSSLContext().getSocketFactory().createSocket(host, clientPort, clientHost, clientPort);
	}

	public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort, HttpConnectionParams params)
			throws IOException, UnknownHostException, ConnectTimeoutException {
		if(null == params){
			throw new IllegalArgumentException();
		}
		int timeout = params.getConnectionTimeout();
		SocketFactory socketFactory = getSSLContext().getSocketFactory();
		if(timeout == 0){
			return socketFactory.createSocket(host, port, clientHost, clientPort);
		}else{
			Socket socket = socketFactory.createSocket(host, port);
			SocketAddress localAddr = new InetSocketAddress(clientHost, clientPort);
			SocketAddress remoteAddr = new InetSocketAddress(host, port);
			socket.bind(localAddr);
			socket.connect(remoteAddr, timeout);
			return socket;
		}
	}	

	public static String getStringByHttps(String url, String userName, String password){
		String result = "";
		Protocol https = new Protocol("https",new HttpSecureProtocolSocketFactory(), 443);
		Protocol.registerProtocol("https", https);
		GetMethod get = new GetMethod(url);
		HttpClient client = new HttpClient();
		UsernamePasswordCredentials creds = new UsernamePasswordCredentials(userName, password);
		client.getState().setCredentials(AuthScope.ANY, creds);
		try {
			client.executeMethod(get);
			result = get.getResponseBodyAsString();
			result = new String(result.getBytes("ISO-8859-1"),"UTF-8");
			Protocol.unregisterProtocol("https");
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	public static void main(String[] args) {
		String url = "https://ilig-app-con.idt1.shanghaionstar.com:52202/LIG/GetCarrierUsage/JSON?Expression=GetCarrierUsageByVIN&VIN=LSGAR5AL2G096033W";
		String userName = "osbadmin";
		String password = "DFQcW5Ok";
		String s = HttpSecureProtocolSocketFactory.getStringByHttps(url, userName, password);
		System.out.println(JSON.toJSONString(s));
	}
}
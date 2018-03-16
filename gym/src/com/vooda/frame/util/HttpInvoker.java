package com.vooda.frame.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class HttpInvoker {

	/**
	 * 该url里面已经携带了请求参数
	 * 
	 * @param url
	 * @throws IOException
	 * @Description:
	 */
	public static String sendGetRequest(String url) {
		String lines = "";
		try {
			// 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
			/*String getURL = url + "?username="
					+ URLEncoder.encode("fata", "utf-8");*/
			URL getUrl = new URL(url);
			// 根据拼凑的URL，打开连接，URL.openConnection()函数会根据
			// URL的类型，返回不同的URLConnection子类的对象，在这里我们的URL是一个http，因此它实际上返回的是HttpURLConnection
			HttpURLConnection connection = (HttpURLConnection) getUrl
					.openConnection();
			// 建立与服务器的连接，并未发送数据
			connection.connect();
			// 发送数据到服务器并使用Reader读取返回的数据
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(),"UTF-8"));
			String temp = "";
			while ((temp = reader.readLine()) != null) {
				lines = lines + temp;
			}
			reader.close();
			// 断开连接
			connection.disconnect();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lines;
	}

	/**
	 * post提交
	 * @param url
	 * @param content
	 * @return
	 */
	public static String sendPostRequest(String url, String content) {
		String line = "";
		try {
//			content =URLEncoder.encode(content, "utf-8"); 
			// Post请求的url，与get不同的是不需要带参数
			URL postUrl = new URL(url);
			// 打开连接
			HttpURLConnection connection = (HttpURLConnection) postUrl
					.openConnection();
			// 打开读写属性，默认均为false
			connection.setDoOutput(true);
			connection.setDoInput(true);
			// 设置请求方式，默认为GET
			connection.setRequestMethod("POST");
			// Post 请求不能使用缓存
			connection.setUseCaches(false);
			//
			
		//	connection.setRequestProperty("Content-type","application/x-www-form-urlencoded");
			
			connection.setRequestProperty("Content-type","text/html");
			
			connection.setInstanceFollowRedirects(true);
//			connection.setRequestProperty(" Content-Type ",
//					" application/x-www-form-urlencoded ");
			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());
			
			out.write(content.toString().getBytes("utf-8"));  
//			out.writeBytes(content);
			out.flush();
			out.close(); // flush and close
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			System.out.println("read from server....");
			String tmp = "";
			while ((tmp = reader.readLine()) != null) {
				line = line + tmp;
			}
			reader.close();
			connection.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return line;
	}
	
	/**
	 * post提交（短信专用）
	 * @param url
	 * @param content
	 * @return
	 */
	public static String sendPostRequestUrlencoded(String url, String content) {
		String line = "";
		try {
//			content =URLEncoder.encode(content, "utf-8"); 
			// Post请求的url，与get不同的是不需要带参数
			URL postUrl = new URL(url);
			// 打开连接
			HttpURLConnection connection = (HttpURLConnection) postUrl
					.openConnection();
			// 打开读写属性，默认均为false
			connection.setDoOutput(true);
			connection.setDoInput(true);
			// 设置请求方式，默认为GET
			connection.setRequestMethod("POST");
			// Post 请求不能使用缓存
			connection.setUseCaches(false);
			//
			
			connection.setRequestProperty("Content-type","application/x-www-form-urlencoded");
			
		//	connection.setRequestProperty("Content-type","text/html");
			
			connection.setInstanceFollowRedirects(true);
//			connection.setRequestProperty(" Content-Type ",
//					" application/x-www-form-urlencoded ");
			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());
			
			out.write(content.toString().getBytes("utf-8"));  
//			out.writeBytes(content);
			out.flush();
			out.close(); // flush and close
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			System.out.println("read from server....");
			String tmp = "";
			while ((tmp = reader.readLine()) != null) {
				line = line + tmp;
			}
			reader.close();
			connection.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return line;
	}
	
	/**
	 * 	作用：使用证书，以post方式提交xml到对应的接口url
	 * second 超时时间
	 */
	public String postXmlSSLCurl(String url, String content,int second){
		String line = "";
		try {
//			content =URLEncoder.encode(content, "utf-8"); 
			// Post请求的url，与get不同的是不需要带参数
			URL postUrl = new URL(url);
			// 打开连接
			HttpURLConnection connection = (HttpURLConnection) postUrl
					.openConnection();
			// 打开读写属性，默认均为false
			connection.setDoOutput(true);
			connection.setDoInput(true);
			// 设置请求方式，默认为GET
			connection.setRequestMethod("POST");
			// Post 请求不能使用缓存
			connection.setUseCaches(false);
			//
			
			connection.setRequestProperty("Content-type","text/html");
			
			connection.setInstanceFollowRedirects(true);
//			connection.setRequestProperty(" Content-Type ",
//					" application/x-www-form-urlencoded ");
			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());
			
			out.write(content.toString().getBytes("utf-8"));  
//			out.writeBytes(content);
			out.flush();
			out.close(); // flush and close
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			System.out.println("read from server....");
			String tmp = "";
			while ((tmp = reader.readLine()) != null) {
				line = line + tmp;
			}
			reader.close();
			connection.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return line;
	}
	

	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		/*String content = "<xml><ToUserName><![CDATA[mallboy]]></ToUserName><FromUserName><![CDATA[weixin001]]></FromUserName><CreateTime>1348831860</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[hello i an weixin001]]></Content><MsgId>1234567890123456</MsgId></xml>";
		sendPostRequest(POST_URL, content);*/
//		String url = "http://114.215.102.67:8080/duduapp?reqtype=historyorderlist&userid=2014052121231572888";
//		System.out.println(sendGetRequest(url));
		
		String url = "http://113.11.210.114:5888/sms.aspx";
		
		String content = URLEncoder.encode("测试短信","utf-8");
		
		String params = "action=send&userid=43&account=jin&password=123456&mobile=13620461204&content="+ content +"&sendTime=&extno=";
		
	//	System.out.println(sendGetRequest(url + params));
		
		System.out.println(sendPostRequest(url, params));
		
	}
}

package com.vooda.frame.umeng;

import com.vooda.frame.umeng.android.AndroidBroadcast;
import com.vooda.frame.umeng.android.AndroidCustomizedcast;
import com.vooda.frame.umeng.ios.IOSBroadcast;
import com.vooda.frame.umeng.ios.IOSCustomizedcast;

/**
 * 友盟推送通知类型
 * @author Administrator
 *
 */
public class PushNotification {
	private static String android_appkey = "56ce755a67e58e2381000e9c";
	private static String android_appMasterSecret = "q0b0szfmtjbtmwq8aosfofcdgzsxixkk";
	private static String ios_appkey = "56ce75de67e58e5ee1000f62";
	private static String ios_appMasterSecret = "sa7vtd8yw1qpqpul3700z1qq8lbb4jo4";
	
	/**
	 * ios单个推送
	 * @param userid
	 * @param content
	 * @throws Exception
	 */
	public static void sendIOSCustomizedcast(String userid, String title, String content, String type) throws Exception {
		IOSCustomizedcast customizedcast = new IOSCustomizedcast();
		customizedcast.setAppMasterSecret(ios_appMasterSecret);
		customizedcast.setPredefinedKeyValue("appkey", ios_appkey);
		customizedcast.setPredefinedKeyValue("timestamp", Integer.toString((int)(System.currentTimeMillis() / 1000)));
	//	System.out.println("this.timestamp:" + this.timestamp);
		// And if you have many alias, you can also upload a file containing these alias, then 
		// use file_id to send customized notification.
		customizedcast.setPredefinedKeyValue("alias", userid);
		customizedcast.setPredefinedKeyValue("alias_type", "ibama");
		customizedcast.setPredefinedKeyValue("alert", content);
		customizedcast.setPredefinedKeyValue("badge", 0);
		customizedcast.setPredefinedKeyValue("sound", "chime");
		customizedcast.setPredefinedKeyValue("production_mode", "true");
		customizedcast.setCustomizedField("type",type);
		//customizedcast.setCustomizedField("num",num);
		customizedcast.send();
	}
	
	/**
	 * android单个推送
	 * @throws Exception
	 */
	public static void sendAndroidCustomizedcast(String userid, String title, String content, String type) throws Exception {
		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast();
		customizedcast.setAppMasterSecret(android_appMasterSecret);
		customizedcast.setPredefinedKeyValue("appkey", android_appkey);
		customizedcast.setPredefinedKeyValue("timestamp", Integer.toString((int)(System.currentTimeMillis() / 1000)));
		// TODO Set your alias here, and use comma to split them if there are multiple alias.
		// And if you have many alias, you can also upload a file containing these alias, then 
		// use file_id to send customized notification.
		customizedcast.setPredefinedKeyValue("alias", userid);
		// TODO Set your alias_type here
		customizedcast.setPredefinedKeyValue("alias_type", "ibama");
		customizedcast.setPredefinedKeyValue("ticker", "爱爸妈");
		customizedcast.setPredefinedKeyValue("title",  title);
		customizedcast.setPredefinedKeyValue("text",   content);
		customizedcast.setPredefinedKeyValue("after_open", "go_activity");
		customizedcast.setPredefinedKeyValue("activity", "HelpActivity");
		customizedcast.setPredefinedKeyValue("display_type", "notification");
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		customizedcast.setPredefinedKeyValue("production_mode", "true");
		customizedcast.setExtraField("activity", type);
		//customizedcast.setExtraField("num", num);
		customizedcast.send();
	}
	
	/**
	 * 安卓广播
	 */
	public static void sendAndroidBroadcast(String title, String msg) throws Exception{	
		AndroidBroadcast broadCast = new AndroidBroadcast();
		broadCast.setAppMasterSecret(android_appMasterSecret);
		broadCast.setPredefinedKeyValue("appkey", android_appkey);
		broadCast.setPredefinedKeyValue("timestamp", Integer.toString((int)(System.currentTimeMillis() / 1000)));
		broadCast.setPredefinedKeyValue("display_type", "notification");
		broadCast.setPredefinedKeyValue("after_open", "go_app");
		broadCast.setPredefinedKeyValue("ticker", "爱爸妈");
		broadCast.setPredefinedKeyValue("title", title);
		broadCast.setPredefinedKeyValue("text", msg);
		broadCast.setPredefinedKeyValue("production_mode", "true");
		broadCast.send();
	}
	
	
	
	/**
	 * IOS广播
	 */
	public static void sendIOSBroadcast(String title, String msg) throws Exception{	
		IOSBroadcast broadCast = new IOSBroadcast();
		broadCast.setAppMasterSecret(ios_appMasterSecret);
		broadCast.setPredefinedKeyValue("appkey", ios_appkey);
		broadCast.setPredefinedKeyValue("timestamp", Integer.toString((int)(System.currentTimeMillis() / 1000)));
		broadCast.setPredefinedKeyValue("type", "broadcast");
		broadCast.setPredefinedKeyValue("alert", msg);
		broadCast.setPredefinedKeyValue("badge", 0);
		broadCast.setPredefinedKeyValue("sound", "chime");
		broadCast.setPredefinedKeyValue("production_mode", "false");
		broadCast.send();
	}
	
	
	
	/**
	 * 推送，android和ios
	 */
	public static void sendPushAndroidAndIos(String userid, String title, String content, String type)throws Exception{
		sendIOSCustomizedcast(userid, title,content, type);
		sendAndroidCustomizedcast(userid, title, content, type);
	}
	
	/**
	 * 发送广播
	 * @throws Exception
	 */
	public static void sendBroadCastNotif(String type, String title, String msg) throws Exception {
		if("1".equals(type)){
			sendAndroidBroadcast(title, msg);
		}else if("2".equals(type)){
			sendIOSBroadcast(title, msg);
		}else{
			sendAndroidBroadcast(title, msg);
			sendIOSBroadcast(title, msg);
		}
	}
	
	public static void main(String[] args) throws Exception {
//		sendAndroidCustomizedcast("15013602667", "通知栏标题", "推送内容", "HelpActivity");
		sendAndroidBroadcast("标题", "内容");
		sendIOSBroadcast("标题", "内容");
	}
}

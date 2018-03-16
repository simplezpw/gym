package com.vooda.frame.wxutil;

/**
 * 微信配置文件信息(微信支付相关)(包含基础参数)

 * @ClassName: WxPayConf_pub

 * @Description: TODO

 * @author: 宋权权

 * @date: 2014年10月16日 下午4:46:51
 */
public class WxPayConf_pub {
	//=======【基本信息设置】=====================================
		//微信公众号身份的唯一标识。审核通过后，在微信发送的邮件中查看
		public static final String APPID = "wxe6879d4bab78c83a";
		//受理商ID，身份标识
		public static final String MCHID = "12345678";
		//商户支付密钥Key。审核通过后，在微信发送的邮件中查看
		public static final String KEY = "xxxxxxxxxxxxxxxxxweixinzhifu";
		//JSAPI接口中获取openid，审核后在公众平台开启开发模式后可查看
		public static final String APPSECRET = "6bf51a5ebffcbdd0f8ef9910e7bb1f78";
		
		public static final String Token = "weixincore";
		
		//=======【JSAPI路径设置】===================================
		//获取access_token过程中的跳转uri，通过跳转将code传入jsapi支付页面
		public static final String JS_API_CALL_URL = "http://xxx.xxx.com/page/dudu_js_api.jsp";
		
		//=======【证书路径设置】=====================================
		//证书路径,注意应该填写绝对路径
		public static final String SSLCERT_PATH = "/weixin_page_java/cacert/apiclient_cert.pem";
		public static final String SSLKEY_PATH = "/weixin_page_java/cacert/apiclient_key.pem";
		
		//=======【异步通知url设置】===================================
		//异步通知url，商户根据实际开发过程设定
		public static final String NOTIFY_URL = "http://xxx.xxx.com/redirectUrl";

		//=======【curl超时设置】===================================
		//本例程通过curl使用HTTP POST方法，此处可修改其超时时间，默认为30秒
		public static final Integer CURL_TIMEOUT = 30;
		
		//编码方式
		public static final String ENCODE = "UTF-8";
}

package com.vooda.frame.wxutil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import org.json.JSONException;
import org.json.JSONObject;

import com.vooda.frame.util.HttpInvoker;


/**
 *  JSAPI支付——H5网页端调起支付接口

 * @ClassName: JsApi_pub

 * @Description: TODO

 * @author: 宋权权

 * @date: 2014年10月17日 下午3:07:30
 */
public class JsApi_pub extends WxPayPubHelper {
	public String code;//code码，用以获取openid
	public String openid;//用户的openid
	public String parameters;//jsapi参数，格式为json
	public String prepay_id;//使用统一支付接口得到的预支付id
	public String curl_timeout;//curl超时时间
	public String access_token;//JS里面的access_token
	public String refresh_token;//用于刷新access_token
	public String nickname;//昵称
	public String headimgurl;//头像地址
	
	/**
	 * 	作用：生成可以获得code的url
	 * redirectUrl 重定向地址
	 */
	public String createOauthUrlForCode(String redirectUrl){
		Map<String,String> arrMap = new HashMap<String,String>();
		arrMap.put("appid", WxPayConf_pub.APPID);
		arrMap.put("redirect_uri", redirectUrl);
		arrMap.put("response_type", "code");
		arrMap.put("scope", "snsapi_base");
		arrMap.put("state", "statedudu");
		String params = formatBizQueryParaMap(arrMap,true,true);
		return "https://open.weixin.qq.com/connect/oauth2/authorize?" + params;
	}

	/**
	 * 	作用：生成可以获得openid的url
	 */
	public String createOauthUrlForOpenid(){
		
		Map<String,String> arrMap = new HashMap<String,String>();
		arrMap.put("appid", WxPayConf_pub.APPID);
		arrMap.put("secret", WxPayConf_pub.APPSECRET);
		arrMap.put("code", code);
		arrMap.put("grant_type", "authorization_code");
		arrMap.put("state", "statedudu");
		String params = formatBizQueryParaMap(arrMap, true, true);
		return "https://api.weixin.qq.com/sns/oauth2/access_token?" + params;
		
	}
	
	
	/**
	 * 	作用：通过curl向微信提交code，以获取openid
	 */
	public String getOpenid(){
		
		String url = createOauthUrlForOpenid();
	    //初始化curl
	    String strJson = HttpInvoker.sendGetRequest(url);
	    System.out.println("openidJson:" + strJson);
		//运行curl，结果以jason形式返回
	    JSONObject obj;
		try {
			obj = new JSONObject(strJson);
			if(obj != null){
				openid = obj.get("openid").toString();
				access_token = obj.get("access_token").toString();
				refresh_token = obj.get("refresh_token").toString();
			}
		} catch (JSONException e) {
			System.out.println("---------------openid_josn_error");
	//		e.printStackTrace();
			return "";
		}
		//取出openid
	   
		return openid;
	}
	
	/**
	 * 获取用户信息
	 * @return
	 */
	public void getUserInfo(){
		String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+ access_token +"&openid="+ openid +"&lang=zh_CN";
		String strJson = HttpInvoker.sendGetRequest(url);
		//运行curl，结果以jason形式返回
	    JSONObject obj;
		try {
			
			System.out.println("getUserInfo_strJson:" + strJson);
			
			obj = new JSONObject(strJson);
			if(obj != null){
				nickname = obj.get("nickname").toString();
				headimgurl = obj.get("headimgurl").toString();
			}
		} catch (JSONException e) {
			System.out.println("---------------getUserInfo_error：" + e.getMessage());
			
		}
	}
	
	/**
	 * 检验授权凭证（access_token）是否有效
	 * @return false 未失效  true 失效
	 */
	public boolean checkAccessToken(){
		String url = "https://api.weixin.qq.com/sns/auth?access_token="+ access_token +"&openid= " + openid;
		String strJson = HttpInvoker.sendGetRequest(url);
		//运行curl，结果以jason形式返回
	    JSONObject obj;
		try {
			System.out.println("checkAccessToken_strJson:" + strJson);
			obj = new JSONObject(strJson);
			if(obj != null){
				String errcode = obj.get("errcode").toString();
				if(errcode.equals("0")){
					return false;//未失效
				}
			}
		} catch (JSONException e) {
			System.out.println("---------------checkAccessToken_error");
		}
		return true;//失效
	}
	
	
	/**
	 * 刷新access_token
	 * 
	 */
	public void resetAccessToken(){
		String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="+ WxPayConf_pub.APPID +"&grant_type=refresh_token&refresh_token=" + refresh_token;
		String strJson = HttpInvoker.sendGetRequest(url);
		//运行curl，结果以jason形式返回
	    JSONObject obj;
		try {
			obj = new JSONObject(strJson);
			if(obj != null){
				openid = obj.get("openid").toString();
				access_token = obj.get("access_token").toString();
				refresh_token = obj.get("refresh_token").toString();
			}
		} catch (JSONException e) {
			System.out.println("---------------resetAccessToken_error");
		}
	}
	

	/**
	 * 	作用：设置prepay_id
	 */
	public void setPrepayId(String prepayId)
	{
		this.prepay_id = prepayId;
	}

	/**
	 * 	作用：设置code
	 */
	public void setCode(String code)
	{
		this.code = code;
	}
	

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	/**
	 * 	作用：设置jsapi的参数
	 */
	public String getParameters()
	{
		Map<String,String> arrMap = new HashMap<String,String>();
		arrMap.put("appId", WxPayConf_pub.APPID);
		long time = new Date().getTime();
		arrMap.put("timeStamp", time + "");
		arrMap.put("nonceStr", createNoncestr(32));
		arrMap.put("package", "prepay_id=" + prepay_id);
		arrMap.put("signType", "MD5");
		arrMap.put("paySign", getSign(arrMap));
		
		net.sf.json.JSONObject jsObj = net.sf.json.JSONObject.fromObject(arrMap);
	//	jsObj = net.sf.json.JSONObject.fromObject(jsObj.toString());
		this.parameters = jsObj.toString();
		
		return parameters;
	}
	
	public static void main(String[] args) {
		System.out.println(new JsApi_pub().createOauthUrlForCode("http://weixin.tonlen.com/beforeCharge?showwxpaytitle=1"));
	}
}

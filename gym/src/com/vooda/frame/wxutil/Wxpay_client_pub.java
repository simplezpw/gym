package com.vooda.frame.wxutil;

import java.util.HashMap;
import java.util.Map;

import com.vooda.frame.util.HttpInvoker;


/**
 * 
 * 请求型接口的基类
 *
 * @ClassName: Wxpay_client_pub

 * @Description: TODO

 * @author: 宋权权

 * @date: 2014年10月17日 下午2:30:26
 */
public class Wxpay_client_pub extends WxPayPubHelper {
	public Map<String,String> parameters = new HashMap<String, String>();//请求参数，类型为关联数组
	public String respStr;//微信返回的响应
	public Map<String,String> resultMap;//返回参数，类型为关联数组
	String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";//接口链接
	Integer curl_timeout;//curl超时时间
	
	/**
	 * 	作用：设置标配的请求参数，生成签名，生成接口参数xml
	 */
	public String createXml(){
		parameters.put("appid", WxPayConf_pub.APPID);//公众账号ID
		parameters.put("mch_id", WxPayConf_pub.MCHID);//商户号
		parameters.put("nonce_str", createNoncestr(32));//随机字符串
		parameters.put("sign", getSign(parameters));//签名
	    return arrayToXml(parameters);
	}
	
	/**
	 * 	作用：post请求xml
	 */
	public String postXml(){
		
	    String xml = createXml();
		respStr = HttpInvoker.sendPostRequest(url, xml);
		System.out.println("xml:" + xml);
		System.out.println("respStr:" + respStr);
		return respStr;
	}
	
	/**
	 * 	作用：获取结果，默认不使用证书
	 */
	public Map<String,String> getResult(){		
		respStr = postXml();
		resultMap = xmltoMap(respStr);
		return resultMap;
	}
}

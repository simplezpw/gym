package com.vooda.frame.wxutil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.vooda.frame.util.MD5Util;
import com.vooda.frame.util.StringUtil;


/**
 * 微信支付帮助库(微信支付相关)
 * ====================================================
 * 接口分三种类型：
 * 【请求型接口】--Wxpay_client_
 * 		统一支付接口类--UnifiedOrder
 * 		订单查询接口--OrderQuery
 * 		退款申请接口--Refund
 * 		退款查询接口--RefundQuery
 * 		对账单接口--DownloadBill
 * 		短链接转换接口--ShortUrl
 * 【响应型接口】--Wxpay_server_
 * 		通用通知接口--Notify
 * 		Native支付——请求商家获取商品信息接口--NativeCall
 * 【其他】
 * 		静态链接二维码--NativeLink
 * 		JSAPI支付--JsApi
 * =====================================================
 * 【CommonUtil】常用工具：
 * 		trimString()，设置参数时需要用到的字符处理函数
 * 		createNoncestr()，产生随机字符串，不长于32位
 * 		formatBizQueryParaMap(),格式化参数，签名过程需要用到
 * 		getSign(),生成签名
 * 		arrayToXml(),array转xml
 * 		xmlToArray(),xml转 array
 * 		postXmlCurl(),以post方式提交xml到对应的接口url
 * 		postXmlSSLCurl(),使用证书，以post方式提交xml到对应的接口url
*/
public class WxPayPubHelper {
		
	/** = */
	public static final String QSTRING_EQUAL = "=";

	/** & */
	public static final String QSTRING_SPLIT = "&";
	
	/**
	 * 	作用：产生随机字符串，不长于32位
	 */
	public String createNoncestr(int length){
		
		String chars = "abcdefghijklmnopqrstuvwxyz0123456789";  
		String str ="";
		Random rand = new Random();
		for (int i = 0; i < length; i++) {
			int index = rand.nextInt(chars.length());
			str += chars.substring(index, index + 1);
		} 
		return str;
	}
		
	/**
	 * 	把请求要素按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param para 请求要素
     * @param sort 是否需要根据key值作升序排列
     * @param encode 是否需要URL编码
     * @return 拼接成的字符串
	 */
	public static String formatBizQueryParaMap(Map<String,String> para,boolean sort, boolean encode)
	{
		List<String> keys = new ArrayList<String>(para.keySet());
        
        if (sort)
        	Collections.sort(keys);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = para.get(key);
            
            if (encode) {
				try {
					value = URLEncoder.encode(value, WxPayConf_pub.ENCODE);
				} catch (UnsupportedEncodingException e) {
				}
            }
            
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                sb.append(key).append(QSTRING_EQUAL).append(value);
            } else {
                sb.append(key).append(QSTRING_EQUAL).append(value).append(QSTRING_SPLIT);
            }
        }
        return sb.toString();
	}
	
	/**
	 * 	作用：生成签名
	 */
	public static String getSign(Map<String,String> paramMap)
	{
	//	System.out.println("------------------bef-----------");
		for (String key : paramMap.keySet()) {//
	//		System.out.println("-------------------------:" + key);
			//值为空不参加签名
			if(StringUtil.isNullOrEmpty(paramMap.get(key))){
	//			System.out.println("-------------------------1:" + key);
				paramMap.remove(key);
				
			}
		}
		
		
		String params = formatBizQueryParaMap(paramMap, true, false);
		//echo '【string1】'.$String.'</br>';
		//签名步骤二：在string后加入KEY
		params = params + "&key=" + WxPayConf_pub.KEY;//
		//echo "【string2】".$String."</br>";
		//签名步骤三：MD5加密并转大写
		params = MD5Util.MD5(params);
		//echo "【string3】 ".$String."</br>";
		//签名步骤四：所有字符转为大写
		//echo "【result】 ".$result_."</br>";
		return params;
	}
	
//	boolean hasSymble = !str.matches("^[\\da-zA-Z]*$"); 
	
	/**
	 * 	作用：array转xml
	 */
	public static String arrayToXml(Map<String,String> paramMap){
        String xml = "<xml>";
        
        for (String key : paramMap.keySet()) {//
      //  	System.out.println("key:" + key + "---------------value:" + paramMap.get(key));
			//值是否只有字母和数字
			if(paramMap.get(key).matches("^[\\da-zA-Z]*$")){
				
				xml += "<" + key + ">" + paramMap.get(key) + "</" + key + ">"; 
				
			}else{
				
				xml += "<" + key + "><![CDATA[" + paramMap.get(key) + "]]></" + key + ">";
				
			}
		}
        
        xml += "</xml>";
        
        return xml;
    }
	
	/**
	 * xml 转  map
	 * @param xml
	 * @return
	 */
	public static Map<String,String> xmltoMap(String xml) {  
        try {  
            Map<String,String> map = new HashMap<String,String>();  
            Document document = DocumentHelper.parseText(xml);  
            Element nodeElement = document.getRootElement();  
            List node = nodeElement.elements();  
            for (Iterator it = node.iterator(); it.hasNext();) {
                Element elm = (Element) it.next();  
                String val = elm.getText();
                val = val.replace("<![CDATA[", "");
                val = val.replace("]]>", "");
                map.put(elm.getName(), val);  
                elm = null;  
            }  
            node = null;
            nodeElement = null;  
            document = null;  
            return map;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }
	
	/**
	 * 	作用：以post方式提交xml到对应的接口url
	 */
//	public boolean postXmlCurl(String xml,String url,int second){		
//        //初始化curl        
//       	$ch = curl_init();
//		//设置超时
//		curl_setopt($ch, CURLOP_TIMEOUT, $second);
//        //这里设置代理，如果有的话
//        //curl_setopt($ch,CURLOPT_PROXY, '8.8.8.8');
//        //curl_setopt($ch,CURLOPT_PROXYPORT, 8080);
//        curl_setopt($ch,CURLOPT_URL, $url);
//        curl_setopt($ch,CURLOPT_SSL_VERIFYPEER,FALSE);
//        curl_setopt($ch,CURLOPT_SSL_VERIFYHOST,FALSE);
//		//设置header
//		curl_setopt($ch, CURLOPT_HEADER, FALSE);
//		//要求结果为字符串且输出到屏幕上
//        curl_setopt($ch, CURLOPT_RETURNTRANSFER, TRUE);
//		//post提交方式
//		curl_setopt($ch, CURLOPT_POST, TRUE);
//		curl_setopt($ch, CURLOPT_POSTFIELDS, $xml);
//		//运行curl
//        $data = curl_exec($ch);
//		curl_close($ch);
//		//返回结果
//		if($data)
//		{
//			curl_close($ch);
//			return $data;
//		}
//		else 
//		{ 
//			$error = curl_errno($ch);
//			echo "curl出错，错误码:$error"."<br>"; 
//			echo "<a href='http://curl.haxx.se/libcurl/c/libcurl-errors.html'>错误原因查询</a></br>";
//			curl_close($ch);
//			return false;
//		}
//	}
	
	
	
	public static void main(String[] args) {
		/*String str = "<![CDATA[123]]>";
		str = str.replace("<![CDATA[", "");
		str = str.replace("]]>", "");
		System.out.println(str + "-------" + str.length());*/
		
		Map<String,String> map = new HashMap<String,String>();
		//ok
		map.put("appid", "wx003e020dd9cc431e");
		map.put("bank_type", "CMBC_DEBIT");
		map.put("fee_type", "CNY");
		map.put("is_subscribe", "N");
		map.put("mch_id", "10021093");
		map.put("nonce_str", "xhoy6wsue7enbifryz75sf9zzjnetz3j");
		map.put("openid", "oiLgNs1t93Pt_pkf0rNx4wiBRNUU");
		map.put("out_trade_no", "1414723592");	
		map.put("result_code", "SUCCESS");
		map.put("return_code", "SUCCESS");
		map.put("sub_mch_id", "10021093");
		map.put("time_end", "20141031104651");
		map.put("total_fee", "1");
		map.put("trade_type", "JSAPI");
		map.put("transaction_id", "1006560229201410310005550787");
		System.out.println(getSign(map));
		
		//OK
		/*String xml = "<xml><appid><![CDATA[wx34d95648ae68e10b]]></appid>"
				+ "<bank_type><![CDATA[CMB_DEBIT]]></bank_type>"
				+ "<fee_type><![CDATA[CNY]]></fee_type>"
				+ "<is_subscribe><![CDATA[Y]]></is_subscribe>"
				+ "<mch_id><![CDATA[10018724]]></mch_id>"
				+ "<nonce_str><![CDATA[b4vwqzc02rc24l5lzi8xl83puzulpmnv]]></nonce_str>"
				+ "<openid><![CDATA[oAAAAAJYh6kDEaHJt9VxPZB2N-78]]></openid>"
				+ "<out_trade_no><![CDATA[2014102114500795030]]></out_trade_no>"
				+ "<result_code><![CDATA[SUCCESS]]></result_code>"
				+ "<return_code><![CDATA[SUCCESS]]></return_code>"
				+ "<sign><![CDATA[AB0CDB66A8781AC55F7518D5289034AD]]></sign>"
				+ "<sub_mch_id><![CDATA[10018724]]></sub_mch_id>"
				+ "<time_end><![CDATA[20141021145025]]></time_end>"
				+ "<total_fee>1</total_fee>"
				+ "<trade_type><![CDATA[JSAPI]]></trade_type>"
				+ "<transaction_id><![CDATA[1001860932201410210005411576]]></transaction_id>"
				+ "</xml>";
		
		System.out.println(xmltoMap(xml));*/
		//微信查询接口测试
//		map.put("appid", "wx34d95648ae68e10b");
//		map.put("mch_id", "10018724");
//		map.put("nonce_str", "b4vwqzc02rc24l5lzi8xl83puzulpmnv");
//		map.put("out_trade_no", "2014102114500795030");
//		map.put("sign", getSign(map));
//		String xml = arrayToXml(map);
//		String url = "https://api.mch.weixin.qq.com/pay/orderquery";
//		System.out.println(HttpInvoker.sendPostRequest(url, xml));
	}
	
}

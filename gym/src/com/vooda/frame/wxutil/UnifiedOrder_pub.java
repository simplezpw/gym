package com.vooda.frame.wxutil;


/**
 * 统一支付接口类

 * @ClassName: UnifiedOrder_pub

 * @Description: TODO

 * @author: 宋权权

 * @date: 2014年10月17日 下午2:31:42
 */
public class UnifiedOrder_pub extends Wxpay_client_pub {
	
	//终端ip
	public String spbill_create_ip = "127.0.0.1";
	
	public UnifiedOrder_pub(){
		//设置接口链接
		url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		//设置url超时时间
		curl_timeout = 30;
	}
	
	/**
	 * 生成接口参数xml
	 */
	public String createXml(){
		
		try
		{
			//检测必填参数
			if(parameters.get("out_trade_no") == null){
				throw new SDKRuntimeException("缺少统一支付接口必填参数out_trade_no！");
			}else if(parameters.get("body") == null){
				throw new SDKRuntimeException("缺少统一支付接口必填参数body！");
			}else if(parameters.get("total_fee") == null ) {
				throw new SDKRuntimeException("缺少统一支付接口必填参数total_fee！");
			}else if(parameters.get("notify_url") == null) {
				throw new SDKRuntimeException("缺少统一支付接口必填参数notify_url！");
			}else if(parameters.get("trade_type") == null) {
				throw new SDKRuntimeException("缺少统一支付接口必填参数trade_type！");
			}else if(parameters.get("trade_type") == "JSAPI" && parameters.get("openid") == null){
				throw new SDKRuntimeException("统一支付接口中，缺少必填参数openid！trade_type为JSAPI时，openid为必填参数！");
			}
			
			parameters.put("appid", WxPayConf_pub.APPID);//公众账号ID
			parameters.put("mch_id", WxPayConf_pub.MCHID);//商户号
			parameters.put("nonce_str", createNoncestr(32));//随机字符串
			parameters.put("spbill_create_ip", spbill_create_ip);//终端ip
			parameters.put("sign", getSign(parameters));//签名
			
		}catch (SDKRuntimeException $e){
			System.out.println("-----------------------参数出错");
		}
		
		return  arrayToXml(parameters);
	}
	
	
	/**
	 * 获取prepay_id
	 */
	public String getPrepayId(){
		
		resultMap = xmltoMap(postXml());
		return resultMap.get("prepay_id");
	}
	
}

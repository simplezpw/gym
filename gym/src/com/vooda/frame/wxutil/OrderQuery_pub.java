package com.vooda.frame.wxutil;


/**
 * 订单查询接口

 * @ClassName: OrderQuery_pub

 * @Description: TODO

 * @author: 宋权权

 * @date: 2014年10月17日 下午2:56:17
 */
public class OrderQuery_pub extends Wxpay_client_pub {
	
	public OrderQuery_pub(){
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
			if(parameters.get("out_trade_no") == null && parameters.get("transaction_id") == null){
				
				throw new SDKRuntimeException("订单查询接口中，out_trade_no、transaction_id至少填一个！");
			}
			
			parameters.put("appid", WxPayConf_pub.APPID);//公众账号ID
			parameters.put("mch_id", WxPayConf_pub.MCHID);//商户号
			parameters.put("nonce_str", createNoncestr(32));//随机字符串
			parameters.put("sign", getSign(parameters));//签名
			
		}catch (SDKRuntimeException e){
			System.out.println("-----------------订单查询接口中，参数错误！");
		}
		
		return arrayToXml(parameters);
	}
}

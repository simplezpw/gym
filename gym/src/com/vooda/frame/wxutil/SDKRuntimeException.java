package com.vooda.frame.wxutil;
/**
 * (微信支付相关)

 * @ClassName: SDKRuntimeException

 * @Description: TODO

 * @author: 宋权权

 * @date: 2014年10月16日 下午6:04:36
 */
public class SDKRuntimeException extends Exception{
	@Override
	public String getMessage() {
		return super.getMessage();
	}
	
	public SDKRuntimeException(String msg) {
		System.out.println("msg");
	}
	
}

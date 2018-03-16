package com.vooda.weixin.cp.api;

import com.vooda.weixin.common.exception.WxErrorException;
import com.vooda.weixin.common.session.WxSessionManager;
import com.vooda.weixin.cp.bean.WxCpXmlMessage;

import java.util.Map;

/**
 * 微信消息拦截器，可以用来做验证
 * @author
 */
public interface WxCpMessageInterceptor {

	/**
	 * 拦截微信消息
	 * @param wxMessage
	 * @param context 上下文，如果handler或interceptor之间有信息要传递，可以用这个
	 * @param wxCpService
	 * @param sessionManager
	 * @return true代表OK，false代表不OK
	 */
	public boolean intercept(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService, WxSessionManager sessionManager) throws WxErrorException;

}

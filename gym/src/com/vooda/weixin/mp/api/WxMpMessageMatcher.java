package com.vooda.weixin.mp.api;

import com.vooda.weixin.mp.bean.WxMpXmlMessage;

/**
 * 消息匹配器，用在消息路由的时候
 */
public interface WxMpMessageMatcher {

	/**
	 * 消息是否匹配某种模式
	 * @param message
	 * @return
	 */
	public boolean match(WxMpXmlMessage message);

}

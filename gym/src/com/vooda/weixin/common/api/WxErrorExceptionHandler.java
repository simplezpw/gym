package com.vooda.weixin.common.api;

import com.vooda.weixin.common.exception.WxErrorException;

/**
 * WxErrorException处理器
 */
public interface WxErrorExceptionHandler {

	public void handle(WxErrorException e);

}

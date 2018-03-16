package com.vooda.weixin.common.util;

import com.vooda.weixin.common.api.WxErrorExceptionHandler;
import com.vooda.weixin.common.exception.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogExceptionHandler implements WxErrorExceptionHandler {

	private Logger log = LoggerFactory.getLogger(WxErrorExceptionHandler.class);

	public void handle(WxErrorException e) {

		log.error("Error happens", e);

	}

}

package com.vooda.weixin.mp.bean.custombuilder;

import com.vooda.weixin.mp.bean.WxMpCustomMessage;

public class BaseBuilder<T> {
	protected String msgType;
	protected String toUser;

	public T toUser(String toUser) {
		this.toUser = toUser;
		return (T) this;
	}

	public WxMpCustomMessage build() {
		WxMpCustomMessage m = new WxMpCustomMessage();
		m.setMsgType(this.msgType);
		m.setToUser(this.toUser);
		return m;
	}
}

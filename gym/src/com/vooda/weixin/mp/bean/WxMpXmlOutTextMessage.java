package com.vooda.weixin.mp.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.vooda.weixin.common.api.WxConsts;
import com.vooda.weixin.common.util.xml.XStreamCDataConverter;

@XStreamAlias("xml")
public class WxMpXmlOutTextMessage extends WxMpXmlOutMessage {

	@XStreamAlias("Content")
	@XStreamConverter(value = XStreamCDataConverter.class)
	private String content;

	public WxMpXmlOutTextMessage() {
		this.msgType = WxConsts.XML_MSG_TEXT;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}

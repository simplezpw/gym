package com.vooda.weixin.cp.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.vooda.weixin.common.api.WxConsts;
import com.vooda.weixin.common.util.xml.XStreamMediaIdConverter;

@XStreamAlias("xml")
public class WxCpXmlOutVoiceMessage extends WxCpXmlOutMessage {

	@XStreamAlias("Voice")
	@XStreamConverter(value = XStreamMediaIdConverter.class)
	private String mediaId;

	public WxCpXmlOutVoiceMessage() {
		this.msgType = WxConsts.XML_MSG_VOICE;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

}

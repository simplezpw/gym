package com.vooda.weixin.mp.bean.outxmlbuilder;

import com.vooda.weixin.mp.bean.WxMpXmlOutVoiceMessage;

/**
 * 语音消息builder
 * @author
 */
public final class VoiceBuilder extends BaseBuilder<VoiceBuilder, WxMpXmlOutVoiceMessage> {

	private String mediaId;

	public VoiceBuilder mediaId(String mediaId) {
		this.mediaId = mediaId;
		return this;
	}

	public WxMpXmlOutVoiceMessage build() {
		WxMpXmlOutVoiceMessage m = new WxMpXmlOutVoiceMessage();
		setCommon(m);
		m.setMediaId(mediaId);
		return m;
	}

}

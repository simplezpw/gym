package com.vooda.weixin.cp.bean.outxmlbuilder;

import com.vooda.weixin.cp.bean.WxCpXmlOutImageMessage;

/**
 * 图片消息builder
 * @author
 */
public final class ImageBuilder extends BaseBuilder<ImageBuilder, WxCpXmlOutImageMessage> {

	private String mediaId;

	public ImageBuilder mediaId(String media_id) {
		this.mediaId = media_id;
		return this;
	}

	public WxCpXmlOutImageMessage build() {
		WxCpXmlOutImageMessage m = new WxCpXmlOutImageMessage();
		setCommon(m);
		m.setMediaId(this.mediaId);
		return m;
	}

}

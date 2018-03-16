package com.vooda.weixin.cp.bean.messagebuilder;

import com.vooda.weixin.common.api.WxConsts;
import com.vooda.weixin.cp.bean.WxCpMessage;

/**
 * 获得消息builder
 * 
 * <pre>
 * 用法: WxCustomMessage m = WxCustomMessage.IMAGE().mediaId(...).toUser(...).build();
 * </pre>
 * @author
 */
public final class ImageBuilder extends BaseBuilder<ImageBuilder> {
	private String mediaId;

	public ImageBuilder() {
		this.msgType = WxConsts.CUSTOM_MSG_IMAGE;
	}

	public ImageBuilder mediaId(String media_id) {
		this.mediaId = media_id;
		return this;
	}

	public WxCpMessage build() {
		WxCpMessage m = super.build();
		m.setMediaId(this.mediaId);
		return m;
	}
}

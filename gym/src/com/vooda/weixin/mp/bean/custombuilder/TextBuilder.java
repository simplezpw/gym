package com.vooda.weixin.mp.bean.custombuilder;

import com.vooda.weixin.common.api.WxConsts;
import com.vooda.weixin.mp.bean.WxMpCustomMessage;

/**
 * 文本消息builder
 * 
 * <pre>
 * 用法: WxMpCustomMessage m = WxMpCustomMessage.TEXT().content(...).toUser(...).build();
 * </pre>
 * @author
 */
public final class TextBuilder extends BaseBuilder<TextBuilder> {
	private String content;

	public TextBuilder() {
		this.msgType = WxConsts.CUSTOM_MSG_TEXT;
	}

	public TextBuilder content(String content) {
		this.content = content;
		return this;
	}

	public WxMpCustomMessage build() {
		WxMpCustomMessage m = super.build();
		m.setContent(this.content);
		return m;
	}
}

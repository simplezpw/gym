package com.vooda.weixin.common.util.xml;

public class XStreamMediaIdConverter extends XStreamCDataConverter {

	public String toString(Object obj) {
		return "<MediaId>" + super.toString(obj) + "</MediaId>";
	}
}

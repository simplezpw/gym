/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2013. All rights reserved. This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended only for the use of KINGSTAR MEDIA application
 * development. Reengineering, reproduction arose from modification of the original source, or other redistribution of this source is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */
package com.vooda.weixin.mp.util.json;

import com.google.gson.*;
import com.vooda.weixin.common.util.json.GsonHelper;
import com.vooda.weixin.mp.bean.result.WxMpUser;

import java.lang.reflect.Type;

public class WxMpUserGsonAdapter implements JsonDeserializer<WxMpUser> {

	public WxMpUser deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject o = json.getAsJsonObject();
		WxMpUser wxMpUser = new WxMpUser();
		wxMpUser.setSubscribe(new Integer(0).equals(GsonHelper.getInteger(o, "subscribe")) ? false : true);
		wxMpUser.setCity(GsonHelper.getString(o, "city"));
		wxMpUser.setCountry(GsonHelper.getString(o, "country"));
		wxMpUser.setHeadImgUrl(GsonHelper.getString(o, "headimgurl"));
		wxMpUser.setLanguage(GsonHelper.getString(o, "language"));
		wxMpUser.setNickname(GsonHelper.getString(o, "nickname"));
		wxMpUser.setOpenId(GsonHelper.getString(o, "openid"));
		wxMpUser.setProvince(GsonHelper.getString(o, "province"));
		wxMpUser.setSubscribeTime(GsonHelper.getLong(o, "subscribe_time"));
		wxMpUser.setUnionId(GsonHelper.getString(o, "unionid"));
		Integer sexId = GsonHelper.getInteger(o, "sex");
		wxMpUser.setSexId(sexId);
		if (new Integer(1).equals(sexId)) {
			wxMpUser.setSex("男");
		} else if (new Integer(2).equals(sexId)) {
			wxMpUser.setSex("女");
		} else {
			wxMpUser.setSex("未知");
		}
		return wxMpUser;
	}

}

/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2013. All rights reserved. This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended only for the use of KINGSTAR MEDIA application
 * development. Reengineering, reproduction arose from modification of the original source, or other redistribution of this source is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */
package com.vooda.weixin.mp.util.json;

import com.google.gson.*;
import com.vooda.weixin.common.util.json.GsonHelper;
import com.vooda.weixin.mp.bean.result.WxMpQrCodeTicket;

import java.lang.reflect.Type;

/**
 * @author
 */
public class WxQrCodeTicketAdapter implements JsonDeserializer<WxMpQrCodeTicket> {

	public WxMpQrCodeTicket deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		WxMpQrCodeTicket ticket = new WxMpQrCodeTicket();
		JsonObject ticketJsonObject = json.getAsJsonObject();

		if (ticketJsonObject.get("ticket") != null && !ticketJsonObject.get("ticket").isJsonNull()) {
			ticket.setTicket(GsonHelper.getAsString(ticketJsonObject.get("ticket")));
		}
		if (ticketJsonObject.get("expire_seconds") != null && !ticketJsonObject.get("expire_seconds").isJsonNull()) {
			ticket.setExpire_seconds(GsonHelper.getAsPrimitiveInt(ticketJsonObject.get("expire_seconds")));
		}
		if (ticketJsonObject.get("url") != null && !ticketJsonObject.get("url").isJsonNull()) {
			ticket.setUrl(GsonHelper.getAsString(ticketJsonObject.get("url")));
		}
		return ticket;
	}

}

package com.vooda.weixin.common.bean.result;

import com.vooda.weixin.common.util.json.WxGsonBuilder;

import java.io.Serializable;

public class WxMediaUploadResult implements Serializable {

	private String type;
	private String mediaId;
	private String thumbMediaId;
	private long createdAt;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	public static WxMediaUploadResult fromJson(String json) {
		return WxGsonBuilder.create().fromJson(json, WxMediaUploadResult.class);
	}

	public String toString() {
		return "WxUploadResult [type=" + type + ", media_id=" + mediaId + ", thumb_media_id=" + thumbMediaId + ", created_at=" + createdAt + "]";
	}

}

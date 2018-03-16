package com.vooda.frame.entity;

public class SendMeas {
	private String usertp;
	private String highv;
	private String lowv;
	private String heartrate;
	private String conclusion;//结论
	private String concolor;//结论颜色
	private String mtime;
	public String getUsertp() {
		return usertp;
	}
	public void setUsertp(String usertp) {
		this.usertp = usertp;
	}
	public String getHighv() {
		return highv;
	}
	public void setHighv(String highv) {
		this.highv = highv;
	}
	public String getLowv() {
		return lowv;
	}
	public void setLowv(String lowv) {
		this.lowv = lowv;
	}
	public String getHeartrate() {
		return heartrate;
	}
	public void setHeartrate(String heartrate) {
		this.heartrate = heartrate;
	}
	public String getMtime() {
		return mtime;
	}
	public void setMtime(String mtime) {
		this.mtime = mtime;
	}
	public String getConclusion() {
		return conclusion;
	}
	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}
	public String getConcolor() {
		return concolor;
	}
	public void setConcolor(String concolor) {
		this.concolor = concolor;
	}
	
}

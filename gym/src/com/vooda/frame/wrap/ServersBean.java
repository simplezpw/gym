package com.vooda.frame.wrap;

import java.util.List;

import com.vooda.frame.server.parent.AbstractServer;



/**
 * 
 *@Title:后台服务所有的包装列表
 *@Description:
 *@Author:fengjianshe
 *@Since:2014年04月11日
 *@Version:1.1.0
 */
public class ServersBean {
	// 服务列表
	private List<AbstractServer> serverList;

	public List<AbstractServer> getServerList() {
		return serverList;
	}

	public void setServerList(List<AbstractServer> serverList) {
		this.serverList = serverList;
	}

}

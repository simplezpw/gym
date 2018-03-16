package com.vooda.frame.server.parent;


/**
 * 
 * @Title:后台服务父类
 * @Description:
 * @Author:fengjianshe
 * @Since:2013年8月28日
 * @Version:1.1.0
 */
public abstract class AbstractServer {

	public void startServer() {
		doWork();
	}

	protected abstract void doWork();

}

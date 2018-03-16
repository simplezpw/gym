package com.vooda.frame.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vooda.frame.server.parent.AbstractServer;

/**
 * 清理服务
 * @author Administrator
 *
 */
public class CleanServer extends AbstractServer implements Runnable {
	private static Logger log = LoggerFactory.getLogger(CleanServer.class);
	
	@Override
	public void run() {
		System.out.println("----------------------cleanserver");
		
	}

	@Override
	protected void doWork() {
		Thread thread = new Thread(this);
		log.info("start cleanserver succesfull.....");
		thread.start();//启动线程初始化菜单
	}

}

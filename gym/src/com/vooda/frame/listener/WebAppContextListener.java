package com.vooda.frame.listener;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vooda.frame.server.parent.AbstractServer;
import com.vooda.frame.util.Const;
import com.vooda.frame.util.SpringContextsUtil;
import com.vooda.frame.wrap.ServersBean;

/**
 * @author vooda
 * 
 * @version 1.0
 */
public class WebAppContextListener extends ContextLoaderListener{

	private List<AbstractServer> serverList;
	
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
	}

	public void contextInitialized(ServletContextEvent event) {
		// TODO Auto-generated method stub
		super.contextInitialized(event);
		Const.WEB_APP_CONTEXT = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		//System.out.println("========获取Spring WebApplicationContext");
		event.getServletContext().setAttribute("rt", event.getServletContext().getContextPath());
		
		ServersBean sb = (ServersBean) SpringContextsUtil.getBean("ServersBean");
		this.serverList = sb.getServerList();
		start();
	}
	
	private void start() {
		// 启动接入服务器
		for (AbstractServer server : serverList) {
			server.startServer();
		}
	}

}

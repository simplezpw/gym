package com.vooda.frame.server;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vooda.frame.server.parent.AbstractServer;
import com.vooda.frame.wxutil.Menu;
import com.vooda.frame.wxutil.WeixinService;
import com.vooda.weixin.common.bean.WxMenu;
import com.vooda.weixin.common.bean.WxMenu.WxMenuButton;

public class MenuServer extends AbstractServer implements Runnable {
	
	private static final Logger log = LoggerFactory.getLogger(MenuServer.class);
	
	@Autowired
	private WeixinService weixinService; 
	
	@Override
	public void run() {
		try{
		
			List<Menu> menuList = weixinService.getMenuList();
			
			WxMenu wxmenu = new WxMenu();
			
			List<WxMenuButton> btsList = new ArrayList<WxMenuButton>();
			
			for (Menu m : menuList) {
				if(m.getMenu_type().equals("1") && m.getParent_menu_id().equals("0")){//父菜单
					WxMenuButton btn = new WxMenuButton();
					btn.setKey(m.getKey());
					btn.setName(m.getName());
					btn.setType(m.getType());
					if(m.getType().equals("view")){
						btn.setUrl(m.getMenu_url());
					}
					List<WxMenuButton> subList = new ArrayList<WxMenuButton>();
					for (Menu m1 : menuList) {
						
						if(m1.getMenu_type().equals("0") && m.getMenu_id().equals(m1.getParent_menu_id())){//二级菜单
							
							WxMenuButton btn_1 = new WxMenuButton();
							btn_1.setKey(m1.getKey());
							btn_1.setName(m1.getName());
							btn_1.setType(m1.getType());
							if(m1.getType().equals("view")){
								btn_1.setUrl(m1.getMenu_url());
							}
							subList.add(btn_1);
						}
						
					}
					
					if(subList.size() > 0){//有子菜单
						btn.setSubButtons(subList);
					}
					btsList.add(btn);
				}
			}
			
			wxmenu.setButtons(btsList);
			TokenServer.wxMpService.menuCreate(wxmenu);
		}catch(Exception e){
			System.out.println("创建菜单失败：" + e.getMessage());
		}
	}

	@Override
	protected void doWork() {
		Thread thread = new Thread(this);
		log.info("start menuserver succesfull.....");
		thread.start();//启动线程初始化菜单
	}

}

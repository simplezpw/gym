package com.vooda.weixin.test;

import java.util.ArrayList;
import java.util.List;

import com.vooda.weixin.common.bean.WxMenu;
import com.vooda.weixin.common.bean.WxMenu.WxMenuButton;
import com.vooda.weixin.common.exception.WxErrorException;
import com.vooda.weixin.mp.api.WxMpInMemoryConfigStorage;
import com.vooda.weixin.mp.api.WxMpMessageRouter;
import com.vooda.weixin.mp.api.WxMpService;
import com.vooda.weixin.mp.api.WxMpServiceImpl;
import com.vooda.weixin.mp.bean.WxMpTemplateData;
import com.vooda.weixin.mp.bean.WxMpTemplateMessage;

public class Test {
	 protected static WxMpInMemoryConfigStorage wxMpConfigStorage;
	  protected static  WxMpService wxMpService;
	  protected static WxMpMessageRouter wxMpMessageRouter;


	public static void main(String[] args) throws WxErrorException{
		wxMpConfigStorage = new WxMpInMemoryConfigStorage();
		wxMpConfigStorage.setAppId("wxdbe7cd1dde27dc65"); // 设置微信公众号的appid
		wxMpConfigStorage.setSecret("e2289bd907001ca94c4c027e3c30cf65"); // 设置微信公众号的app corpSecret
		wxMpConfigStorage.setToken("weixincore"); // 设置微信公众号的token
//		wxMpConfigStorage.setAesKey("CnSsI35Rwz4Iv8RErXr2T0dUxTFO6XLhWbtAH7D0VKy"); // 设置微信公众号的EncodingAESKey
//		wxMpConfigStorage.setAccessToken("EDP4bMIu_tM0-kWIDu4oBzucY2pW48_-aeavPKPs5vWBsRwIS0A6IpWLRHRS1YiIicCIXn86TaU6BGLJv2oDwxYwUYGUL0pfnKymq6yUJ7I");
		wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(wxMpConfigStorage);
		
		WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
		templateMessage.setToUser("o4dhjs13dxdRB6RqtCUOYWsjkeVY");
		templateMessage.setTemplateId("wGVSrQOgEWzVyFROMUQ4EMXad8aabr1U8mhBaMojPCs");
		templateMessage.setUrl("");
		templateMessage.setTopColor("red");
		templateMessage.getDatas().add(new WxMpTemplateData("first", "爸爸测量血压", "#ff0033"));
		templateMessage.getDatas().add(new WxMpTemplateData("gy", "120", "#000"));
		templateMessage.getDatas().add(new WxMpTemplateData("dy", "60", "#000"));
		templateMessage.getDatas().add(new WxMpTemplateData("xl", "74", "#000"));
		templateMessage.getDatas().add(new WxMpTemplateData("time", "2015-08-20 10:38", "#000"));
		templateMessage.getDatas().add(new WxMpTemplateData("sp", "正常血压", "#339933"));

		wxMpService.templateSend(templateMessage);

		
		//-----------------自定义菜单
//		WxMenu menu = new WxMenu();
//		
//		List<WxMenuButton> btsList = new ArrayList<WxMenuButton>();
//		WxMenuButton btn1 = new WxMenuButton();
//		btn1.setKey("b_001");
//		btn1.setName("问医生");
//		btn1.setType("click");
//		btsList.add(btn1);
//		WxMenuButton btn2 = new WxMenuButton();
//		btn2.setKey("b_002");
//		btn2.setName("资讯");
//		btn2.setType("click");
//		btsList.add(btn2);
//		WxMenuButton btn3 = new WxMenuButton();
//		btn3.setName("档案与设置");
//		btn3.setType("view");
//		btn3.setKey("b_003");
//		//二级菜单
//		List<WxMenuButton> subList = new ArrayList<WxMenuButton>();
//		WxMenuButton btn3_3 = new WxMenuButton();
//		btn3_3.setName("爸爸档案");
//		btn3_3.setType("view");
//		btn3_3.setUrl("http://www.baidu.com");
//		subList.add(btn3_3);
//		WxMenuButton btn3_2 = new WxMenuButton();
//		btn3_2.setName("妈妈档案");
//		btn3_2.setType("view");
//		btn3_2.setUrl("http://www.baidu.com");
//		subList.add(btn3_2);
//		WxMenuButton btn3_1 = new WxMenuButton();
//		btn3_1.setName("设置");
//		btn3_1.setType("view");
//		btn3_1.setUrl("http://www.baidu.com");
//		subList.add(btn3_1);
//		
//		btn3.setSubButtons(subList);
//		btsList.add(btn3);
//		
//		menu.setButtons(btsList);
//		
//		
//		wxMpService.menuCreate(menu);
//		//-----------------
		
		
		/*
		String openid = "oyXoeuOWGybjWwcru6cRscGXSZdw";
	//	WxMpCustomMessage message = WxMpCustomMessage.TEXT().toUser(openid).content("Hello World").build();
		//wxMpService.customMessageSend(message);
		
		WxMpCustomMessage.WxArticle article = new WxMpCustomMessage.WxArticle();
		article.setDescription("测试");
		article.setPicUrl("http://images.china.cn/attachement/jpg/site1000/20150730/c03fd55668721724081a59.jpg");
		article.setTitle("测试");
		article.setUrl("www.baidu.com");
		WxMpCustomMessage message2 = WxMpCustomMessage.NEWS().toUser(openid).addArticle(article).build();
		wxMpService.customMessageSend(message2);
		WxMpMessageHandler handler = new WxMpMessageHandler() {

			@Override
			public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
					Map<String, Object> context, WxMpService wxMpService,
					WxSessionManager sessionManager) throws WxErrorException {
		        WxMpXmlOutTextMessage m
	            = WxMpXmlOutMessage.TEXT().content("测试加密消息").fromUser(wxMessage.getToUserName())
	            .toUser(wxMessage.getFromUserName()).build();
	        return m;
			}
		    };

	    wxMpMessageRouter = new WxMpMessageRouter(wxMpService);
	    wxMpMessageRouter
	        .rule()
	        .async(false)
	        .content("哈哈") // 拦截内容为“哈哈”的消息
	        .handler(handler)
	        .end();*/

	}
}

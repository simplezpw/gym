package com.vooda.frame.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vooda.frame.entity.AccessToken;
import com.vooda.frame.server.parent.AbstractServer;
import com.vooda.frame.wxutil.WxPayConf_pub;
import com.vooda.weixin.common.exception.WxErrorException;
import com.vooda.weixin.mp.api.WxMpInMemoryConfigStorage;
import com.vooda.weixin.mp.api.WxMpMessageRouter;
import com.vooda.weixin.mp.api.WxMpService;
import com.vooda.weixin.mp.api.WxMpServiceImpl;


/**
 * 获取微信token
 * @author Administrator
 *
 */
public class TokenServer extends AbstractServer implements Runnable {

	private static Logger log = LoggerFactory.getLogger(TokenServer.class);  
	public static AccessToken accessToken = null;  

	public static WxMpInMemoryConfigStorage wxMpConfigStorage;
	public static WxMpService wxMpService;
	public static WxMpMessageRouter wxMpMessageRouter;
	
	
	@Override
	public void run() {
		while (true) {
            try {
            	String appid = WxPayConf_pub.APPID;
        		String appsecret = WxPayConf_pub.APPSECRET;
                getAccessToken(appid,appsecret);
                log.info("获取access_token成功，有效时长{}秒 token:{}", wxMpConfigStorage.getAccessToken(), wxMpConfigStorage.getExpiresTime());
                Thread.sleep(2 * 60 * 60 * 1000 - 2 * 60 * 1000);
            } catch (Exception e) {  
                try {  
                    Thread.sleep(60 * 1000);  
                } catch (InterruptedException e1) {  
                    log.error("{}", e1);  
                }  
                log.error("{}", e);  
            }  
        }  
	}

	@Override
	protected void doWork() {
		Thread thread = new Thread(this);
		log.info("start tokensever succesfull.....");
		thread.start();
	}
	
	/**
	 * 获取交互口令
	 * 
	 * @return
	 * @throws WxErrorException 
	 * @Description:
	 */
	public static void getAccessToken(String appid,String appsecret) throws WxErrorException {
		
		wxMpConfigStorage = new WxMpInMemoryConfigStorage();
		wxMpConfigStorage.setAppId(appid); 
		wxMpConfigStorage.setSecret(appsecret); 
		
		wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(wxMpConfigStorage);
		wxMpService.getAccessToken();
	}

}

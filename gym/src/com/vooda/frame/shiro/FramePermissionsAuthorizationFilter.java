package com.vooda.frame.shiro;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.DateUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


public class FramePermissionsAuthorizationFilter extends
		PermissionsAuthorizationFilter {
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private CacheManager shiroCacheManager;
	
	@Override
	public boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws IOException {
		Subject user = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) user.getPrincipal();
	
		/*
		Session session = user.getSession(false);
		Cache<Object, Object> cache = shiroCacheManager.getCache(GlobalStatic.authenticationCacheName);
		String cachedSessionId = cache.get(GlobalStatic.authenticationCacheName+"-"+shiroUser.getAccount()).toString();
		String sessionId=(String) session.getId();
		if(!sessionId.equals(cachedSessionId)){
			user.logout();
		}
		*/
		
		return true;

	}
}

package com.vooda.frame.util;

import com.vooda.system.service.menu.MenuService;
import com.vooda.system.service.role.RoleService;
import com.vooda.system.service.user.UserService;

/**
 * @author vooda 获取Spring容器中的service bean
 */
public final class ServiceHelper {

	public static Object getService(String serviceName) {
		//WebApplicationContextUtils.
		return Const.WEB_APP_CONTEXT.getBean(serviceName);
	}

	public static UserService getUserService() {
		return (UserService) getService("userService");
	}

	public static RoleService getRoleService() {
		return (RoleService) getService("roleService");
	}

	public static MenuService getMenuService() {
		return (MenuService) getService("menuService");
	}
}

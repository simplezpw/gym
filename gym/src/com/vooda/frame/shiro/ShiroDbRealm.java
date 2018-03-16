package com.vooda.frame.shiro;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vooda.frame.common.PageData;
import com.vooda.frame.util.Const;
import com.vooda.frame.util.DateUtil;
import com.vooda.frame.util.StringUtils;
import com.vooda.system.entity.User;
import com.vooda.system.service.user.UserService;


//认证数据库存储
public class ShiroDbRealm extends AuthorizingRealm {
	public Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private CacheManager shiroCacheManager;
	
	@Resource
	UserService userService;
	public static final String HASH_ALGORITHM = "MD5";
	public static final int HASH_INTERATIONS = 1;

    public boolean supports(AuthenticationToken token) {  
        return token instanceof SecCodeUsernamePasswordToken;  
    }  
    
//	public ShiroDbRealm() {
//		// 认证
//		super.setAuthenticationCacheName(GlobalStatic.authenticationCacheName);
//		super.setAuthenticationCachingEnabled(false);
//		// 授权
//		super.setAuthorizationCacheName(GlobalStatic.authorizationCacheName);
//		super.setName(GlobalStatic.authorizingRealmName);
//	}

	// 授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principalCollection) {

		// 因为非正常退出，即没有显式调用 SecurityUtils.getSubject().logout()
		// (可能是关闭浏览器，或超时)，但此时缓存依旧存在(principals)，所以会自己跑到授权方法里。
		if (!SecurityUtils.getSubject().isAuthenticated()) {
			doClearCache(principalCollection);
			SecurityUtils.getSubject().logout();
			return null;
		}

		ShiroUser shiroUser = (ShiroUser) principalCollection
				.getPrimaryPrincipal();
		// String userId = (String)
		// principalCollection.fromRealm(getName()).iterator().next();
		String userId = shiroUser.getId();
		if (StringUtils.isBlank(userId)) {
			return null;
		}
		// 添加角色及权限信息
		SimpleAuthorizationInfo sazi = new SimpleAuthorizationInfo();
		return sazi;
	}

	// 认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		SecCodeUsernamePasswordToken upToken = (SecCodeUsernamePasswordToken) token;

		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		String sessionCode = (String) session.getAttribute(Const.SESSION_SECURITY_CODE); //获取session中的验证码

		String secCode = upToken.getSecCode();
		if (null == secCode || "".equals(secCode)) {
			return null;
		}else{
			if(!secCode.equals(sessionCode)){
				return null;
			}
		}
		// 调用业务方法
		String userName = upToken.getUsername();
		
		String pwd = upToken.getPassword();
		String password = new SimpleHash("SHA-1", userName, pwd).toString();
		try {
			//手机号码
			PageData pd = new PageData();
			pd.put("USERNAME", userName);
			pd.put("PASSWORD", password);
			pd = userService.getUserByNameAndPwd(pd);
			if (pd != null) {
				pd.put("LAST_LOGIN", DateUtil.getTime().toString());
				userService.updateLastLogin(pd);
				User user = new User();
				user.setUSER_ID(pd.getString("USER_ID"));
				user.setUSERNAME(pd.getString("USERNAME"));
				user.setPASSWORD(pd.getString("PASSWORD"));
				user.setNAME(pd.getString("NAME"));
				user.setRIGHTS(pd.getString("RIGHTS"));
				user.setROLE_ID(pd.getString("ROLE_ID"));
				user.setLAST_LOGIN(pd.getString("LAST_LOGIN"));
				user.setIP(pd.getString("IP"));
				user.setSTATUS(pd.getString("STATUS"));
				session.setAttribute(Const.SESSION_USER, user);
				session.removeAttribute(Const.SESSION_SECURITY_CODE);
				// 要放在作用域中的东西，请在这里进行操作
				// SecurityUtils.getSubject().getSession().setAttribute("c_user",
				// user);
				// byte[] salt = EncodeUtils.decodeHex(user.getSalt());

				//Session session = SecurityUtils.getSubject().getSession(false);
				AuthenticationInfo authinfo = new SimpleAuthenticationInfo(
						userName, password, this.getName());
				// Cache<Object, Object> cache =
				// shiroCacheManager.getCache(GlobalStatic.authenticationCacheName);
				// cache.put(GlobalStatic.authenticationCacheName+"-"+userName,
				// session.getId());
				return authinfo;
			} else {
				return null;
			}
			
		} catch (Exception e) {
			//logger.error(e.getMessage(),e);
			throw  new AuthenticationException(e);
		}
	}
}

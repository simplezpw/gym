package com.vooda.frame.shiro;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;


public class SecCodeAuthenticationFilter extends AccessControlFilter{

    public static final String DEFAULT_SECCODE_PARAM = "secCode";
    
    private String secCodeParam = DEFAULT_SECCODE_PARAM;
  
    public String getSecCodeParam() {
		return secCodeParam;
	}

	public void setSecCodeParam(String secCodeParam) {
		this.secCodeParam = secCodeParam;
	}

    
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		return false;
	}
	
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {  
		Subject subject = getSubject(request,response);
        String username = request.getParameter("loginname");
        String password = request.getParameter("password");
        String secCode = request.getParameter("code");
        // password = new SimpleHash("SHA-1", username, password).toString();
		SecCodeUsernamePasswordToken token = new SecCodeUsernamePasswordToken(username, password,secCode);
		boolean allowed = true;
		try{
			subject.login(token);
		}catch(Exception e){
			String message = e.getClass().getSimpleName();
			allowed = false;
		    response.setCharacterEncoding("UTF-8");
	        PrintWriter out = response.getWriter();
	        if ("IncorrectCredentialsException".equals(message)) {
	            out.println("{result:usererror,message:'密码错误'}");
	        } else if ("UnknownAccountException".equals(message)) {
	            out.println("{result:usererror,message:'账号不存在'}");
	        } else if ("LockedAccountException".equals(message)) {
	            out.println("{result:false,message:'账号被锁定'}");
	        } else {
	            out.println("{result:false,message:'未知错误'}");
	        }
	        out.flush();
	        out.close();
	        
			e.printStackTrace();
		}
		return allowed;
	}
	
	private void onLoginFail(ServletResponse response) throws IOException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		httpResponse.getWriter().write("");
	}
}

package com.vooda.frame.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.crypto.hash.SimpleHash;


public class SecCodeUsernamePasswordToken implements AuthenticationToken{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//验证码字符串  
    private String secCode;
    private String username;
    private String password;
    
    
    public SecCodeUsernamePasswordToken(String username,String password,String secCode){
    	this.username = username;
    	this.password = password;
    	this.secCode = secCode;
    }

	public String getSecCode() {
		return secCode;
	}

	public void setSecCode(String secCode) {
		this.secCode = secCode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Object getCredentials() {
		return new SimpleHash("SHA-1", username, password).toString();
	}

	@Override
	public Object getPrincipal() {
		return username;
	}  
  
    
}

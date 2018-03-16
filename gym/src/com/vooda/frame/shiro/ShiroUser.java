package com.vooda.frame.shiro;


import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.vooda.frame.entity.Vuser;

public class ShiroUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 编号
	 */
	private java.lang.String id;
	/**
	 * 姓名
	 */
	private java.lang.String name;
	/**
	 * 账号
	 */
	private java.lang.String account;


	public ShiroUser() {

	}

	public ShiroUser(Vuser user) {
		this.id = user.getId();
		this.account = user.getAccount();
		this.name = user.getName();
	}

	/**
	 * 本函数输出将作为默认的<shiro:principal/>输出.
	 */
	@Override
	public String toString() {
		return this.account;
	}

	/**
	 * 重载equals,只计算account;
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShiroUser other = (ShiroUser) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		return true;
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getAccount())
			.toHashCode();
	}

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getName() {
		return name;
	}

	
	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getAccount() {
		return account;
	}

	public void setAccount(java.lang.String account) {
		this.account = account;
	}
}
package com.vooda.frame.wxutil;

/**
 * 
 * @Title:菜单类
 * @Description:
 * @Author:fengjianshe
 * @Since:2013年8月28日
 * @Version:1.1.0
 */
public class Menu {
	/*
	 * { "button":[ { "name":"菜单", "sub_button":[ { "type":"click",
	 * "name":"hello word", "key":"V1001_HELLO_WORLD" }, { "type":"click",
	 * "name":"赞一下我们", "key":"V1001_GOOD" }] }] }
	 */
	/**
	 * 菜单ID
	 */
	private String menu_id;
	/**
	 * 菜单名称
	 */
	private String name;
	/**
	 * 菜单的触发类型
	 */
	private String type;
	/**
	 * 菜单的触发key值
	 */
	private String key;
	/**
	 * 菜单的类型--父菜单Or子菜单 1是父菜单，0是子菜单
	 */
	private String menu_type;
	/**
	 * 菜单的父菜单ID,0缺省是父菜单
	 */
	private String parent_menu_id = "0";

	/**
	 * 菜单的排序
	 */
	private Integer menu_order;
	/**
	 * type 是view时的Url
	 */
	private String menu_url;

	public Menu(String menu_id, String name, String type, String key,
			String menu_type, String parent_menu_id, Integer menu_order,
			String menu_url) {
		this.menu_id = menu_id;
		this.name = name;
		this.type = type;
		this.key = key;
		this.menu_type = menu_type;
		this.parent_menu_id = parent_menu_id;
		this.menu_order = menu_order;
		this.menu_url = menu_url;
	}

	public Menu() {

	}

	public String getMenu_url() {
		return menu_url;
	}

	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}

	public String getMenu_id() {
		return menu_id;
	}

	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMenu_type() {
		return menu_type;
	}

	public void setMenu_type(String menu_type) {
		this.menu_type = menu_type;
	}

	public String getParent_menu_id() {
		return parent_menu_id;
	}

	public void setParent_menu_id(String parent_menu_id) {
		this.parent_menu_id = parent_menu_id;
	}

	public Integer getMenu_order() {
		return menu_order;
	}

	public void setMenu_order(Integer menu_order) {
		this.menu_order = menu_order;
	}

}

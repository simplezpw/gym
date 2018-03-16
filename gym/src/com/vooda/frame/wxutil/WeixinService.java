package com.vooda.frame.wxutil;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vooda.frame.dao.DaoSupport;

@Service("weixinService")
public class WeixinService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 查询微信菜单
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<Menu> getMenuList()throws Exception{
		List<Menu> mList = (List<Menu>)dao.findForList("WeixinMapper.getMenuList", null);
		return mList;
	}
}

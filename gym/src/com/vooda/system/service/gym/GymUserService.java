package com.vooda.system.service.gym;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vooda.frame.common.PageData;
import com.vooda.frame.dao.DaoSupport;
import com.vooda.frame.entity.Page;

@Service(value="gymUserService")
public class GymUserService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	public PageData userDetail(PageData pd) throws Exception {
		return (PageData) dao.findForObject("GymUserMapper.getUserInfo", pd);
	}
	
	public void registerUser(PageData pd) throws Exception {
		dao.save("GymUserMapper.registerUser", pd);
	}
	
	public void updateUserInfo(PageData pd) throws Exception {
		dao.update("GymUserMapper.updateUserInfo", pd);
	}

	public List<PageData> userListPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("GymUserMapper.gymUserlistPage", page);
	}
}

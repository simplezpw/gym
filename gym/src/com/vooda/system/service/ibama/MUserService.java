package com.vooda.system.service.ibama;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vooda.frame.common.PageData;
import com.vooda.frame.dao.DaoSupport;
import com.vooda.frame.entity.Page;

@Service(value="mUserService")
public class MUserService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	public List<PageData> userlistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("MibamaMapper.userlistPage", page);
	}
	
	public PageData userDetail(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MibamaMapper.userDetail", pd);
	}
	
	public List<PageData> devicelistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("MibamaMapper.devicelistPage", page);
	}
	
	public PageData deviceDetail(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MibamaMapper.deviceDetail", pd);
	}
	
	public List<PageData> userRegistInfo(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MibamaMapper.userRegistInfo", pd);
	}
	
	public int userRegistByDay() throws Exception {
		return (Integer)dao.findForObject("MibamaMapper.userRegistByDay", null);
	}
	
	public int userRegistByWeek() throws Exception {
		return (Integer)dao.findForObject("MibamaMapper.userRegistByWeek", null);
	}
	
	public int userRegistByMonth() throws Exception {
		return (Integer)dao.findForObject("MibamaMapper.userRegistByMonth", null);
	}
	
	public int userRegistTotal() throws Exception {
		return (Integer)dao.findForObject("MibamaMapper.userRegistTotal", null);
	}

	public List<PageData> healthDetaillistPage(Page page) throws Exception{
		return (List<PageData>)dao.findForList("MibamaMapper.healthDetaillistPage", page);
	}
	
	public List<PageData> selecthealthusers(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MibamaMapper.selecthealthusers", pd);
	}
}

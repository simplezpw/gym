package com.vooda.system.service.ibama;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vooda.frame.common.PageData;
import com.vooda.frame.dao.DaoSupport;
import com.vooda.frame.entity.Page;

@Service(value="mSysServerService")
public class MSysServerService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	public List<PageData> feedbacklistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("MibamaMapper.feedbacklistPage", page);
	}
	
	public PageData queryFeedBackById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("MibamaMapper.queryFeedBackById", pd);
	}
	
	public void deletefeedbackById(PageData pd) throws Exception {
		dao.delete("MibamaMapper.deletefeedbackById", pd);
	}
	
	public void deletefeedbackByIds(List<String> ids) throws Exception {
		dao.delete("MibamaMapper.deletefeedbackByIds", ids);
	}

	public List<PageData> pushlistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("MibamaMapper.pushlistPage", page);
	}
	
	public PageData querySysPushById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("MibamaMapper.querySysPushById", pd);
	}
	
	public void insertSysPush(PageData pd) throws Exception {
		dao.save("MibamaMapper.insertSysPush", pd);
	}
	
	public void updatePushTime(PageData pd) throws Exception {
		dao.update("MibamaMapper.updatePushTime", pd);
	}
	
	public void deleteSysPushById(PageData pd) throws Exception {
		dao.delete("MibamaMapper.deleteSysPushById", pd);
	}

	public List<PageData> queryLoglistPage(Page page) throws Exception{
		return (List<PageData>)dao.findForList("MibamaMapper.queryLoglistPage", page);
	}
	
	public PageData querySysLogById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("MibamaMapper.querySysLogById", pd);
	}
	
	public void deleteSysLogById(PageData pd) throws Exception {
		dao.delete("MibamaMapper.deleteSysLogById", pd);
	}

	public void delSysLogByIds(List<String> asList) throws Exception{
		dao.delete("MibamaMapper.delSysLogByIds", asList);
	}
	
	public List<PageData> advertlistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("MibamaMapper.advertlistPage", page);
	}
	
	public PageData selectadvdetail(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MibamaMapper.selectadvdetail", pd);
	}
	
	public void updateAdvert(PageData pd) throws Exception {
		dao.update("MibamaMapper.updateAdvert", pd);
	}
	
	public void deleteAdvertById(PageData pd) throws Exception {
		dao.delete("MibamaMapper.deleteAdvertById", pd);
	}
	
	public void insertAdvert(PageData pd) throws Exception {
		dao.save("MibamaMapper.insertAdvert", pd);
	}

	public List<PageData> queryVersionlistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("MibamaMapper.queryVersionlistPage", page);
	}
	
	public void insertVersion(PageData pd) throws Exception {
		dao.update("MibamaMapper.updateOldVersion", pd);
		dao.save("MibamaMapper.insertVersion", pd);
		//推送..
	}
	
	//id apptype
	public void updateNewVersion(PageData pd) throws Exception {
		dao.update("MibamaMapper.updateOldVersion", pd);
		dao.update("MibamaMapper.updateNewVersion", pd);
	}
	
	public PageData versionDetail(PageData pd) throws Exception {
		return (PageData)dao.findForObject("MibamaMapper.versionDetail", pd);
	}
	
	public void updateVersion(PageData pd) throws Exception {
		dao.update("MibamaMapper.updateVersion", pd);
	}
}

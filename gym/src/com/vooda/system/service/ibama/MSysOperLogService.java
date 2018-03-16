package com.vooda.system.service.ibama;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vooda.frame.common.PageData;
import com.vooda.frame.dao.DaoSupport;
import com.vooda.frame.entity.Page;

@Service("mSysOperLogService")
public class MSysOperLogService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

    @SuppressWarnings("unchecked")
    public List<PageData> sysoperlistPage(Page page) throws Exception{
        return (List<PageData>)dao.findForList("MSysOperLogMapper.sysoperlistPage", page);
    }
    
    public void insert(PageData pd) throws Exception{
        dao.save("MSysOperLogMapper.insert", pd);
    }
    
    public void delete(PageData pd) throws Exception{
        dao.delete("MSysOperLogMapper.deleteoperlog", pd);
    }
}
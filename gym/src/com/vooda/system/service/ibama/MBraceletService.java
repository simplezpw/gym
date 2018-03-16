package com.vooda.system.service.ibama;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vooda.frame.common.PageData;
import com.vooda.frame.dao.DaoSupport;
import com.vooda.frame.entity.Page;
import com.vooda.frame.util.StringUtils;

@Service(value="mBraceletService")
public class MBraceletService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	public List<PageData> braceletlistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("BraceletMapper.braceletlistPage", page);
	}
	
	public List<PageData> querymedicineremind(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("BraceletMapper.querymedicineremind", pd);
	}
	
	public PageData queryBraceletData(PageData pd) throws Exception {
		PageData ret = new PageData();
		if(!pd.containsKey("datatype") || StringUtils.isEmpty(pd.getStringOf("datatype"))){
			pd.put("datatype", 1);
		}
		
		if(!pd.containsKey("datatime") || StringUtils.isEmpty(pd.getStringOf("datatime"))){
			PageData lastrecord = (PageData)dao.findForObject("BraceletMapper.queryLastRecordById", pd);
			if(lastrecord == null) return null;
			pd.put("datatime", lastrecord.getStringOf("recordtime"));
		}
		ret.putAll(pd);
		ret.putAll(queryBraceletDataByDay(pd));
		ret.putAll(queryBraceletDataByWeek(pd));
		ret.putAll(queryBraceletDataByMonth(pd));
		ret.putAll(queryBraceletDataByYear(pd));
		return ret;
	}
	
	public PageData queryBraceletDataByDay(PageData pd) throws Exception {
		PageData ret = (PageData)dao.findForObject("BraceletMapper.queryBraceletDataByDay", pd);
		if(ret == null || ret.isEmpty()) ret = new PageData();
		return ret;
	}
	
	public PageData queryBraceletDataByWeek(PageData pd) throws Exception {
		List<PageData> week = (List<PageData>)dao.findForList("BraceletMapper.queryBraceletDataByWeek", pd);
		PageData ret = new PageData();
		if(week != null){
			StringBuilder dy = new StringBuilder();
			dy.append("[");
			List<PageData> datas = new ArrayList<PageData>();
			PageData data = null;
			String datatype;
			switch (Integer.parseInt(pd.getStringOf("datatype"))) {
				case 1:  datatype = "avghr";	break;
				case 2:  datatype = "avgts";	break;
				case 3:	 datatype = "sumstep";	break;
				default: datatype = ""; break;
			}
			for(int i=0; i<7; i++){
				data = new PageData();
				data.put(datatype, 0);
				if(i == 0)
					data.put("dindex", "0");
				else
					data.put("dindex", i);
				
				datas.add(data);
					
			}
			for(PageData da :datas){
				for(PageData d : week){
					if(Integer.parseInt(da.getStringOf("dindex")) == 
							Integer.parseInt((d.getStringOf("dindex")))){
						da.putAll(d);
					}
				}
			}
			for(int i=0; i< datas.size(); i++){
				dy.append(datas.get(i).getStringOf(datatype));
				dy.append(i == datas.size() - 1 ? "]" : ",");
			}
			ret.put("wy", dy);
		}
		return ret;
	}
	
	public PageData queryBraceletDataByMonth(PageData pd) throws Exception {
		List<PageData> monthdata = (List<PageData>)dao.findForList("BraceletMapper.queryBraceletDataByMonth", pd);
		PageData ret = new PageData();
		if(monthdata != null){
			StringBuilder dy = new StringBuilder();
			dy.append("[");
			List<PageData> datas = new ArrayList<PageData>();
			PageData data = null;
			String datatype;
			switch (Integer.parseInt(pd.getStringOf("datatype"))) {
				case 1:  datatype = "avghr";	break;
				case 2:  datatype = "avgts";	break;
				case 3:	 datatype = "sumstep";	break;
				default: datatype = ""; break;
			}
			for(int i=1; i<=4; i++){
				data = new PageData();
				data.put(datatype, 0);
				data.put("windex", "0" + i);
				datas.add(data);
					
			}
			for(PageData da :datas){
				for(PageData d : monthdata){
					if(Integer.parseInt(da.getStringOf("windex")) == 
							Integer.parseInt((d.getStringOf("windex")))){
						da.putAll(d);
					}
				}
			}
			for(int i=0; i< datas.size(); i++){
				dy.append(datas.get(i).getStringOf(datatype));
				dy.append(i == datas.size() - 1 ? "]" : ",");
			}
			ret.put("my", dy);
		}
		return ret;
	}
	
	public PageData queryBraceletDataByYear(PageData pd) throws Exception {
		List<PageData> yeardata = (List<PageData>)dao.findForList("BraceletMapper.queryBraceletDataByYear", pd);
		PageData ret = new PageData();
		if(yeardata != null){
			StringBuilder dy = new StringBuilder();
			dy.append("[");
			List<PageData> datas = new ArrayList<PageData>();
			PageData data = null;
			String datatype;
			switch (Integer.parseInt(pd.getStringOf("datatype"))) {
				case 1:  datatype = "avghr";	break;
				case 2:  datatype = "avgts";	break;
				case 3:	 datatype = "sumstep";	break;
				default: datatype = ""; break;
			}
			for(int i=1; i<=12; i++){
				data = new PageData();
				data.put(datatype, 0);
				data.put("mindex", "0" + i);
				
				datas.add(data);
					
			}
			for(PageData da :datas){
				for(PageData d : yeardata){
					if(Integer.parseInt(da.getStringOf("mindex")) == 
							Integer.parseInt((d.getStringOf("mindex")))){
						da.putAll(d);
					}
				}
			}
			for(int i=0; i< datas.size(); i++){
				dy.append(datas.get(i).getStringOf(datatype));
				dy.append(i == datas.size() - 1 ? "]" : ",");
			}
			ret.put("yy", dy);
		}
		return ret;
	}
}

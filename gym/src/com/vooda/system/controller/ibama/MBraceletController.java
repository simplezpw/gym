package com.vooda.system.controller.ibama;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vooda.frame.common.PageData;
import com.vooda.frame.controller.BaseController;
import com.vooda.frame.entity.Page;
import com.vooda.frame.util.Const;
import com.vooda.frame.util.LatLonUtil;
import com.vooda.frame.util.StringUtils;
import com.vooda.system.entity.User;
import com.vooda.system.service.ibama.MBraceletService;
import com.vooda.system.service.ibama.MSysOperLogService;

@Controller
@Scope("prototype")
@RequestMapping(value="/mbracelet")
public class MBraceletController extends BaseController{
	
	@Resource(name="mBraceletService")
	MBraceletService braceletService;
	@Resource(name="mSysOperLogService")
	MSysOperLogService logService;
	
	private User user = (User)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_USER);
	
	@RequestMapping(value="/list")
	public ModelAndView muserList(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		String msg = user.getNAME() + new SimpleDateFormat(
				"YYYY-MM-DD HH:mm:ss").format(new Date()) + "查看手环列表";
		mv.addObject("varList", braceletService.braceletlistPage(page));
		insertoperlog(msg);
		mv.addObject("page", page);
		mv.addObject("pd", pd);
		mv.setViewName("system/bracelet/bracelet_list");
		return mv;
	}

	/**
	 * 服药提醒
	 */
	@RequestMapping(value = "/reminds")
	public ModelAndView reminds() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			List<PageData> userinfo = braceletService.querymedicineremind(pd);
			mv.addObject("pd", pd);
			mv.addObject("varList", userinfo);
			mv.setViewName("system/bracelet/medicine_remind");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	@RequestMapping(value="/godata")
	public ModelAndView goData(){
		ModelAndView mv = this.getModelAndView();
		this.getRequest().getSession().setAttribute(
				"braceletid", this.getPageData().getStringOf("braceletid"));
		mv.setViewName("system/bracelet/datas");
		return mv;
	}
	
	/**
	 * 测量数据
	 */
	@RequestMapping(value="/datas")
	@ResponseBody
	public Object deviceList() throws Exception{
		PageData pd = this.getPageData();
		pd.put("braceletid", (String)this.getRequest().getSession().getAttribute("braceletid"));
		return braceletService.queryBraceletData(pd);
	}

	
	private void insertoperlog(String msg) throws Exception{
        PageData logdata = new PageData();
        logdata.put("sysuid", user.getUSER_ID());
        logdata.put("opermsg", msg);
        logService.insert(logdata);
    }
}

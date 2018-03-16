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
import org.springframework.web.servlet.ModelAndView;

import com.vooda.frame.common.PageData;
import com.vooda.frame.controller.BaseController;
import com.vooda.frame.entity.Page;
import com.vooda.frame.util.Const;
import com.vooda.frame.util.LatLonUtil;
import com.vooda.frame.util.StringUtils;
import com.vooda.system.entity.User;
import com.vooda.system.service.ibama.MSysOperLogService;
import com.vooda.system.service.ibama.MUserService;

@Controller
@Scope("prototype")
@RequestMapping(value="/muser")
public class MUserController extends BaseController{
	
	@Resource(name="mUserService")
	MUserService userService;
	@Resource(name="mSysOperLogService")
	MSysOperLogService logService;
	
	private User user = (User)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_USER);
	/**
	 * 用户列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView muserList(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		String msg = user.getNAME() + new SimpleDateFormat(
				"YYYY-MM-DD HH:mm:ss").format(new Date()) + "查看用户列表";
		mv.addObject("varList", userService.userlistPage(page));
		insertoperlog(msg);
		mv.addObject("page", page);
		mv.addObject("pd", pd);
		mv.setViewName("system/ibama/user_list");
		return mv;
	}

	/**
	 * 去用户详情页
	 */
	@RequestMapping(value = "/userDetail")
	public ModelAndView userDetail() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			PageData userinfo = userService.userDetail(pd);
			if(!StringUtils.isEmpty((userinfo.getStringOf("longitude"))) && 
					!StringUtils.isEmpty((userinfo.getStringOf("latitude")))){
				String userlat = String.valueOf(userinfo.get("latitude"));
				String userlon = String.valueOf(userinfo.get("longitude"));
				Map<String,String> cityMap = LatLonUtil.getCity(userlat, userlon);
				userinfo.put("city", cityMap.get("city"));
			}
			mv.addObject("pd", pd);
			mv.addObject("info", userinfo);
			mv.addObject("msg", "saveuser");
			mv.setViewName("system/ibama/user_info");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 设备列表
	 */
	@RequestMapping(value="/devicelist")
	public ModelAndView deviceList(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		String msg = user.getNAME() + new SimpleDateFormat(
				"YYYY-MM-DD HH:mm:ss").format(new Date()) + "查看设备列表";
		insertoperlog(msg);
		mv.addObject("varList", userService.devicelistPage(page));
		mv.addObject("page", page);
		mv.addObject("pd", pd);
		mv.setViewName("system/ibama/device_list");
		return mv;
	}

	/**
	 * 去用户详情页
	 */
	@RequestMapping(value = "/deviceDetail")
	public ModelAndView deviceDetail() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			PageData userinfo = userService.deviceDetail(pd);
			mv.addObject("pd", pd);
			mv.addObject("info", userinfo);
			mv.addObject("msg", "saveuser");
			mv.setViewName("system/ibama/device_info");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	@RequestMapping(value="healthDetail")
	public ModelAndView healthDetail(Page page) throws Exception{
		if(page == null) page = new Page();
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		List<PageData> varList = userService.healthDetaillistPage(page);
		List<PageData> userList = userService.selecthealthusers(pd);
		mv.addObject("page", page);
		mv.addObject("pd", pd);
		mv.addObject("varList", varList);
		mv.addObject("userList", userList);
		mv.setViewName("system/ibama/health_info");
		return mv;
	}
	
	private void insertoperlog(String msg) throws Exception{
        PageData logdata = new PageData();
        logdata.put("sysuid", user.getUSER_ID());
        logdata.put("opermsg", msg);
        logService.insert(logdata);
    }
}

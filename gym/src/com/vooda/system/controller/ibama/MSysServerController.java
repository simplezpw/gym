package com.vooda.system.controller.ibama;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.vooda.frame.common.PageData;
import com.vooda.frame.controller.BaseController;
import com.vooda.frame.entity.Page;
import com.vooda.frame.qiniu.QiniuTools;
import com.vooda.frame.umeng.PushNotification;
import com.vooda.frame.util.AppUtil;
import com.vooda.frame.util.Const;
import com.vooda.frame.util.DateUtil;
import com.vooda.frame.util.ObjectExcelView;
import com.vooda.frame.util.StringUtils;
import com.vooda.system.entity.User;
import com.vooda.system.service.ibama.MSysOperLogService;
import com.vooda.system.service.ibama.MSysServerService;

@Controller
@Scope("prototype")
@RequestMapping(value="/syssver")
public class MSysServerController extends BaseController{
	
	@Resource(name="mSysServerService")
	MSysServerService sysService;
	@Resource(name="mSysOperLogService")
	MSysOperLogService logService;
	
	private User user = (User)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_USER);
	/**
	 * 反馈列表
	 */
	@RequestMapping(value="/feedback_list")
	public ModelAndView muserList(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		mv.addObject("varList", sysService.feedbacklistPage(page));
		mv.addObject("page", page);
		mv.addObject("pd", pd);
		mv.setViewName("system/ibama/feedback_list");
		return mv;
	}

	/**
	 * 删除反馈
	 */
	@RequestMapping(value = "/delfeedback")
	public void deleteU(PrintWriter out) {
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			PageData feedback = sysService.queryFeedBackById(pd);
			if(feedback != null){
				String msg = user.getNAME() + "删除用户id为【" + feedback.getStringOf("userid") + "】发表内容为【" + 
						feedback.getStringOf("content") + "】的反馈【联系方式:" + feedback.getStringOf("phone") + "】";
				sysService.deletefeedbackById(pd);
				insertoperlog(msg);
			}
			out.write("success");
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}

	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/delAllFeedBack")
	@ResponseBody
	public Object deleteAllU() {
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String USER_IDS = pd.getString("USER_IDS");

			if (null != USER_IDS && !"".equals(USER_IDS)) {
				String msg = user.getNAME() + "批量删除用户【" + USER_IDS + "】发表的反馈";
				sysService.deletefeedbackByIds(Arrays.asList(USER_IDS.split(",")));
				insertoperlog(msg);
				pd.put("msg", "ok");
			} else {
				pd.put("msg", "no");
			}

			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	@RequestMapping(value="add_push")
	public ModelAndView addPush() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/ibama/edit_push");
		return mv;
	}
	
	@RequestMapping(value = "/save_push")
    public ModelAndView editU(PrintWriter out) throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        String msg = user.getNAME() + "添加标题【"+pd.getStringOf("title")
        			+"】内容为【" + pd.getStringOf("content") + "】的推送";
        sysService.insertSysPush(pd);
        insertoperlog(msg);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }
	
	@RequestMapping(value="/push_list")
	public ModelAndView pushList(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		mv.addObject("varList", sysService.pushlistPage(page));
		mv.addObject("page", page);
		mv.addObject("pd", pd);
		mv.setViewName("system/ibama/push_list");
		return mv;
	}
	
	/**
	 * 删除推送
	 */
	@RequestMapping(value = "/delsyspush")
	public void delsyspush(PrintWriter out) {
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			PageData feedback = sysService.querySysPushById(pd);
			if(feedback != null){
				String msg = user.getNAME() + "删除标题为【" + feedback.getStringOf("title") + "】的推送";
				sysService.deleteSysPushById(pd);
				insertoperlog(msg);
			}
			out.write("success");
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
	}
	
	@RequestMapping(value="sendpush")
	public void sendpush(PrintWriter out) throws Exception{
		PageData pd = this.getPageData();
		PageData pushmsg = sysService.querySysPushById(pd);
		//发送系统推送
		PushNotification.sendBroadCastNotif(
				pushmsg.getStringOf("push_type"), pushmsg.getStringOf("title"), pushmsg.getStringOf("content"));
		//更新推送时间
		
		pd.put("statu", "1");
		sysService.updatePushTime(pd);
		out.print("success");
		out.close();
	}
	
	@RequestMapping(value = "/delsyslog")
	public void delsyslog(PrintWriter out) {
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			PageData feedback = sysService.querySysLogById(pd);
			if(feedback != null){
				String msg = user.getNAME() + "删除操作日志内容【" + feedback.getStringOf("opermsg") + "】的日志";
				sysService.deleteSysLogById(pd);
				insertoperlog(msg);
			}
			out.write("success");
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
	}
	
	@RequestMapping(value = "/delAllSysLog")
	@ResponseBody
	public Object delAllSysLog() {
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String USER_IDS = pd.getString("USER_IDS");

			if (null != USER_IDS && !"".equals(USER_IDS)) {
				String msg = user.getNAME() + "批量操作日志id【" + USER_IDS + "】的反馈";
				sysService.delSysLogByIds(Arrays.asList(USER_IDS.split(",")));
				insertoperlog(msg);
				pd.put("msg", "ok");
			} else {
				pd.put("msg", "no");
			}

			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	
	@RequestMapping(value="log_list")
	@ResponseBody
	public ModelAndView logList(Page page) throws Exception {
		PageData pd = this.getPageData();
		ModelAndView mv = new ModelAndView();
		page.setPd(pd);
		mv.addObject("varList", sysService.queryLoglistPage(page));
		mv.addObject("page", page);
		mv.addObject("pd", pd);
		mv.setViewName("system/ibama/log_list");
		return mv;
	}
	
	@RequestMapping(value = "/logexcel")
    public ModelAndView exportExcel(Page page) {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        page.setPd(pd);
        try {

            Map<String, Object> dataMap = new HashMap<String, Object>();
            List<String> titles = new ArrayList<String>();

            titles.add("序号"); //1
            titles.add("操作日志"); //2
            titles.add("操作时间"); //3
            titles.add("操作人"); //4

            dataMap.put("titles", titles);

            List<PageData> userList = sysService.queryLoglistPage(page);
            List<PageData> varList = new ArrayList<PageData>();
            for (int i = 0; i < userList.size(); i++) {
                PageData vpd = new PageData();
                vpd.put("var1", String.valueOf(i)); //1
                vpd.put("var2", userList.get(i).getString("opermsg")); //2
                vpd.put("var3", new SimpleDateFormat("YYYY-MM-DD HH:mm:ss").format(userList.get(i).get("logtime"))); //3
                vpd.put("var4", userList.get(i).getString("NAME")); //4
                varList.add(vpd);
            }

            dataMap.put("varList", varList);

            ObjectExcelView erv = new ObjectExcelView(); //执行excel操作

            mv = new ModelAndView(erv, dataMap);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }
	
	private void insertoperlog(String msg) throws Exception{
        PageData logdata = new PageData();
        logdata.put("sysuid", user.getUSER_ID());
        logdata.put("opermsg", msg);
        logService.insert(logdata);
    }
	
	/**
     * 广告列表
     */
	@RequestMapping(value="/advlist")
	public ModelAndView advlist(Page page) throws Exception{
	    ModelAndView mv = new ModelAndView();
	    PageData param = this.getPageData();
	    page.setPd(param);
	    List<PageData> users = sysService.advertlistPage(page);
	    mv.addObject("varList", users);
	    mv.addObject("page", page);
	    mv.addObject("pd", param);
	    mv.setViewName("system/adv/adv_list");
	    return mv;
	}
    
	/**
	 * 去修改广告
	 */
	@RequestMapping(value = "/goEditAdv")
	public ModelAndView goEditAdv() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		try {
			PageData adv = sysService.selectadvdetail(pd);
			mv.setViewName("system/adv/adv_edit");
			mv.addObject("msg", "editAdv");
			mv.addObject("eidttype", "edit");
			mv.addObject("pd", pd);
			mv.addObject("adv", adv);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	@RequestMapping(value="saveadvorder")
	@ResponseBody
	public String saveadvorder() throws Exception{
		PageData pd = this.getPageData();
		sysService.updateAdvert(pd);
		return "success";
	}
	
	/**
	 * 去新增广告
	 */
	@RequestMapping(value = "/goAddAdv")
	public ModelAndView goAddAdv() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		try {
			mv.setViewName("system/adv/adv_edit");
			mv.addObject("msg", "editAdv");
			mv.addObject("eidttype", "add");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 删除广告
	 */
	@RequestMapping(value = "/deleteAdv")
	public void deleteAdv(PrintWriter out) {
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
//			PageData adv = sysService.selectadvdetail(pd);
			/*if(adv != null && !adv.isEmpty() && 
					adv.containsKey("pic_path") && StringUtils.isEmpty(adv.getStringOf("pic_path"))){
				QiniuUtils.deleteOSSObj(adv.getStringOf("pic_path"));
			}*/
			sysService.deleteAdvertById(pd);
			out.write("success");
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}

	}
	
	@RequestMapping(value = "/topAdv")
	public void topAdv(PrintWriter out) {
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			sysService.updateAdvert(pd);
			out.write("success");
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}

	}
	
	/**
     * 修改广告
     */
    @RequestMapping(value = "/editAdv")
    public ModelAndView editAdv(
    		@RequestParam(value="pic_path")MultipartFile file,
    		@RequestParam(value="pic_href")String href,
    		@RequestParam(value="pic_statu")String statu,
    		@RequestParam(value="pic_type")String type,
    		@RequestParam(value="id")String id,
    		@RequestParam(value="pic_desc")String pic_desc,
    		@RequestParam(value="eidttype")String eidttype
    		) throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        if(file != null && !file.isEmpty()){
        	String filename = this.get32UUID()
                    + file.getOriginalFilename().substring(
                            file.getOriginalFilename().lastIndexOf("."));
        	PageData result = QiniuTools.upload(file.getBytes(), filename);
        	if("success".equals(result.getStringOf("statu")))
        		pd.put("pic_path", result.getStringOf("url"));
        }
        pd.put("pic_href", href);
        pd.put("pic_statu", statu);
        pd.put("pic_type", type);
        pd.put("pic_desc", pic_desc);
        if("add".equals(eidttype)){
        	pd.put("id", this.get32UUID());
        	sysService.insertAdvert(pd);
        }else if("edit".equals(eidttype)){
        	pd.put("id", id);
        	sysService.updateAdvert(pd);
        }
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }
    
    @RequestMapping(value="/versionlist")
    public ModelAndView getAppVersion(Page page) throws Exception {
    	PageData pd = this.getPageData();
    	ModelAndView mv = this.getModelAndView();
    	page.setPd(pd);
    	mv.addObject("varList", sysService.queryVersionlistPage(page));
    	mv.addObject("pd", pd);
    	mv.addObject("page", page);
    	mv.setViewName("system/adv/version_list");
    	return mv;
    }
    
    /**
	 * 去新增版本
	 */
	@RequestMapping(value = "/goAddVersion")
	public ModelAndView goAddVersion() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		try {
			mv.setViewName("system/adv/version_edit");
			mv.addObject("msg", "editVersion");
			mv.addObject("eidttype", "add");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 去修改版本
	 */
	@RequestMapping(value = "/goEditVersion")
	public ModelAndView goEditVersion() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		try {
			PageData adv = sysService.versionDetail(pd);
			mv.setViewName("system/adv/version_edit");
			mv.addObject("msg", "editVersion");
			mv.addObject("eidttype", "edit");
			mv.addObject("pd", pd);
			mv.addObject("adv", adv);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	@RequestMapping(value="getToken")
	public void getToken(PrintWriter out){
		JSONObject tokenjson = new JSONObject();
		try {
			tokenjson.accumulate("uptoken", QiniuTools.getToken());
		} catch (JSONException e) {
			
		}
		out.write(tokenjson.toString());
        out.close();
	}
	
	@RequestMapping(value = "/editVersion")
    public ModelAndView editVersion(
    		@RequestParam(value="location")String file,
    		@RequestParam(value="version")String version,
    		@RequestParam(value="apptype")int apptype,
    		@RequestParam(value="id")String id,
    		@RequestParam(value="eidttype")String eidttype,
    		@RequestParam(value="remark")String remark
    		) throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        /*if(file != null && !file.isEmpty()){
        	String filename = DateUtil.getTimeStemp() + "_version_type_" + apptype
                    + file.getOriginalFilename().substring(
                            file.getOriginalFilename().lastIndexOf("."));
        	PageData result = QiniuTools.upload(file.getBytes(), filename);
        	if("success".equals(result.getStringOf("statu")))
        		pd.put("location", result.getStringOf("url"));
        }*/
        if(!StringUtils.isEmpty(file)){
        	pd.put("location", file);
        }
        pd.put("version", version);
        pd.put("apptype", apptype);
        pd.put("remark", remark);
        if("add".equals(eidttype)){
        	sysService.insertVersion(pd);
        }else if("edit".equals(eidttype)){
        	pd.put("id", id);
        	sysService.updateVersion(pd);
        }
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }
	
	/**
	 * 设置为新版本
	 */
	@RequestMapping(value = "/updatenewversion")
	public void updatenewversion(PrintWriter out) {
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			sysService.updateNewVersion(pd);
			out.write("success");
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}

	}
}

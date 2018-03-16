package com.vooda.frame.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.vooda.frame.annotation.ErrorOrder;
import com.vooda.frame.common.Logger;
import com.vooda.frame.common.PageData;
import com.vooda.frame.entity.Page;
import com.vooda.frame.util.SortUtil;
import com.vooda.frame.util.UuidUtil;

public class BaseController {

	protected Logger logger = Logger.getLogger(this.getClass());

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 6357869213649815390L;

	/**
	 * 得到PageData
	 */
	public PageData getPageData() {
		return new PageData(this.getRequest());
	}

	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView() {
		return new ModelAndView();
	}

	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

		return request;
	}

	/**
	 * 得到32位的uuid
	 * 
	 * @return
	 */
	public String get32UUID() {

		return UuidUtil.get32UUID();
	}

	public String getBasePath() {
	    HttpServletRequest request = this.getRequest();
        return request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath() + "/";
	}
	
	/**
	 * 得到分页列表的信息
	 */
	public Page getPage() {

		return new Page();
	}

	public static void logBefore(Logger logger, String interfaceName) {
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}

	public static void logAfter(Logger logger) {
		logger.info("end");
		logger.info("");
	}

	public boolean hasValidateError(BindingResult br,Map<String,Object> retMap,String validateString){
		List<String> validateFields = Arrays.asList(validateString.split(","));
		Object o = br.getTarget();
		List<FieldError> fieldErrors = br.getFieldErrors();
		for (FieldError fieldError : fieldErrors) {
			String field = fieldError.getField();
			if(validateFields.contains(field)){
				retMap.put(field, fieldError.getDefaultMessage());
			}
        }
		
		Class entity = o.getClass();
		Field[] fields = entity.getDeclaredFields();
		List<Map<String,Object>> orderFields = new ArrayList<Map<String,Object>>();
		
		for(Field f : fields){
            //获取字段中包含FieldOrder的注解  
			ErrorOrder order = f.getAnnotation(ErrorOrder.class);
            if(order!=null){
            	Map<String,Object> fieldMap = new HashMap<String,Object>();
            	int i = order.order();
            	String fieldName = f.getName();
            	fieldMap.put("errorOrder", i);
            	if(retMap.containsKey(fieldName)&&validateFields.contains(fieldName)){
            		fieldMap.put("fieldName", fieldName);
            		orderFields.add(fieldMap);
            	}
            }
        } 
		SortUtil.sort(orderFields, "errorOrder", "asc");
		if(orderFields.size()>0){
			String firstField = (String)orderFields.get(0).get("fieldName");
			
			retMap.put("error", br.getFieldError(firstField).getDefaultMessage());
		}
		return !retMap.isEmpty();
	}
}

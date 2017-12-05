package com.points.osp.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.points.osp.common.config.Global;

public class LoginInterceptor implements HandlerInterceptor{

	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		// TODO Auto-generated method stub
		Object user = request.getSession().getAttribute(Global.USER_SESSION_KEY);
		if (user == null) {
	      System.out.println("ÉÐÎ´µÇÂ¼£¬µ÷µ½µÇÂ¼Ò³Ãæ");
	      response.sendRedirect(request.getContextPath() +"/login");
	      return false;
	    }
		return true;
	}

}

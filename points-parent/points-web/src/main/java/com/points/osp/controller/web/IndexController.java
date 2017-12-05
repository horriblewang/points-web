package com.points.osp.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bailian.entity.User;
import com.points.osp.common.config.Global;


@Controller
@RequestMapping("points")
public class IndexController {
	
	
	/**
	 * Ö÷½çÃæ
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response,Model model) {
		User user = (User)request.getSession().getAttribute(Global.USER_SESSION_KEY);
		model.addAttribute("user", user);
		return "modules/sys/sysIndex";
	}

}

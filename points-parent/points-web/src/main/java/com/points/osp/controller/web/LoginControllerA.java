package com.points.osp.controller.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bailian.entity.User;
import com.bailian.utils.MD5;
import com.bailian.utils.ResultUtil;
import com.google.common.collect.Maps;
import com.points.osp.common.config.Global;
import com.points.osp.common.service.SystemService;
import com.points.osp.common.utils.CookieUtils;
import com.points.osp.common.utils.RedisUtil;

@Controller
public class LoginControllerA {
	
	@Autowired
	private SystemService SystemService;
	
	@Autowired
	private RedisUtil redisUtil;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/sys/sysLogin";
	}
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginIn(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, String> auth = Maps.newHashMap();
		String pass = request.getParameter("password");
		String userName = request.getParameter("username");
		auth.put("nonce", MD5.getMD5(pass.getBytes()));
		auth.put("username", userName);
		User user = SystemService.doLogin(auth);
		if(user != null){
			CookieUtils.setCookie(response, "nonce", MD5.getMD5(pass.getBytes()));
			request.getSession().setAttribute(Global.USER_SESSION_KEY, user);
			return "redirect:/points/index";
		}
		model.addAttribute("error", "用户名或者密码错误");
		return "modules/sys/sysLogin";
	}
	
	
	@RequestMapping(value = "/theme/{theme}")
	public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request, HttpServletResponse response){
		String kk = redisUtil.get("aaaa", "");
		System.out.println("pppppp====>" + kk);
		if (StringUtils.isNotBlank(theme)){
			CookieUtils.setCookie(response, "theme", theme);
		}else{
			theme = CookieUtils.getCookie(request, "theme");
		}
		return "redirect:" + request.getParameter("url");
	}

}

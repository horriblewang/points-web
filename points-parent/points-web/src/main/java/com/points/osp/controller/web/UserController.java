package com.points.osp.controller.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bailian.entity.User;
import com.points.osp.common.service.SystemService;

@Controller
@RequestMapping(value = "/sys/user")
public class UserController {
	
	@Autowired
	private SystemService systemService;
	
	/**
	 * 个人信息
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("info")
	public String info(User user, Model model) {
		User currentUser = systemService.getCurrentUser();
		model.addAttribute("currentUser", currentUser);
		return "modules/sys/userInfo";
	}
	
	/**
	 * 密码修改
	 * @param oldPassword
	 * @param newPassword
	 * @param model
	 * @return
	 */
	@RequestMapping("modifyPwd")
	public String modifyPwd(String oldPassword, String newPassword, Model model) {
		
		User currentUser = systemService.getCurrentUser();
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)){
			if (systemService.validatePassword(oldPassword, currentUser.getPassWord())){
				systemService.updatePasswordById(currentUser.getUserId(), newPassword);
				model.addAttribute("message", "修改密码成功");
			}else{
				model.addAttribute("message", "修改密码失败，旧密码错误");
			}
		}
		model.addAttribute("currentUser", currentUser);
		return "modules/sys/userModifyPwd";
	}

}

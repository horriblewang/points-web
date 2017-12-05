package com.points.osp.controller.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bailian.dto.ActivityDetailDto;
import com.bailian.dto.ActivityDto;
import com.bailian.dto.OrderInfoDto;
import com.bailian.entity.Activity;
import com.bailian.entity.Goods;
import com.bailian.entity.MemberInfo;
import com.bailian.entity.OrderDetail;
import com.bailian.entity.OrderInfo;
import com.bailian.entity.RuleInfo;
import com.bailian.utils.Page;
import com.google.common.collect.Maps;
import com.points.osp.common.service.GoodsService;
import com.points.osp.common.service.MemberService;
import com.points.osp.common.service.OrderService;
import com.points.osp.common.service.RuleService;
import com.points.osp.common.utils.ViewPage;

@Controller
@RequestMapping("points/rule")
public class RuleController {

	private static Logger log = LoggerFactory.getLogger(RuleController.class);

	private static final String WEB_ROOT = "modules/rule/";

	@Autowired
	private RuleService ruleService;

	@RequestMapping({ "list", "" })
	public String list(@RequestParam Map<String, Object> paraMap,
			HttpServletRequest request, HttpServletResponse response,
			Model model, RuleInfo ruleInfo) {
		ViewPage<RuleInfo> viewPage = new ViewPage<RuleInfo>(request, response);
		int count = ruleService.countRule(paraMap);
		Page<RuleInfo> page = new Page<RuleInfo>();
		page.setCurrentPage(viewPage.getPageNo());
		page.setPageSize(viewPage.getPageSize());
		page.setCount(count);
		paraMap.put("start", page.getStart() - 1);
		paraMap.put("end", page.getPageSize());
		List<RuleInfo> list = ruleService.getRuleByPage(paraMap);
		viewPage.getList().addAll(list);
		viewPage.setCount(count);
		model.addAttribute("page", viewPage);
		model.addAttribute("ruleInfo", ruleInfo);
		return WEB_ROOT + "ruleList";

	}

	/**
	 * 规则编辑页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param memberInfo
	 * @return
	 */
	@RequestMapping("form")
	public String ruleForm(HttpServletRequest request,
			HttpServletResponse response, Model model, RuleInfo ruleInfo) {
		Long ruleId = ruleInfo.getRuleId();
		if (ruleId != null) {
			ruleInfo = ruleService.getRuleInfo(ruleId);
		}
		model.addAttribute("ruleInfo", ruleInfo);
		return WEB_ROOT + "ruleForm";

	}

	/**
	 * 保存规则
	 * 
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param ruleInfo
	 * @return
	 */
	@RequestMapping("save")
	public String ruleSave(HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes redirectAttributes, RuleInfo ruleInfo) {
		if (ruleInfo.getRuleId() == null) {
			ruleService.addRuleInfo(ruleInfo);
		} else {
			ruleService.updateRuleInfo(ruleInfo);
		}
		redirectAttributes.addFlashAttribute("message", "操作成功");
		return "redirect:/points/rule";

	}

}

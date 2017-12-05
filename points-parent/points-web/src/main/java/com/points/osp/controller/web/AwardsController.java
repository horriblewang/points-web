package com.points.osp.controller.web;

import java.util.Date;
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
import com.bailian.dto.AwardsRecordDto;
import com.bailian.entity.Activity;
import com.bailian.entity.Goods;
import com.bailian.utils.DateUtils;
import com.bailian.utils.Page;
import com.google.common.collect.Maps;
import com.points.osp.common.service.ActivityService;
import com.points.osp.common.service.AwardsService;
import com.points.osp.common.service.GoodsService;
import com.points.osp.common.utils.ViewPage;


@Controller
@RequestMapping("/points/awards")
public class AwardsController {
	

	private static Logger log = LoggerFactory.getLogger(AwardsController.class);

	private static final String WEB_ROOT = "modules/awards/";
	
	@Autowired
	private AwardsService awardsService;
	
	/**
	 * 列表查询
	 * @param paraMap
	 * @param request
	 * @param response
	 * @param model
	 * @param awards
	 * @return
	 */
	@RequestMapping({ "list", "" })
	public String list(@RequestParam Map<String, Object> paraMap,
			HttpServletRequest request, HttpServletResponse response,
			Model model, AwardsRecordDto awards) {
		ViewPage<AwardsRecordDto> viewPage = new ViewPage<AwardsRecordDto>(request, response);
		int count = awardsService.getAwardsCountByPage(paraMap);
		Page<AwardsRecordDto> page = new Page<AwardsRecordDto>();
		page.setCurrentPage(viewPage.getPageNo());
		page.setPageSize(viewPage.getPageSize());
		page.setCount(count);
		paraMap.put("start", page.getStart() - 1);
		paraMap.put("end", page.getPageSize());
		List<AwardsRecordDto> list = awardsService.getAwardsByPage(paraMap);
		viewPage.getList().addAll(list);
		viewPage.setCount(count);
		model.addAttribute("page", viewPage);
		model.addAttribute("awards", awards);
		return WEB_ROOT + "awardsList";

	}
	
	
	
	/**
	 * 更新奖品领取
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param activity
	 * @return
	 */
	@RequestMapping("use")
	public String useGift(HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes redirectAttributes, AwardsRecordDto activity) {
		Long id = activity.getId();
		awardsService.userAwards(id);
		redirectAttributes.addFlashAttribute("message", "操作成功");
		return "redirect:/points/awards";
	}
	
	
	

}

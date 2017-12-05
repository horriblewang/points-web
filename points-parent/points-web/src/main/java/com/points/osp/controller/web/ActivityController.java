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
import com.bailian.entity.Activity;
import com.bailian.entity.Goods;
import com.bailian.utils.Page;
import com.google.common.collect.Maps;
import com.points.osp.common.service.ActivityService;
import com.points.osp.common.service.GoodsService;
import com.points.osp.common.utils.ViewPage;

/**
 * 转盘抽奖活动
 * @author wbwangsh
 *
 */
@Controller
@RequestMapping("/points/activity")
public class ActivityController {
	

	private static Logger log = LoggerFactory.getLogger(ActivityController.class);

	private static final String WEB_ROOT = "modules/activity/";
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private GoodsService goodsService;
	
	
	@RequestMapping({ "list", "" })
	public String list(@RequestParam Map<String, Object> paraMap,
			HttpServletRequest request, HttpServletResponse response,
			Model model, ActivityDto activity) {
		ViewPage<ActivityDto> viewPage = new ViewPage<ActivityDto>(request, response);
		int count = activityService.countActivity(paraMap);
		Page<Activity> page = new Page<Activity>();
		page.setCurrentPage(viewPage.getPageNo());
		page.setPageSize(viewPage.getPageSize());
		page.setCount(count);
		paraMap.put("start", page.getStart() - 1);
		paraMap.put("end", page.getPageSize());
		List<ActivityDto> list = activityService.getActivityByPage(paraMap);
		viewPage.getList().addAll(list);
		viewPage.setCount(count);
		model.addAttribute("page", viewPage);
		model.addAttribute("activity", activity);
		return WEB_ROOT + "actList";

	}
	
	/**
	 * 活动编辑
	 * @param request
	 * @param response
	 * @param model
	 * @param activity
	 * @return
	 */
	@RequestMapping("form")
	public String actForm(HttpServletRequest request,
			HttpServletResponse response, Model model, Activity activity) {
		Long actId = activity.getActId();
		//数据修改
		if (actId != null) {
			activity = activityService.getActivityById(actId);
			List<ActivityDetailDto> details = activityService.getActivityDetailById(actId);
			model.addAttribute("details", details);
		}
		
		List<Goods> goodsList = goodsService.getAllGoods();
		model.addAttribute("goodsList", goodsList);
		model.addAttribute("activity", activity);
		return WEB_ROOT + "actForm";

	}

	/**
	 * 保存活动
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param activity
	 * @return
	 */
	@RequestMapping("save")
	public String actSave(HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes redirectAttributes, Activity activity) {
		Long actId = activity.getActId();
		Map<String, Object> ckMap = Maps.newHashMap();
		ckMap.put("startTime", activity.getStartTime());
		ckMap.put("actId", actId);
		Long errorActId = activityService.checkActTime(ckMap);
		if(errorActId != null){
			redirectAttributes.addFlashAttribute("message", "操作失败，活动时间重叠！活动编号：" + errorActId);
			return "redirect:/points/activity";	
		}
		String deail = request.getParameter("detailList");
		if (actId != null) {// 修改
			activityService.updateActivity(activity, deail );
		} else {// 新增
			activityService.addActivity(activity, deail);
		}
		redirectAttributes.addFlashAttribute("message", "操作成功");
		return "redirect:/points/activity";

	}
	
	
	/**
	 * 活动禁用
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param activity
	 * @return
	 */
	@RequestMapping("disable")
	public String disable(HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes redirectAttributes, Activity activity) {
		Long actId = activity.getActId();
		activityService.disableAct(actId);
		redirectAttributes.addFlashAttribute("message", "操作成功");
		return "redirect:/points/activity";
	}
	
	/**
	 * 活动启用
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param activity
	 * @return
	 */
	@RequestMapping("enable")
	public String enable(HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes redirectAttributes, Activity activity) {
		Long actId = activity.getActId();
		activityService.enableAct(actId);
		redirectAttributes.addFlashAttribute("message", "操作成功");
		return "redirect:/points/activity";
	}
	
	
	/**
	 * 活动详情页面
	 * @param request
	 * @param response
	 * @param model
	 * @param activity
	 * @return
	 */
	@RequestMapping("detail")
	public String detail(HttpServletRequest request,HttpServletResponse response,
			Model model, Activity activity) {
		Long actId = activity.getActId();
		activity = activityService.getActivityById(actId);
		List<ActivityDetailDto> details = activityService.getActivityDetailById(actId);
		model.addAttribute("details", details);
		model.addAttribute("activity", activity);
		return WEB_ROOT + "actDetail";
	}
	

}

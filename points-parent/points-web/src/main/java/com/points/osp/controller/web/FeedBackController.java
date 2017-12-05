package com.points.osp.controller.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bailian.entity.FeedBack;
import com.bailian.utils.Page;
import com.points.osp.common.service.FeedBackService;
import com.points.osp.common.utils.ViewPage;

/**
 * 系统反馈
 * @author wbwangsh
 *
 */
@Controller
@RequestMapping("points/feedBack")
public class FeedBackController {
	
	@Autowired
	private FeedBackService feedBackService;
	
	private static final String WEB_ROOT = "modules/feedBack/";
	
	/**
	 * 反馈分页查询
	 * @param map
	 * @param request
	 * @param response
	 * @param model
	 * @param feedBack
	 * @return
	 */
	@RequestMapping({"list",""})
	public String goodsList(@RequestParam Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response,
			Model model, FeedBack feedBack) {
		ViewPage<FeedBack> viewPage = new ViewPage<FeedBack>(request, response);
		int count = feedBackService.countFeedBack(map);
		Page<FeedBack> page = new Page<FeedBack>();
		page.setCurrentPage(viewPage.getPageNo());
		page.setPageSize(viewPage.getPageSize());
		page.setCount(count);
		map.put("start", page.getStart() - 1);
		map.put("end", page.getPageSize());
		List<FeedBack> list = feedBackService.getFeedBackByPage(map);
		viewPage.getList().addAll(list);
		viewPage.setCount(count);
		model.addAttribute("page", viewPage);
		model.addAttribute("feedBack", feedBack);
		return WEB_ROOT + "feedBackList";

	}

}

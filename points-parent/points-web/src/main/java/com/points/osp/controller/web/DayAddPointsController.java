package com.points.osp.controller.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.points.osp.common.service.DayAddPointsService;

@Controller
@RequestMapping("points/add")
public class DayAddPointsController extends BaseController {
	
	@Autowired
	private DayAddPointsService dayAddPointsService;
	
	@RequestMapping("day")
	public String  addPointsDay(HttpServletResponse response){
		dayAddPointsService.addPoints();
		return ajaxJson(response, "success");
	}

}

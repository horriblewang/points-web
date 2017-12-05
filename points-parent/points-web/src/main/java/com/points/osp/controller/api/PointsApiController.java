package com.points.osp.controller.api;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bailian.entity.MemberInfo;
import com.bailian.utils.ResultUtil;
import com.google.common.collect.Maps;
import com.points.osp.common.service.MemberService;
import com.points.osp.controller.web.BaseController;

/**
 * 积分转让API
 * @author xyzq
 *
 */
@Controller
@RequestMapping("api/point")
public class PointsApiController extends BaseController{
	
	@Autowired
	private MemberService memberService;
	
	
	
	@RequestMapping("trans")
	public String transPoints(MemberInfo memberInfo,
			HttpServletRequest request, HttpServletResponse response){
		String mobile = memberInfo.getMobile();
		Long points = memberInfo.getPoints();
		Long memberId =memberInfo.getMemberId();
		if(StringUtils.isBlank(mobile) || points == null || memberId == null){
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult("参数不完整！"));
		}
		Map<String, Object> paraMap = Maps.newHashMap();
		paraMap.put("mobile", mobile);
		paraMap.put("points", points.toString());
		paraMap.put("memberId", memberId.toString());
		String msg = memberService.transMemPoints(paraMap);
		
		
		if(StringUtils.isNotBlank(msg)){
			return ajaxJsonP(request, response, ResultUtil.creComErrorResult(msg));
		}
		
		return ajaxJsonP(request, response, ResultUtil.creComSucResult());
	}

}

package com.points.osp.controller.api;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bailian.dto.AwardsRecordDto;
import com.bailian.dto.MemberDto;
import com.bailian.utils.ResultUtil;
import com.google.common.collect.Maps;
import com.points.osp.common.service.AwardsService;
import com.points.osp.controller.web.BaseController;

/**
 * 我的中奖记录查询
 * 
 * @author wbwangsh
 *
 */
@Controller
@RequestMapping("api/awards")
public class AwardsApiController extends BaseController {

	@Autowired
	private AwardsService awardsService;

	/**
	 * 我的中奖记录
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("myRewards")
	public String myRewards(MemberDto memberDto,
			HttpServletRequest request, HttpServletResponse response) {

		if (memberDto.getMemberId() == null) {
			return ajaxJsonP(request, response,ResultUtil.creComErrorResult("请登录查询！"));
		}

		Map<String, Object> map = Maps.newHashMap();
		Map<String, Object> resMap = Maps.newHashMap();
		map.put("memberId", memberDto.getMemberId());
		map.put("start", memberDto.getStart());
		int end = memberDto.getEnd();
		map.put("end", end == 0 ? 1000 : end);
		List<AwardsRecordDto> list = awardsService.getAwardsByPage(map);
		int total = awardsService.getAwardsCountByPage(map);
		resMap.put("total", total);
		resMap.put("list", list);
		return ajaxJsonP(request, response, ResultUtil.creObjSucResult(resMap));

	}

}

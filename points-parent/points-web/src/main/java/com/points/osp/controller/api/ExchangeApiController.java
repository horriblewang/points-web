package com.points.osp.controller.api;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bailian.dto.GoodsRecordDto;
import com.bailian.dto.MemberDto;
import com.bailian.utils.ResultUtil;
import com.google.common.collect.Maps;
import com.points.osp.common.service.MemberService;
import com.points.osp.controller.web.BaseController;

/**
 * �ҵĶһ���¼��ѯ
 * @author wbwangsh
 *
 */
@Controller
@RequestMapping("api/exchange")
public class ExchangeApiController extends BaseController{
	
	
	@Autowired
	private MemberService memberService;
	
	/**
	 * �ҵĶһ���¼��ѯ
	 * @param memberDto
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("list")
	public String getExchangeList(MemberDto memberDto,
			HttpServletRequest request, HttpServletResponse response){
		
		//��½��ѯ�һ���¼
		if (memberDto.getMemberId() == null) {
			return ajaxJsonP(request, response,ResultUtil.creComErrorResult("���¼��ѯ��"));
		}
		
		Map<String, Object> map = Maps.newHashMap();
		Map<String, Object> resMap = Maps.newHashMap();
		map.put("memberId", memberDto.getMemberId());
		map.put("start", memberDto.getStart());
		int end = memberDto.getEnd();
		map.put("end", end == 0 ? 10 : 1000);
		List<GoodsRecordDto> list = memberService.exchangeList(map);
		int total = memberService.countExchange(map);
		resMap.put("total", total);
		resMap.put("list", list);
		return ajaxJsonP(request, response, ResultUtil.creObjSucResult(resMap));

	}

}

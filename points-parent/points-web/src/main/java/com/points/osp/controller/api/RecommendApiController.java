package com.points.osp.controller.api;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bailian.entity.MemberInfo;
import com.bailian.utils.ResultUtil;
import com.google.common.collect.Maps;
import com.points.osp.common.service.RecomApiService;
import com.points.osp.controller.web.BaseController;

@Controller
@RequestMapping("api/reco")
public class RecommendApiController extends BaseController {

	@Autowired
	private RecomApiService recomApiService;

	@RequestMapping("list")
	public String getRecommendList(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> resMap = Maps.newHashMap();
		resMap.put("count", 0);
		String memberId = request.getParameter("memberId");
		if (StringUtils.isBlank(memberId)) {
			return ajaxJsonP(request, response,
					ResultUtil.creComErrorResult("ÇëµÇÂ¼£¡"));
		}
		List<MemberInfo> mems = recomApiService.getRecommendMem(Long.valueOf(memberId));

		if(CollectionUtils.isNotEmpty(mems)){
			resMap.put("count", mems.size());
			resMap.put("records", mems);
		}
		return ajaxJsonP(request, response, ResultUtil.creObjSucResult(resMap));
	}

}
